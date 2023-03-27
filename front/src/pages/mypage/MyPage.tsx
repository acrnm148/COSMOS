import axios from "axios"
import { useContext, useEffect, useState } from "react"
import { useNavigate } from "react-router-dom"
import { useRecoilState } from "recoil"
import { LUser, userState } from "../../recoil/states/UserState"
import { getUserInfo  } from "../../apis/api/user"
import { useQuery } from "react-query"
import { UserDispatch } from "../../layouts/MainLayout"

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
  
  export default function MyPage(){
      const [LoginUser, setLoginUser] = useRecoilState<LUser>(userState)
      const [userInfo, setUserInfo] = useState<userInfo>()
      const [coupleInfo, setCoupleInfo] = useState<userInfo>()
      const navigate = useNavigate();
      let dispatch = useContext(UserDispatch);
      const {data} =  useQuery({
          queryKey: ["getUserInfo"],
          queryFn: () => getUserInfo(LoginUser.seq,dispatch)
        })
  
        useEffect(()=>{
            if (LoginUser.seq > -1){
                console.log('로그인된유저', LoginUser.seq)
                console.log('data',data)
                
                // const bringUser = (seq:number, me:string) =>{
                //     axios.get((`https://j8e104.p.ssafy.io/api/accounts/userInfo/${seq}`), 
                //     )
                //         .then((res) =>{
                //             if(me === 'me'){setUserInfo( res.data )}
                //             else {setCoupleInfo(res.data)}
    
                //             if((!coupleInfo) && (userInfo?.coupleUserId)){
                //                 bringUser(userInfo.coupleUserId, 'you')
                //             }
                //         })
                //         .catch((err)=>{
                //             console.log('err', err)
                //         })
                // }
                // bringUser(Number(LoginUser.seq), 'me')
            } 
        })
    

    function kakaoLogout(){
        // if (!window.Kakao.isInitialized()){
        //     window.Kakao.init(process.env.REACT_APP_KAKAO)
        // }
        if(window.Kakao.Auth.getAccessToken()){
            console.log('카카오 엑세스 토큰', window.Kakao.Auth.getAccessToken())
        }
        window.Kakao.Auth.logout(()=>{
            navigate("/")
        })
            
    }
  {/* function kakaoLogout() {
    // if (!window.Kakao.isInitialized()){
    //     window.Kakao.init(process.env.REACT_APP_KAKAO)
    // } */}
    if (window.Kakao.Auth.getAccessToken()) {
      console.log("카카오 엑세스 토큰", window.Kakao.Auth.getAccessToken());
    }
    window.Kakao.Auth.logout(() => {
      navigate("/")
    });
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
    )
  }
