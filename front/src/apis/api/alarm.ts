import { AxiosAuthApi, defaultInstance } from "../utils";

// GET APIs
// 알림
/**
 * @param {number} userSeq : 유저 seq
 * GET : 알림 리스트 데이터를 가져온다.
 * @returns [] : 알림 리스트
 */
interface alarmListParam {
    userSeq: number;
    clicked: number;
}
export const getAlarmList = async ({ userSeq, clicked }: alarmListParam) => {
    const { data } = await defaultInstance.get(
        `noti/list/${userSeq}/clicked/${clicked}`
    );
    return data;
};

// 안읽은 알림 개수
/**
 * @param {number} userSeq : 유저 seq
 * GET : 안읽은 알림 개수를 가져온다.
 * @returns [] : cnt
 */
export const getUnReadAlarm = async (userSeq: number) => {
    const { data } = await defaultInstance.get(`noti/unread/${userSeq}`);
    return data;
};

// DELETE APIs

// 알림 개별 삭제
/**
 * @param {number} userSeq : 유저 seq
 * @param {number} notiId : 알람 id
 * DELETE : 알림을 개별 삭제한다.
 */
interface alarmParam {
    userSeq: number;
    notiId: number;
    ac: string | null;
}
export const deleteAlarm = async ({ userSeq, notiId, ac }: alarmParam) => {
    const instance = AxiosAuthApi(process.env.REACT_APP_API_URL, ac, userSeq);
    const { data } = await instance.delete(`noti/del/${userSeq}/${notiId}`);
    return data;
};

// 알림 전체 삭제
/**
 * @param {number} userSeq : 유저 seq
 * DELETE : 알림을 전체 삭제한다.
 */
interface delAllParam {
    userSeq: number;
    ac: string | null;
}
export const deleteAll = async ({ userSeq, ac }: delAllParam) => {
    const instance = AxiosAuthApi(process.env.REACT_APP_API_URL, ac, userSeq);
    const { data } = await instance.delete(`noti/delAll/${userSeq}`);
    return data;
};
