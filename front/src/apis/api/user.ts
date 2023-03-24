import { authInstance,  } from "../utils"

// POST APIs

/**
 * POST : 카카오 로그인 완료 후 로그인 요청
 * @returns String accessToken, String refreshToken
 */
// export const loginCosmos = async () =>{
//     const accessToken = await defaultInstance.get("accessToken")
//     return accessToken
// }

// GET APIs

/**
 * GET : 마이페이지 접근시 유저 데이터 요청
 * @returns  <userInfo> : userInfo dictionary data
 */
//  export const getUserInfo = async () => {
//     const { data } = await authInstance.get("sido");
//     return data;
//   };