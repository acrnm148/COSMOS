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
