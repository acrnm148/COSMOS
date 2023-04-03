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
  filter: string,
  limit: number,
  offset: number
) => {
  // 검색어
  if (sido === "" && gugun === "" && text !== "" && filter === "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/gugun/text/${text}/filter/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
  // 검색필터
  else if (sido === "" && gugun === "" && text === "" && filter !== "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/gugun/text/filter/${filter}/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
  // 검색어, 검색필터
  else if (sido === "" && gugun === "" && text !== "" && filter !== "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/gugun/text/${text}/filter/${filter}/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
  // 시/도, 구/군
  else if (sido !== "" && gugun !== "" && text === "" && filter === "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido}/gugun/${gugun}/text/filter/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
  // 시/도, 구/군, 검색어
  else if (sido !== "" && gugun !== "" && text !== "" && filter === "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido}/gugun/${gugun}/text/${text}/filter/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
  // 시/도, 구/군, 검색필터
  else if (sido !== "" && gugun !== "" && text === "" && filter !== "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido}/gugun/${gugun}/text/filter/${filter}/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
  // 시/도, 구/군, 검색어, 검색필터
  else if (sido !== "" && gugun !== "" && text !== "" && filter !== "") {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido}/gugun/${gugun}/text/${text}/filter${filter}/?limit=${limit}&offset=${offset}`
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
export const getDateCourse = async (data: any) => {
  const res = await defaultInstance.post(`/courses`, data, {
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
