import { defaultInstance } from "../utils"

/**
 * GET : 커플 전체사진 조회
 * @returns 사진 리스트
 * 
 */
export const getPhotos = async(coupleId:string|number, limit:number, offset:number) => {
    const {data} = await defaultInstance.get(`pictures/${coupleId}`,{
        params:{
            limit : limit,
            offset : offset
        }
    })
    return data
}


/**
 * GET : 커플 일정 월별 조회
 * @returns 월 일정
 * 
 */
export const getMonthSchedule = async(coupleId:string, day:string) => {
    const {data} = await defaultInstance.get(`plans/${coupleId}/month/${day}`)
    return data
}

/**
 * GET : 장소별 커플 리뷰 조회
 * @returns 장소에 대한 리뷰 리스트
 * 
 */
export const getPlaceCoupleReview = async(userSeq:number, coupleId : number, placeId : number, limit:number, offset:number) => {
    const {data} = await defaultInstance.get(`reviews/users/${userSeq}/coupleId/places/${placeId}`, {
        params : {
            limit : limit,
            offset : offset
        }
    })
    return data
}