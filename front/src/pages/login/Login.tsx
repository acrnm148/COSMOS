import Cosmos from "../../assets/login/pinkCosmos.png";
import DarkCosmos from "../../assets/login/darkCosmos.png";
import { useRecoilState } from "recoil";
import React from "react";
import { darkMode } from "../../recoil/states/UserState";
export default function Login() {
  const isDark = useRecoilState(darkMode);
  return (
    <>
      <div className="w-full h-screen flex flex-col content-center justify-center p-10 items-center">
        {isDark[0] ? (
          <img src={DarkCosmos} alt="" />
        ) : (
          <img src={Cosmos} alt="" />
        )}
        <p className="text-sm my-10 dark:text-white">
          간편하게 코스모스를 시작해보세요!
        </p>
        <a
        // https://j8e104.p.ssafy.io/login/oauth2
          // href={`https://kauth.kakao.com/oauth/authorize?client_id=097d883a03c0da953d919d990701da5f&redirect_uri=https://j8e104.p.ssafy.io/login/oauth2&response_type=code`}
          href={`https://kauth.kakao.com/oauth/authorize?client_id=${process.env.REACT_APP_KAKAO_LOGIN_CLIENT_ID}&redirect_uri=${process.env.REACT_APP_KAKAO_LOGIN_LOCAL}&response_type=code`}
        >
          <button className="h-12 w-5/6 rounded-md flex justify-center m-auto">
            <img
              src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
              width="222"
              alt="카카오 로그인 버튼"
            />
          </button>
        </a>
      </div>
    </>
  );
}
