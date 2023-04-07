import { defaultInstance } from "../utils";

// GET APIs
/**
 * GET : 시/도 리스트를 가져온다.
 * @returns  {sidoCode: string, sidoName: string}
 */
export const getSidoList = async () => {
  const { data } = await defaultInstance.get(`sido`);
  return data;
};

/**
 * @param {string} sidoCode
 * GET : 구/군 리스트를 가져온다.
 * @returns  {sidoCode: string, sidoName: string}
 */
export const getGugunList = async (sidoCode: string) => {
  if (sidoCode === "") return null; // 시/도 미선택시
  const { data } = await defaultInstance.get(`gugun/${sidoCode}`);
  return data;
};

/**
 * @param {string} searchWord
 * GET : 검색어 자동완성
 * @returns  {placeId, name, address, thumbNailUrl, type}
 */
export const getAutoSearchList = async (searchWord: string) => {
  if (searchWord === "") return null;
  const { data } = await defaultInstance.get(`places/auto/${searchWord}`);
  return data;
};

/**
 * @param {number} userSeq
 * @param {string} sido
 * @param {string} gugun
 * @param {string} text
 * @param {string} filter
 * @param {number} limit
 * @param {number} offset
 * GET : 조건별 검색
 * @returns  {places: [{placeId, name, address, score, thumbNailUrl, detail, latitude, longitude, type, like}], midLatitude, midLongitude}
 */
export const getPlacesWithConditions = async (
  userSeq: number,
  sido: string,
  gugun: string,
  text: string,
  filter: string
) => {
  // 검색어
  if (sido === "" && gugun === "" && text !== "" && filter === "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/gugun/text/${text}/filter/`
    );
    return data;
  }
  // 검색필터
  else if (sido === "" && gugun === "" && text === "" && filter !== "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/gugun/text/filter/${filter}/`
    );
    return data;
  }
  // 검색어, 검색필터
  else if (sido === "" && gugun === "" && text !== "" && filter !== "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/gugun/text/${text}/filter/${filter}/`
    );
    return data;
  }
  // 시/도, 구/군
  else if (sido !== "" && gugun !== "" && text === "" && filter === "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido}/gugun/${gugun}/text/filter/`
    );
    return data;
  }
  // 시/도, 구/군, 검색어
  else if (sido !== "" && gugun !== "" && text !== "" && filter === "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido}/gugun/${gugun}/text/${text}/filter/`
    );
    return data;
  }
  // 시/도, 구/군, 검색필터
  else if (sido !== "" && gugun !== "" && text === "" && filter !== "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido}/gugun/${gugun}/text/filter/${filter}/`
    );
    return data;
  }
  // 시/도, 구/군, 검색어, 검색필터
  else if (sido !== "" && gugun !== "" && text !== "" && filter !== "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido}/gugun/${gugun}/text/${text}/filter${filter}/`
    );
    return data;
  }
  // 그 외 경우
  else {
    return null;
  }
};

/**
 * @param {number} userSeq : 사용자 번호
 * @param {number} placeId : 장소 ID
 * @param {number} type : 장소 유형
 * GET : 장소 상세정보 가져오기
 * @returns {}
 */
export const getPlaceDetail = async (
  userSeq: number,
  placeId: number,
  type: string
) => {
  const { data } = await defaultInstance.get(
    `places/detail/users/${userSeq}/placeId/${placeId}/type/${type}`
  );
  if (placeId === 0 && type === "") return null;
  return data;
};

/**
 * @param {number} userSeq : 사용자 번호
 * @param {number} placeId : 장소 ID
 * GET : 사용자의 찜 리스트에 추가
 * @returns {}
 */
export const likePlace = async (userSeq: number, placeId: number) => {
  const { data } = await defaultInstance.get(
    `places/${placeId}/users/${userSeq}`
  );
  return data;
};

/**
 * @param {number} placeId : 장소 ID
 * GET : 장소별 리뷰 전체 불러오기
 * @returns {}
 */
export const getReviewAll = async (
  placeId: number,
  limit: number,
  offset: number
) => {
  if (placeId === null) return null;
  const { data } = await defaultInstance.get(
    `reviews/places/${placeId}?limit=${limit}&offset=${offset}`
  );
  return data;
};

/**
 * @param {number} userSeq : 사용자 번호
 * @param {number} placeId : 장소 ID
 * @param {number} coupleId : 커플 ID
 * GET : 장소별 유저 리뷰 불러오기
 * @returns {}
 */
export const getReviewOurs = async (
  userSeq: number,
  placeId: number,
  coupleId: string,
  limit: number,
  offset: number
) => {
  if (coupleId === "") {
    // 솔로 유저
    const { data } = await defaultInstance.get(
      `reviews/users/${userSeq}/coupleId/places/${placeId}/?limit=${limit}&offset=${offset}`
    );
    return data;
  } else {
    // 커플 유저
    const { data } = await defaultInstance.get(
      `reviews/users/${userSeq}/coupleId/${coupleId}/places/${placeId}/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
};

// POST APIs
/**
 * @RequestBody JSON
 * POST : 데이트 코스 생성
 * @returns {}
 */
export const getDateCourse = async (data: any, userSeq: number) => {
  // UCC용 코스 제작
  if (userSeq === 227) {
    const res = {
      data: {
        courseId: 10000,
        date: null,
        midLatitude: 35.1123123825,
        midLongitude: 128.959072925,
        name: null,
        orders: null,
        places: [
          {
            address: "부산광역시 사하구 낙동남로 1191 부산현대미술관",
            courseId: null,
            detail: `지하 1층과 지상 1~2층 전시실, 3층 아카이브실과 업무 공간 등으로 구성된다. 뉴미디어 아트를 포함한 동시대 미술, 자연과 생태를 주제로 전시한다. 외관부터 그 특징이 드러난다. 식물학자 패트릭 블랑(Patrick Blanc)이 국내 자생하는 식물 175종을 식재한 ‘수직정원’이 건물 외벽을 푸르게 물들인다.<br>부산현대미술관은 부산광역시가 건립한 공공미술관이다.<br>
            낙동강 하구의 을숙도에 위치한 부산현대미술관은 29,900㎡의 부지에 연면적 15,312㎡ 규모의 건물로 2017년 완공되었다. 지하1층, 지상1층, 2층의 전시공간(5,780㎡)과 수장고, 세미나 및 체험실, 어린이 독서공간, 아카이브실, 학예실, 사무실 등을 포함한 3층으로 구성되어 있다.<br>
            2018년 6월 개관한 부산현대미술관은 동시대 미술을 중심으로 하는 공립 ‘현대’미술관이다.<br>
            오늘날의 미술동향과 사회적 맥락에 대한 연구를 바탕으로 새로운 담론을 제시하는 전시, 미래지향적인 예술 교육프로그램, 국제 네트워크와 협력, 동시대 미술작품 수집, 그리고 학술행사 등 미술관으로서의 역할을 수행할 것이다.<br>`,
            latitude: "35.10937035",
            longitude: "128.9427199",
            name: "부산현대미술관",
            orders: 1,
            phoneNumber: null,
            placeId: 5338,
            score: null,
            thumbNailUrl:
              "http://tong.visitkorea.or.kr/cms/resource/82/2603382_image2_1.jpg",
            type: "culture",
          },
          {
            address: "부산광역시 사하구 낙동남로 1413(하단동)",
            courseId: null,
            detail: `Trendy하고 감성적인 최고의 브랜드, 새롭고 다양함이 생활이 되는 Life Style Goods, 보는 & 먹는 즐거움의 감성 충만한 공간`,
            latitude: "35.1067933",
            longitude: "128.9665012",
            name: "아트몰링 하단점",
            orders: 2,
            phoneNumber: "1588-5550",
            placeId: 14461,
            score: null,
            thumbNailUrl:
              "http://tong.visitkorea.or.kr/cms/resource/14/2885914_image2_1.jpg",
            type: "shopping",
          },
          {
            address: "부산광역시 사하구 낙동남로1423번길 77-2 1층",
            courseId: null,
            detail: `안녕하세요. 더브런치하우스 입니다. 이탈리아음식과 와인,해외맥주,하이볼,위스키 를 누구나 편하게 가성비좋은 맛있는 브런치와 즐길수있는 공간을 만드는것이 저희의 모티브입니다.`,
            latitude: "35.1104",
            longitude: "128.9649",
            name: "더브런치하우스",
            orders: 3,
            phoneNumber: null,
            placeId: 48834,
            score: null,
            thumbNailUrl:
              "https://search.pstatic.net/common/?src=https%3A%2F%2Fpup-review-phinf.pstatic.net%2FMjAyMzAzMTVfMTYz%2FMDAxNjc4ODM0NzE3ODE0.nBHZmIrDdHvcVg9QB_GiOMW1UY9y83DxXaa-Lq7Qp24g.BvTFd1VwXwfwADS9mPhL4nAFn2RiaHxbJ3Oo2gY5CtQg.JPEG%2F20230314_185957.jpg",
            type: "cafe",
          },
          {
            address: "부산광역시 사하구 강변대로 420-7",
            courseId: null,
            detail: `북구에서 사하구 쪽 강변 대로에 있는 카페 VSANT은 공장지대의 폐기물 처리용 용지를 깔끔히 재정비하여 만든 곳이다. 3층 건물로 입구 쪽에 야외 테라스가 마련되어 있다. 전체적으로 노출 콘크리트와 철재를 사용한 인더스트리얼 인테리어로 꾸며져 있어 빈티지 감성을 느낄 수 있다. 층마다 다양한 소품들로 공간 연출을 해 놓아 볼거리가 많으며 특히 1층 한쪽에는 빨간색 폭스바겐 차를 비치해 놓은 포토존도 마련되어 있다. 3층 루프탑은 젊은 작가들의 그라피티 아트로 꾸며져 있으며 선베드가 준비되어 있어 탁 트인 공간에서 하늘과 낙동강 뷰를 한눈에 볼 수 있다. 건물 뒤쪽에 넓은 주차장이 있어 편리하게 이용할 수 있다.`,
            latitude: "35.12268588",
            longitude: "128.9621706",
            name: "VSANT",
            orders: 4,
            phoneNumber: null,
            placeId: 3599,
            score: null,
            thumbNailUrl:
              "http://tong.visitkorea.or.kr/cms/resource/29/2838929_image2_1.jpg",
            type: "restaurant",
          },
        ],
      },
    };
    return res;
  }
  const res = await defaultInstance.post(`/courses`, data, {
    headers: { "Content-Type": "application/json" },
  });
  return res;
};

// PUT APIs
/**
 * @param courseId: 코스 id
 * PUT : 코스 찜
 */
export const likeThisCourse = async (data: any) => {
  const res = await defaultInstance.put(`/courses`, data, {
    headers: { "Content-Type": "application/json" },
  });
  return res;
};

// DELETE APIs

/**
 * @param {number} userSeq : 사용자 번호
 * @param {number} placeId : 장소 ID
 * DELETE : 사용자의 찜 리스트에 해제
 * @returns {}
 */
export const dislikePlace = async (userSeq: number, placeId: number) => {
  const { data } = await defaultInstance.delete(
    `places/${placeId}/users/${userSeq}`
  );
  return data;
};
