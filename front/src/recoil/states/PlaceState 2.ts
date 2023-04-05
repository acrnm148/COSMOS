
{/* 카테고리별 선택지 */}
export interface CATE_QA {
    [key : string] : string[], '음식' : string[], '카페' : string[], '문화' : string[],'쇼핑' : string[], '관광' : string[], '운동' : string[],'축제' : string[]
}
export const CATEGORY_QA:CATE_QA = {
    '음식' : ['음식이 맛있어요', '서비스가 좋아요', '가게만의 메뉴가 있어요', '가성비가 좋아요', '가게가 깨끗해요'],
    '카페' : ['메뉴가 다양해요', '커피가 맛있어요', '인테리어가 감각적이에요', '테라스가 있어요', '공간이 넓고 쾌적해요'],
    '문화' : ['이용이 편리해요', '서비스가 좋아요','편의시설이 잘 되어있어요', '컨텐츠가 유익해요',  '공간이 깔끔해요' ],
    '쇼핑' : ['매장이 다양해요', '서비스가 좋아요','편의시설이 잘 되어있어요', '가성비가 좋아요',  '공간이 깔끔해요' ],
    '관광' : ['볼거리가 다양해요', '안내가 잘 되어있어요','부대시설이 잘 되어있어요', '풍경맛집이에요',  '사람이 많아요'],
    '운동' : ['활동시설이 다양해요', '서비스가 친절해요','편의시설이 잘 되어있어요', '가성비가 좋아요',  '시설이 깔끔해요' ],
    '축제' : ['볼거리가 다양해요', '서비스가 좋아요','편의시설이 잘 되어있어요', '컨텐츠가 유익해요',  '공간이 깔끔해요' ],
    '숙소' : [ '조식이 맛있어요', '시설이 좋아요', '편의시설이 잘 되어있어요', '가성비가 좋아요', '공간이 깔끔해요']
}
{/* 공통 선택지 */}
export const COMMON_QA = ['접근성이 좋아요', '분위기가 좋아요', '반려동물 동반이 가능해요', '주차 지원이 가능해요', '사진찍기 좋아요']

// TODO : db를 영어에서 한글로 바꾸거나 받은 데이터 변환
export const PLACECATE = {
    'cafe' : '카페',
    'shopping' : '쇼핑',
    'restaurant' : '음식',
    'accommodation' : '숙소',
    'festival' : '축제',
    'tour' : '관광',
    'culture' : '문화',
    'leisure' :'운동'
}
export interface Place{
    idx : number,
    name : string,
    imgUrl : string,
    category : string,
    location : string,
    date: string,
    placeId : number,
}