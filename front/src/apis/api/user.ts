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
 * POST : 마이페이지 접근시 유저 데이터 요청
 */
 export const makeCouple = async (coupleId:number|string, userSeq:number, coupleUserSeq:number) => {
    const {data} = await defaultInstance.post(`couples/accept/${Number(coupleId)}`, {'userSeq':userSeq, 'coupleUserSeq':coupleUserSeq})
  return data
};

