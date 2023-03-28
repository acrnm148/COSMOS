import { AxiosAuthApi, defaultInstance } from "../utils";

// GET APIs

// 코스
/**
 * @param {number} userSeq : 유저 seq
 * GET : 찜한 코스 리스트 데이터를 가져온다.
 * @returns [] : 찜한 코스 리스트`
 */
export const getWishCourseList = async (userSeq: number, ac: string | null) => {
    const instance = AxiosAuthApi(process.env.REACT_APP_API_URL, ac);
    const { data } = await instance.get(`courses/users/${userSeq}`);
    return data;
};

// 장소
/**
 * @param {number} userSeq : 유저 seq
 * GET : 찜한 장소 리스트 데이터를 가져온다.
 * @returns [] : 찜한 장소 id, name, score, address, detail, thumbNailUrl
 */
export const getWishPlaceList = async (userSeq: number) => {
    const { data } = await defaultInstance.get(`places/users/${userSeq}`);
    return data;
};

// DELETE APIs
/**
 * @param {number} courseId : 코스 id
 * DELETE : 찜한 코스를 찜 목록에서 삭제한다.
 */
export const deleteWishCourse = async (courseId: number) => {
    const { data } = await defaultInstance.delete(`courses/${courseId}`);
    return data;
};
