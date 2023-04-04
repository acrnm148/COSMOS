import { AxiosAuthApi, defaultInstance } from "../utils";

// GET APIs
// 알림
/**
 * @param {number} userSeq : 유저 seq
 * GET : 알림 리스트 데이터를 가져온다.
 * @returns [] : 알림 리스트
 */
export const getAlarmList = async (userSeq: number) => {
    const { data } = await defaultInstance.get(`noti/list/${userSeq}`);
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
/**
 * @param {number} userSeq : 유저 seq
 * @param {number} notiId : 알람 id
 * DELETE : 찜한 코스를 찜 목록에서 삭제한다.
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
