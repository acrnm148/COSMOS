import { defaultInstance } from "../utils";

/**
 * GET : 일별 코스조회
 * @returns  <userInfo> : userInfo dictionary data
 */
interface params {
    coupleId : number|string,
    date : string
}
export const getDayCourse = async (coupleId:string, date:string) => {
    console.log(coupleId, date)
    const {data} = await defaultInstance.get(`plans/${coupleId}/day/${date}`)
    console.log(data)
    return data
  };