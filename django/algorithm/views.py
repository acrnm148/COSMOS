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

user_based_collabor = pd.DataFrame()

'''
리뷰와 장소를 통해 사용자 기반 협업 필터링 계산 진행
1일 1 갱신 진행
cosine 유사도 활용
'''
@api_view(['GET'])
def algorithm(request):
    global user_based_collabor
    review_data = pd.DataFrame()
    place_data = pd.DataFrame()
    
    review_data['user_seq', 'place_id', 'score'] = 0
    place_data['place_id', 'name'] = 0
    
    user_object = User.objects.all()
    for user_idx in range(len(user_object)):
        reviews = user_object[user_idx].reviews.all()
        for review in reviews:
            for review_place in review.review_place.all():
                data = {'user_seq' : review.user_seq.user_seq, 'place_id' : review_place.place.place_id, 'score' : review.score}
                new_input = pd.DataFrame.from_dict([data])
                # review_data = review_data.append(new_input, ignore_index=True)
                review_data = pd.concat([review_data, new_input])
                
    review_data.drop(labels=[review_data.columns[0]], axis=1, inplace=True)
    # print(review_data)
    
    place_object = Place.objects.all()
    # for place_idx in range(len(place_object)):
    for place_idx in range(10):
        data = {'place_id' : place_object[place_idx].place_id, 'name' : place_object[place_idx].name}
        new_input = pd.DataFrame([data])
        place_data = pd.concat([place_data, new_input])
        print('place 추출 중...')
    place_data.drop(labels=[place_data.columns[0]], axis=1, inplace=True)
    # print(place_data)

    user_place_rating = pd.merge(review_data, place_data, on = 'place_id')
    user_place_rating = user_place_rating.pivot_table('score', index='user_seq', columns='name')
    user_place_rating.fillna(0, inplace = True)
    
    user_based_collabor = cosine_similarity(user_place_rating)
    
    user_based_collabor = pd.DataFrame(data = user_based_collabor, index=user_place_rating.index, columns = user_place_rating.index)
    
    return Response(status=status.HTTP_200_OK)

'''
해당 유저와 가장 유사도가 높은 유저를 반환
'''
@api_view(['POST'])
def userAlgorithm(request, user_seq):
    categories = request.data['categories']
    sido = request.data['sido']
    gugun = request.data['gugun']
    print(categories, sido, gugun)
    
    def get_user_based_collabor(user_seq):
        users = user_based_collabor[user_seq].sort_values(ascending = False)
        limit = math.ceil(len(users) * 0.2)
        if limit > 100:
            data = users[:limit]
            data.drop([1], axis = 0, inplace =True)
            data = data.sample(n = len(users))
            return data
        else:
            data = users
            data.drop([1], axis = 0, inplace =True)
            data = data.sample(n = len(users))
            return data

    result = get_user_based_collabor(user_seq)
    answer = []
    
    for category in categories:
        try:
            flag = False
            for idx in range(1, len(result)):
                result_data = result.index[idx]
            
                user = User.objects.get(pk=result_data)
                reviews = user.reviews.all()
                
                for review in reviews:
                    for review_place in review.review_place.all():
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
                    place_object = Place.objects.filter(type=category).order_by('?')[0]
                    if place_object.place_id not in answer:
                        answer.append(place_object.place_id)
                        break
        except:
            while True:
                place_object = Place.objects.filter(type=category).order_by('?')[0]
                if place_object.place_id not in answer:
                    answer.append(place_object.place_id)
                    break

    jsonData = JsonResponse(answer, safe=False, json_dumps_params={'ensure_ascii': False})
    
    return jsonData