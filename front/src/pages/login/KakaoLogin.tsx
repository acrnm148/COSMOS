import React, { useEffect } from "react";
import axios from "axios";
import { useRecoilState, useSetRecoilState } from "recoil";
// import { isLoggedInState,userSeqState, acTokenState, coupleIdState } from "../../recoil/states/UserState";
import { useNavigate } from 'react-router';
import { useMutation, useQuery } from "react-query";
import { LUser, userState } from "../../recoil/states/UserState";

declare const window: typeof globalThis & {
    Kakao: any;
    document:any;
  };
export default function KakaoLogin(){
  const JWT_EXPIRRY_TIME = 24 * 3600 * 1000
  const navigate = useNavigate();
  
  // userState recoil
  const [LoginUser, setLoginUser] = useRecoilState<LUser>(userState)
  
  const params = new URLSearchParams(window.location.search);
  let code: any = params.get('code')
  useEffect(() => {
    // console.log('heyhyehey 카카오로그인 들어옴', code)
    cosmosLogin(code)
  })
        async function cosmosLogin(code:string){
            axios({
              url : "https://j8e104.p.ssafy.io/api/accounts/auth/login/kakao",
              method: 'GET',
              params: {code},
              })
              .then((res:any)=>{
                // 응답받은 userSeq 저장
                const us = res.data.userSeq
                setLoginUser((user)=>({
                  ...{seq : us, 
                    isLoggedIn : true, 
                    acToken: res.data.accessToken, 
                    coupleId: res.data.coupleId}
                }))


                console.log('코스모스 로그인 성공', res.data)
                return navigate("/")
              })
              .catch((err:any) => {
                console.log('코스모스 로그인 실패', err)
              })
        }
      // })
      return(
          <div>카카오로그인 코드: {code} </div>
      )
}