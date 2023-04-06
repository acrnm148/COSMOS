import { invitedCoupleId, invitedUserId, serveyChoice, serveyPage } from "../../recoil/states/ServeyPageState";
import { useRecoilState, useRecoilValue } from "recoil";

import Servey1 from "./Servey1";
import Servey2 from "./Servey2";
import Servey3 from "./Servey3";
import Servey4 from "./Servey4";
import Servey5 from "./Servey5";
import Servey6 from "./Servey6";
import Servey7 from "./Servey7";
import Servey8 from "./Servey8";
import Servey9 from "./Servey9";
import React, { useEffect, useState } from "react";
import { Navigate, useNavigate, useParams } from "react-router-dom";
import { loggedIn, userState } from "../../recoil/states/UserState";

export default function ServeyPage() {
  const [serveyPg, setServeyPage] = useRecoilState(serveyPage);
  const serveyChoices = useRecoilState(serveyChoice);
  const [loginUser, setLoginUser] = useRecoilState(userState)

  const navigate = useNavigate();
  const [cate, setCate] = useState("");
  const [cateNumCode, setCateNumCode] = useState("");
  const [isLoggedIn, setLoggedIn] = useRecoilState(loggedIn)

  const [invitedId, setInvitedCoupleId] = useRecoilState(invitedCoupleId)
  const [x, setInvitedUserId] = useRecoilState(invitedUserId)
  const param = useParams()
  let coupleId: string
  // 생성된 유저 coupleId recoil에 저장
  const [user, setUser] = useRecoilState(userState)
  
  // 설문조사 결과
  useEffect(() => {
    if((param.coupleId) && (param.inviteId)){
      // 커플매칭을 위해 들어온사람
      // 1. recoil에 커플매칭 대기상태 표시, 커플Id저장
      coupleId = param.coupleId
      setInvitedCoupleId(coupleId)
      setInvitedUserId(param.inviteId)
      // 2. 로그인 후 설문페이지로 돌아오도록 
    }
    // 로그인한 상태인지 확인
    if (serveyPg === 10) {
      // console.log("여기는 10페이지");
      // console.log("serveyChoices", serveyChoices); // {1: 'J', 2: 'O', 3: 'Y', 4: 'T', 5: 'E', 6: 'O', 7: 'O', 8: 'Y', 9: 'E'}
      // console.log("typeof(serveyChoices)", typeof serveyChoices);
      // serveyChoices에 담긴 알파벳 값 카운트 해서 result 생성
      const sc = serveyChoices[0];
      let result = { E: 0, J: 0, A: 0, O: 0, Y: 0, T: 0 };
      for (const [key, value] of Object.entries(sc)) {
        if (value === "E") {
          result["E"] += 1;
        } else if (value === "J") {
          result["J"] += 1;
        } else if (value === "A") {
          result["A"] += 1;
        } else if (value === "O") {
          result["O"] += 1;
        } else if (value === "Y") {
          result["Y"] += 1;
        } else if (value === "T") {
          result["T"] += 1;
        }
      }
      console.log("result", result);
      setCate(() => {
        let firstLetter = result["E"] > result["J"] ? "E" : "J";
        let firstCode = result["E"] > result["J"] ? result["E"] : result["J"];

        let secondLetter = result["O"] > result["A"] ? "O" : "A";
        let secondCode = result["O"] > result["A"] ? result["O"] : result["A"];

        let thirdLetter = result["Y"] > result["T"] ? "Y" : "T";
        let thirdCode = result["Y"] > result["T"] ? result["Y"] : result["T"];

        setCateNumCode(
          String(firstCode) + String(secondCode) + String(thirdCode)
        );
        return firstLetter + secondLetter + thirdLetter;
      });
    }
  }, [serveyPg]);
  useEffect(() => {
    if (serveyPg === 10 && cate && cateNumCode) {
      setTimeout(() => {
        navigate(`/servey/result/${cate}/${cateNumCode}`);
      }, 2000);
    }
  }, [cate, cateNumCode]);

  function submit() {
    setServeyPage(serveyPg + 1);
  }
  return (
    <>
      <div className="h-screen w-full max-w-[500px] m-auto p-6 bg-darkBackground place-content-center text-darkMain m-auto">
        <div className="flex content-center w-full items-start h-full">
          { !isLoggedIn ?
            <div className="flex h-full flex-col">
              <span className="font-baloo text-base">
                설문을 기반으로 데이트코스를 맞춤 추천해드려요.
              </span>
              <a href={`https://kauth.kakao.com/oauth/authorize?client_id=097d883a03c0da953d919d990701da5f&redirect_uri=https://j8e104.p.ssafy.io/login/oauth2&response_type=code`}>
                <button
                  onClick={submit}
                  className="w-full flex h-12 border-solid border-2 border-darkMain justify-center p-3 text-center rounded-lg w-full 
                  hover:bg-darkMain hover:text-white
                  "
                  >
                  카카오 3초 로그인하고 설문하기
                </button>
                </a>
            </div>
            :
            <>
            {serveyPg === 0 && (
              <div className="flex h-full flex-col">
                <span className="font-baloo text-base">
                  설문을 기반으로 데이트코스를 맞춤 추천해드려요.
                </span>
                <button
                  onClick={submit}
                  className="w-full flex h-12 border-solid border-2 border-darkMain justify-center p-3 text-center rounded-lg w-full 
                  hover:bg-darkMain hover:text-white
                  "
                  >
                  설문 시작하기
                </button>
              </div>
            )}
            {serveyPg === 1 && <Servey1 />}
            {serveyPg === 2 && <Servey2 />}
            {serveyPg === 3 && <Servey3 />}
            {serveyPg === 4 && <Servey4 />}
            {serveyPg === 5 && <Servey5 />}
            {serveyPg === 6 && <Servey6 />}
            {serveyPg === 7 && <Servey7 />}
            {serveyPg === 8 && <Servey8 />}
            {serveyPg === 9 && <Servey9 />}
            {serveyPg === 10 && (
              <div className="flex w-screen h-screen justify-center items-center flex-col">
                <iframe src="https://embed.lottiefiles.com/animation/101474"></iframe>
                <h1 className="font-bold font-baloo text-2xl mb-3">
                  데이트 취향설문 결과 분석중
                </h1>
              </div>
            )}
            </>
          }
        </div>
      </div>
    </>
  );
}
