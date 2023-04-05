from django.shortcuts import render
from rest_framework.response import Response
from rest_framework.decorators import api_view
from rest_framework import status
from sklearn.metrics.pairwise import cosine_similarity
from .models import User, Review, Place, Reviewplace
from .serializers import UserSerializer, ReviewPlaceSerializer, ReviewSerializer, PlaceSerializer
import pandas as pd
from django.http import JsonResponse
from django.http import HttpResponse
import math
import schedule
import time
import requests

user_based_collabor = pd.DataFrame()

def reload_data():
    requests.get('http://j8e104.p.ssafy.io:8000/django/CF/algorithm/data')


'''
리뷰와 장소를 통해 사용자 기반 협업 필터링 계산 진행
1일 1 갱신 진행
cosine 유사도 활용
'''
@api_view(['GET'])
def algorithm(request):
    global user_based_collabor
    
    # 리뷰 데이터를 담을 DataFrame
    review_data = pd.DataFrame()
    # 장소 데이터를 담을 DataFrame
    place_data = pd.DataFrame()
    
    # 컬럼 삽입
    review_data['user_seq', 'place_id', 'score'] = 0
    place_data['place_id', 'name'] = 0
    
    # 리뷰를 남긴 모든 유저 조회
    user_object = User.objects.all()
    for user_idx in range(len(user_object)):
        reviews = user_object[user_idx].reviews.all()
        
        # 리뷰 내용에서 user_seq, place_id, score 추출 후 데이터 삽입
        for review in reviews:
            for review_place in review.review_place.all():
                data = {'user_seq' : review.user_seq.user_seq, 'place_id' : review_place.place.place_id, 'score' : review.score}
                new_input = pd.DataFrame.from_dict([data])
                # review_data = review_data.append(new_input, ignore_index=True)
                review_data = pd.concat([review_data, new_input])
    
    # 첫 번째 열 index 삭제
    review_data.drop(labels=[review_data.columns[0]], axis=1, inplace=True)
    # print(review_data)
    
    # 모든 장소에 대한 정보 조회
    place_object = Place.objects.all()
    for place_idx in range(len(place_object)):
        
        # 장소에 대한 place_id와 name 추출
        data = {'place_id' : place_object[place_idx].place_id, 'name' : place_object[place_idx].name}
        new_input = pd.DataFrame([data])
        place_data = pd.concat([place_data, new_input])
        print('place 추출 중...')
    
    # 첫 번째 열 index 삭제
    place_data.drop(labels=[place_data.columns[0]], axis=1, inplace=True)
    # print(place_data)

    # DataFrame 병합 진행
    user_place_rating = pd.merge(review_data, place_data, on = 'place_id')
    
    # 리뷰 평점과 장소를 이용해 pivot 테이블로 변경
    user_place_rating = user_place_rating.pivot_table('score', index='user_seq', columns='name')
    
    # NAN 값은 모두 0으로 변경
    user_place_rating.fillna(0, inplace = True)
    
    # 코사인 유사도 계산
    user_based_collabor = cosine_similarity(user_place_rating)
    
    # 유저와 유저간의 테이블로 변경
    user_based_collabor = pd.DataFrame(data = user_based_collabor, index=user_place_rating.index, columns = user_place_rating.index)
    
    return Response(status=status.HTTP_200_OK)

@api_view(['GET'])
def reload(request):
    
    # schedule을 활용해 업데이트 될 시간 선정
    schedule.every().day.at("00:00").do(reload_data)
    
    # schedule을 지속 실행시키기 위한 while 문
    while True:
        schedule.run_pending()
        time.sleep(1)
        print('데이터 준비중')


'''
해당 유저와 가장 유사도가 높은 유저를 반환
'''
@api_view(['POST'])
def userAlgorithm(request, user_seq):
    # 카테고리, 시/도, 구/군 입력 받은 값 조회
    categories = request.data['categories']
    sido = request.data['sido']
    gugun = request.data['gugun']
    
    def get_user_based_collabor(user_seq):
        # 유사도가 높은 사람부터 정렬 진행
        users = user_based_collabor[user_seq].sort_values(ascending = False)
        limit = math.ceil(len(users) * 0.2)
        # 100명 이상일 경우 상위 20% 유저 선정
        if limit > 100:
            data = users[:limit]
            # 자기 자신은 유저에서 제외
            data.drop([1], axis = 0, inplace =True)
            # 랜덤으로 유저 섞어주기 -> 동적인 추천을 위한 로직
            data = data.sample(n = len(users))
            return data
        else:
            # 100명 미만일 경우 유저를 랜덤으로 섞고 추천
            data = users
            # 자기 자신은 유저에서 제외
            data.drop([1], axis = 0, inplace =True)
            data = data.sample(n = len(users))
            return data

    result = get_user_based_collabor(user_seq)
    answer = []
    
    # 카테고리별로 반드시 장소에 대한 데이터 입력 필요
    for category in categories:
        # 리뷰가 존재하지 않아 에러 발생 할 경우를 대비해 try except 활용
        try:
            # 장소를 찾을 경우 알려줄 신호 변수
            flag = False
            # 유사도가 높은 유저를 모두 조회
            for idx in range(1, len(result)):
                result_data = result.index[idx]
            
                user = User.objects.get(pk=result_data)
                reviews = user.reviews.all()
                
                # 해당 유저의 모든 리뷰 둘러보기
                for review in reviews:
                    for review_place in review.review_place.all():
                        
                        # 시/도, 구/군이 포함된 장소를 추천해주어야 함
                        # 카테고리의 타입도 일치해야 함
                        address = review_place.place.address
                        if category == review_place.place.type and sido in address and gugun in address and review_place.place.place_id not in answer:
                            answer.append(review_place.place.place_id)
                            flag = True
                            break
                    if flag:
                        break
                if flag:
                    break
            else:
                while True:
                    # 랜덤으로 타입이 같고 시/도, 구/군이 같은 장소를 추가
                    place_object = Place.objects.filter(type=category, address__contains=sido)
                    place_object = place_object.filter(address__contains=gugun).order_by('?')[0]
                    if place_object.place_id not in answer:
                        answer.append(place_object.place_id)
                        break
        except:
            while True:
                # 랜덤으로 타입이 같고 시/도, 구/군이 같은 장소를 추가
                place_object = Place.objects.filter(type=category, address__contains=sido)
                place_object = place_object.filter(address__contains=gugun).order_by('?')[0]
                if place_object.place_id not in answer:
                    answer.append(place_object.place_id)
                    break

    # Json 타입으로 반환
    jsonData = JsonResponse(answer, safe=False, json_dumps_params={'ensure_ascii': False})
    
    return jsonData