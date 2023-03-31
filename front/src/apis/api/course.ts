import { AxiosAuthApi, defaultInstance } from "../utils";

/**
 * GET : 일별 코스조회
 * @returns  <userInfo> : userInfo dictionary data
 */
export const getDayCourse = async (coupleId:string, date:string) => {
    console.log(coupleId, date)
    const {data} = await defaultInstance.get(`plans/${coupleId}/day/${date}`)
    console.log(data)
    return data
};

/**
 * POST : 일정 생성
 * 
*/
interface params {
    schedule : any,
    ac : string
}
export const postSchedule = async ({schedule, ac}:params) => {
    const instance = AxiosAuthApi(process.env.REACT_APP_API_URL, ac)
    const {data} = await instance.post('plans', schedule)
    console.log('일정생성 리턴데이터', data)
    return data
}