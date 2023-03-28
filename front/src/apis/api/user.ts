import { useContext } from "react";
import { UserDispatch } from "../../layouts/MainLayout";
import { AxiosAuthApi, defaultInstance } from "../utils"
// GET APIs

/**
 * GET : 마이페이지 접근시 유저 데이터 요청
 * @returns  <userInfo> : userInfo dictionary data
 */
 export const getUserInfo = async (seq:number, ac:string|null) => {
    const instance = AxiosAuthApi(process.env.REACT_APP_API_URL, ac)
    const {data} = await instance.get(`accounts/userInfo/${seq}`)
    return data
  };

/**
 * GET : 커플아이디 생성요청
 * @returns coupleId : numbers(len() === 10)
 * 
 */
export const getCoupleId = async() => {
    const {data} = await defaultInstance.get('makeCoupleId')
    return data
}

/**
 * POST : 커플 생성요청
 */
 export const makeCouple = async (coupleId:any, userSeq:number, coupleUserSeq:number) => {
   console.log('커플 생성요청', coupleId, userSeq, coupleUserSeq)
    const {data} = await defaultInstance.post(`couples/accept/${coupleId}`, {'userSeq':userSeq, 'coupleUserSeq':coupleUserSeq})
    return data
};

/**
 * POST : 유저 타입 저장
 */
export const postUserType = async (ac:string, type1:string, type2:string) =>{
  console.log('유저 타입 저장 요청')
  const instance = AxiosAuthApi(process.env.REACT_APP_API_URL, ac)
  const {data} = await instance.post(`couples/type`,{'type1':type1, 'type2':type2})
  return data
}
