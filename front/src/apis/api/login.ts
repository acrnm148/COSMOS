import { defaultInstance } from "../utils"

// POST APIs

/**
 * POST : 카카오 로그인 완료 후 로그인 요청
 * @returns String accessToken, String refreshToken
 */
export const loginCosmos = async () =>{
    const accessToken = await defaultInstance.get("accessToken")
    return accessToken
}