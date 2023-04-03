import { AxiosAuthApi, defaultInstance } from "../utils";

// GET APIs
// 알림
/**
 * @param {number} userSeq 유저 seq
 * GET : 알림 리스트 데이터를 가져온다.
 * @returns [] : 알림 리스트
 */
export const getAlarmList = async (userSeq: number) => {
    const { data } = await defaultInstance.get(`noti/list/${userSeq}`);
    return data;
};
