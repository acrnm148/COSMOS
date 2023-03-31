import React, { useEffect, useState } from "react";
import axios from "axios";
import { useRecoilState, useSetRecoilState } from "recoil";
// import { isLoggedInState,userSeqState, acTokenState, coupleIdState } from "../../recoil/states/UserState";
import { Navigate, useNavigate } from 'react-router';
import { loggedIn, LUser, userState } from "../../recoil/states/UserState";
import { invitedCoupleId } from "../../recoil/states/ServeyPageState";

declare const window: typeof globalThis & {
    Kakao: any;
    document:any;
  };
export default function KakaoLogin(){
  const JWT_EXPIRRY_TIME = 24 * 3600 * 1000
  const navigate = useNavigate();
  
  // userState recoil
  const [LoginUser, setLoginUser] = useRecoilState<LUser>(userState)
  
  // couple매칭으로 들어온 사람은 로그인 후 설문페이지로 이동시킴
  const [invited, setInvited] = useState(false)
  const [invitedId, x] = useRecoilState(invitedCoupleId)
  const [isLoggedIn, setIsLogin] = useRecoilState(loggedIn)

  if(invitedId){
    setInvited(true)
  }

  const params = new URLSearchParams(window.location.search);
  let code: any = params.get('code')
  useEffect(() => {
    cosmosLogin(code)
  })

  function cosmosLogin(code:string){
        axios({
          url : "https://j8e104.p.ssafy.io/api/accounts/auth/login/kakao",
          method: 'GET',
          params: {code},
          })
          .then((res:{data:any})=>{
            // 응답받은 userSeq 저장
            const us = res.data.userSeq
            setLoginUser({seq:us, isLoggedIn:true, acToken:res.data.accessToken, coupleId:res.data.coupleId})
            console.log('코스모스 로그인 성공', res)
            setIsLogin(true)
            onLoginSuccess(us) // 24시간 이후 자동으로 로그인 요청 반복
            if (invited){
              navigate('/servey')
            } else{
              navigate('/')
            }
          })
          .catch((err:any) => {
            console.log('코스모스 로그인 실패', err)
            // 카카오 로그아웃요청
            }
          )
    }
    const onLoginSuccess = (seq : number) =>{
      setTimeout(cosmosLogin, JWT_EXPIRRY_TIME - 60000)
    }
  // })
  return(
      <div>카카오로그인 코드: {code} </div>
  )
}