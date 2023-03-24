import { defaultInstance } from "../utils";

// GET APIs

/**
 * @param {number} userSeq : 유저 seq
 * GET : 찜한 장소 리스트 데이터를 가져온다.
 * @returns [] : 찜한 장소 id, name, score, address, detail, thumbNailUrl
 */
export const getWishPlaceList = async (userSeq: number) => {
    const { data } = await defaultInstance.get(`/places/users/${userSeq}`);
    return data;
};

/**
 * @param {number} userSeq : 유저 seq
 * GET : 찜한 코스 리스트 데이터를 가져온다.
 * @returns [] : 찜한 코스 리스트
 */
export const getWishCourseList = async (userSeq: number) => {
    const { data } = await defaultInstance.get(`/courses/users/${userSeq}`);
};
