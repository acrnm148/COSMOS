import { AxiosAuthApi, defaultInstance } from "../utils";

// GET APIs

// 코스
/**
 * @param {number} userSeq : 유저 seq
 * GET : 찜한 코스 리스트 데이터를 가져온다.
 * @returns [] : 찜한 코스 리스트`
 */
export const getWishCourseList = async (userSeq: number, ac: string | null) => {
    const instance = AxiosAuthApi(process.env.REACT_APP_API_URL, ac, userSeq);
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
    const { data } = await defaultInstance.get(
        `places/users/${userSeq}?limit=10&offset=0`
    );
    return data;
};

// DELETE APIs

// 코스
/**
 * @param {number} courseId : 코스 id
 * DELETE : 찜한 코스를 찜 목록에서 삭제한다.
 */
export const deleteWishCourse = async (courseId: number) => {
    const { data } = await defaultInstance.delete(`courses/${courseId}`);
    return data;
};

// 장소
/**
 * @param {number} placeId : 장소 id
 * @param {number} userSeq : 유저 seq
 * DELETE : 찜한 장소를 찜 목록에서 삭제한다.
 */
interface params {
    placeId: number;
    userSeq: number;
}

export const deleteWishPlace = async ({ placeId, userSeq }: params) => {
    const { data } = await defaultInstance.delete(
        `places/${placeId}/users/${userSeq}`
    );
    return data;
};

// POST APIs
// 찜한 장소로 코스 생성
/**
 * @param {number} userSeq : 유저 seq
 * POST : 찜한 장소들로 코스를 생성한다.
 */
interface makeParams {
    userSeq: number;
    placeIds: number[];
    name: string;
}
export const wishMakeCourse = async ({
    userSeq,
    placeIds,
    name,
}: makeParams) => {
    const { data } = await defaultInstance.post(`/courses/users/${userSeq}`, {
        name: name,
        placeIds: placeIds,
    });
    return data;
};
