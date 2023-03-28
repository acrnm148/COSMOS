import { defaultInstance } from "../utils";

// GET APIs

/**
 * GET : 시/도 리스트 데이터를 가져온다.
 * @returns  [] : 시/도 id, 시/도 name을 가진 Item 배열
 */
export const getSidoList = async () => {
  const { data } = await defaultInstance.get("sido");
  return data;
};

/**
 * @param {string} sido : 시/도 코드
 * GET : 구/군 리스트 데이터를 가져온다.
 * @returns  [] : 구/군 id, 구/군 name을 가진 Item 배열
 */
export const getGugunList = async (sido: string) => {
  const { data } = await defaultInstance.get(`gugun/${sido}`);
  return data;
};

/**
 * @param {string} word : 검색어
 * GET : 검색어의 낱말을 포함한 가게 정보를 나열한다.
 * @returns {PlaceholderInSubject, name, thumbNailUrl}
 */
export const getListWithSearchWord = async (searchWord: string) => {
  const { data } = await defaultInstance.get(`places/auto/${searchWord}`);
  return data;
};

/**
 * @param {number} userSeq : 사용자번호
 * @param {number} placeId : 장소 ID
 * @param {string} type: 장소 유형
 * GET : 장소의 상세 정보를 가져온다.
 * @returns {장소 type마다 다름}
 */
export const getPlaceDetail = async (
  userSeq: number,
  placeId: number,
  type: string
) => {
  if (placeId === undefined || type === undefined) return null;
  const { data } = await defaultInstance.get(
    `places/detail/users/${userSeq}/placeId/${placeId}/type/${type}`
  );
  return data;
};

/**
 * @param {number} userSeq : 사용자 번호
 * @param {string} sido : 시도명
 * @param {string} gugun : 구군명
 * @param {string} text : 장소명
 * @param {string} filter : 유형
 * GET : 검색 조건에 따라 리스트 정보를 가져온다.
 * @returns {places: [{placeId, name, address, score, thumbNailUrl, detail, latitude, longitude, type, like}], midLatitude, midLongitude}
 */
export const getPlaceList = async (
  userSeq: number,
  sido: any,
  gugun: any,
  text: string,
  filter: string,
  limit: number,
  offset: number
) => {
  // 시/도, 구/군, 검색어, 필터
  if (
    sido !== undefined &&
    gugun !== undefined &&
    text !== "" &&
    filter !== ""
  ) {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido.name}/gugun/${gugun.name}/text/${text}/filter/${filter}/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
  // 시/도, 구/군, 필터
  if (
    sido !== undefined &&
    gugun !== undefined &&
    text === "" &&
    filter !== ""
  ) {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido}/gugun/${gugun}/text/filter/${filter}/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
  // 시/도, 구/군, 검색어
  if (
    sido !== undefined &&
    gugun !== undefined &&
    text !== "" &&
    filter === ""
  ) {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido.name}/gugun/${gugun.name}/text/${text}/filter/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
  // 시/도, 구/군
  if (
    sido !== undefined &&
    gugun !== undefined &&
    text === "" &&
    filter === ""
  ) {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/${sido.name}/gugun/${gugun.name}/text/filter/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
  // 검색어, 필터
  if (
    sido === undefined &&
    gugun === undefined &&
    text !== "" &&
    filter !== ""
  ) {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/gugun/text/${text}/filter/${filter}/?limit=${limit}&offset=${offset}`
    );
    return data;
  }

  // 검색어
  if (
    sido === undefined &&
    gugun === undefined &&
    text !== "" &&
    filter === ""
  ) {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/gugun/text/${text}/filter/?limit=${limit}&offset=${offset}`
    );
    return data;
  }

  // 필터
  if (
    sido === undefined &&
    gugun === undefined &&
    text === "" &&
    filter !== ""
  ) {
    const { data } = await defaultInstance.get(
      `places/search/users/${userSeq}/sido/gugun/text/filter/${filter}/?limit=${limit}&offset=${offset}`
    );
    return data;
  }
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
