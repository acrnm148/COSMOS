import { useEffect } from "react";
import Cosmos from "../../assets/login/pinkCosmos.png"

declare const window: typeof globalThis & {
    Kakao: any;
  };

export default function Login(){
    useEffect(() => {
        if (!window.Kakao.isInitialized()){
            window.Kakao.init(process.env.REACT_APP_KAKAO)
        }
    })
    const KakaoLogin = () => {
        window.Kakao.Auth.authorize({
            redirectUri : process.env.REACT_APP_BASE_URL,
            // scope: "profile_nickname,profile_image,account_email"
        })
    }
    return(
        <>
            <div className="w-full flex flex-col content-center justify-center p-10 items-center">
                <img src={Cosmos} alt="" />
                <p className="text-sm">간편하게 코스모스를 시작해보세요</p>
                <button 
                    onClick={KakaoLogin}
                    className="h-12 w-5/6 rounded-md flex justify-center"
                >
                    <img src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg" width="222"
                alt="카카오 로그인 버튼" />
                </button>
                
            </div>
        </>
    )
}