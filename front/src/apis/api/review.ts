import { AxiosAuthApi } from "../utils"

/**
 * POST : 리뷰 생성
 * 
*/
interface params {
    review : any,
    ac : string,
    userSeq :number
}
export const postReview = async ({review, ac, userSeq}:params) => {
    const instance = AxiosAuthApi(process.env.REACT_APP_API_URL, ac, userSeq)
    const {data} = await instance.post(`reviews/users/${userSeq}`, review)
    console.log('리뷰생성 리턴데이터', data)
    return data
}


/**
 * PUT : 리뷰 수정
 * 
*/
interface prms {
    review : any,
    ac : string,
    userSeq :number,
    reviewId: any
}
export const putReview = async ({review, ac, userSeq, reviewId}:prms) => {
    const instance = AxiosAuthApi(process.env.REACT_APP_API_URL, ac, userSeq)
    const {data} = await instance.put(`reviews/${reviewId}/users/${userSeq}`, review)
    console.log('리뷰수정 리턴데이터', data)
    return data
}