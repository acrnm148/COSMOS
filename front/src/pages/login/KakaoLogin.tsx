import React, { useEffect } from "react";
import axios from "axios";
import { useRecoilState } from "recoil";
import { userState,userSeqState } from "../../recoil/states/UserState";

declare const window: typeof globalThis & {
    Kakao: any;
  };
export default function KakaoLogin(){
  const [userSeq,setUserSeq] = useRecoilState(userSeqState);
  const JWT_EXPIRRY_TIME = 24 * 3600 * 1000
  
    useEffect(() => {
        let params = new URL(document.location.toString()).searchParams;
        let code = params.get("code");
        let grant_type = "authorization_code";
        let client_id = process.env.REACT_APP_KAKAO_CLIENT_ID;
        axios
          .post(
            `https://kauth.kakao.com/oauth/token?grant_type=${grant_type}&client_id=${client_id}&redirect_uri=${process.env.REACT_APP_BASE_URL}&code=${code}`,
            {
              headers: {
                "Content-type": "application/x-www-form-urlencoded;charset=utf-8",
              },
            }
          )
          .then((res: { data: { access_token: any; }; }) => {
            // sdk 를 못찾아서 초기화
            if (!window.Kakao.isInitialized()){
              window.Kakao.init(process.env.REACT_APP_KAKAO)
          }
          // 여기서 카카오 말고 res.data.access_token을 백엔드로 요청보내기
            window.Kakao.Auth.setAccessToken(res.data.access_token)
            window.Kakao.API.request({
              url: "/v2/user/me",
              success: function (response: any) {
                console.log('정보 받는 부분')
                console.log(response)
                let userEmail = response.kakao_account.email
                let userName = response.kakao_account.profile.nickname
                let userProfile = response.kakao_account.profile.profile_image_url
              },
              fail: function (error: any) {
                console.log(error)
              },
            })
            ///////// 우리 서버에 로그인 요청 보내는 부분
            const onRefresh = () =>{
              axios.post("/api/accounts/auth/login/kakao",{
                code : res.data.access_token
              })
              .then((res:any)=>{
                // 응답받은 userSeq 저장
                const getUserSeq = res.data.userId
                setUserSeq(getUserSeq)
                onLoginSuccess(getUserSeq)
              })
            }
            const onLoginSuccess = (getUserSeq: number | null) => {
              setTimeout(onRefresh, JWT_EXPIRRY_TIME - 60000)
            }
            })
            
          })
          return(
              <div>카카오 로그인 완료</div>
          )
}