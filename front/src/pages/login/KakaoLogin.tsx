import React, { useEffect } from "react";
import axios from "axios";

declare const window: typeof globalThis & {
    Kakao: any;
  };
export default function KakaoLogin(){
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
            console.log(res);
            window.Kakao.Auth.setAccessToken(res.data.access_token)
            window.Kakao.API.request({
              url: "/v2/user/me",
              success: function (response: any) {
                console.log(response)
              },
              fail: function (error: any) {
                console.log(error)
              },
            })
            ///////// 우리 서버에 로그인 요청 보내는 부분
            // api
            //   .post("/api/user/account/login/kakao", {
            //     accessToken: res.data.access_token,
            //   })
            //   .then((res: any) => {
            //     console.log(res);
            //     })
            })
          })
          return(
              <div>카카오 로그인 완료</div>
          )
}