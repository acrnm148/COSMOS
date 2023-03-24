import axios from "axios";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userSeqState } from "../../recoil/states/UserState";

interface userInfo {
  userId: number;
  userName: string;
  phoneNumber: string;
  profileImageUrl: string;
  coupleYn: string;
  age: number;
  type1: string;
  type2: string;
  birthYear: number;
  coupleUserId: number;
}

declare const window: typeof globalThis & {
  Kakao: any;
};

export default function MyPage() {
  const userSeq = useRecoilState(userSeqState);
  const [userInfo, setUserInfo] = useState<userInfo>();
  const [coupleInfo, setCoupleInfo] = useState<userInfo>();
  const navigate = useNavigate();
  useEffect(() => {
    if (userSeq) {
      // console.log('로그인된유저', userSeq)
      // const bringUser = (seq:number, me:string) =>{
      //     axios.get((`/api/accounts/userInfo/${seq}`))
      //         .then((res) =>{
      //             if(me==='me'){setUserInfo( res.data )}
      //             else {setCoupleInfo(res.data)}
      //             if((!coupleInfo) && (userInfo?.coupleUserId)){
      //                 bringUser(userInfo.coupleUserId, 'you')
      //             }
      //         })
      //         .catch((err)=>{
      //             console.log('err', err)
      //         })
      // }
      // bringUser(Number(userSeq), 'me')
    }
  });

  // l64l1viXs2NeolymgS89Ptyh0kCfP_ZfLNdhhb5uCj1y6gAAAYcHIpJS
  function deleteCookie() {
    document.cookie =
      "authorize-access-token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;";
  }
  function kakaoLogout() {
    // if (!window.Kakao.isInitialized()){
    //     window.Kakao.init(process.env.REACT_APP_KAKAO)
    // }
    if (window.Kakao.Auth.getAccessToken()) {
      console.log("카카오 엑세스 토큰", window.Kakao.Auth.getAccessToken());
    }
    window.Kakao.Auth.logout(() => {
      navigate("/");
    });
  }
  return (
    <div className="h-screen">
      <div className="flex w-screen items-center content-center ">
        <div className="w-full flex m-auto flex-col justify-center md:w-5/6 lg:w-3/6">
          <div className="profile justify-center items-end flex mt-10 mb-4">
            <div className="rounded-full bg-lightMain w-16 h-16 max-w-[950px]"></div>
            <div className="flex text-lightMain items-end m-3">
              <div className="w-10 h-px bg-lightMain"></div>
              <p className="text-sm">n일째 코스모스중</p>
              <div className="w-10 h-px bg-lightMain"></div>
            </div>
            <div className="rounded-full bg-lightMain w-12 h-12"></div>
          </div>
          <div className="dateCategory w-full flex flex-col">
            <div className="dateCategory w-full h-60 bg-lightMain3">
              {" "}
              1순위 : {userInfo?.type1}
            </div>
            <div className="h-20 bg-lightMain4 flex justify-center items-center">
              2순위 : {userInfo?.type2}형 코스모스
            </div>
            <div className="h-20 w-full border-solid border-2 border-lightMain4  flex justify-center items-center">
              취향설문 다시하기
            </div>
          </div>
          <div
            onClick={kakaoLogout}
            className="cursor-pointer bg-lightMain p-10 text-white"
          >
            로그아웃
          </div>
          <div className="recent w-full m-2">
            <div className="ml-4 mr-4 text-lightMain2 font-bold">
              최근 본 내역
            </div>
            <div className="recentItem flex justify-around">
              {["일광당", "삼락생태공원", "부산현대미술관"].map((item) => {
                return (
                  <div className="w-1/3">
                    <div className="m-2 bg-lightMain4 h-28 rounded-md md:h-10rem">
                      이미지
                    </div>
                    <div className="m-2 text-sm font-bold">{item}</div>
                  </div>
                );
              })}
            </div>
          </div>
          <div onClick={() => kakaoLogout()} className="cursor-pointer">
            로그아웃
          </div>
        </div>
      </div>
    </div>
  );
}
