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
      const [userInfo, setUserInfo] = useState<userInfo|null>(null)
      const [coupleInfo, setCoupleInfo] = useState<userInfo>()
      const navigate = useNavigate();
      let dispatch = useContext(UserDispatch);
      
      if ((LoginUser.seq > -1)&& (LoginUser.acToken)){
          console.log('로그인된유저', LoginUser.seq)
        }
        const {data} =  useQuery({
            queryKey: ["getUserInfo"],
            queryFn: () => getUserInfo(LoginUser.seq,dispatch)
          })
        console.log('data',data)
        // 유저정보 받아와서 마이페이지에서 표출할 정보로 가공
        if(data){
          setUserInfo({
            userId : data.userId,
            userName : data.userName,
            phoneNumber : data.phoneNumber,
            profileImageUrl : data.profileImageUrl,
            coupleYn : data.coupleYn,
            age: data.age,
            type1 : data.type1,
            type2 : data.type2,
            birthYear : data.birthYear,
            coupleUserId : data.coupleUserId
          })
        }

    

  function kakaoLogout() {
    // ac 지우기
    setLoginUser({...LoginUser, isLoggedIn:false, acToken:''})
    console.log('로그아웃 되었습니다')
    // window.Kakao.Auth.logout(() => {
      navigate("/")
    // });
  }
  return (
    <div className="h-screen">
      <div className="flex w-screen items-center content-center ">
        <div className="w-full flex m-auto flex-col justify-center md:w-5/6 lg:w-3/6">
          <div className="profile justify-center items-end flex mt-10 mb-4">
            <div className={"rounded-full w-16 h-16 max-w-[950px]" + (`bg-[url(${userInfo?.profileImageUrl})]`)}></div>
            <div className="flex text-lightMain items-end m-3">
              <div className="w-10 h-px bg-lightMain"></div>
              {userInfo?.coupleYn &&
                <>
                  <p className="text-sm">n일째 코스모스중</p>
                  <div className="w-10 h-px bg-lightMain"></div>
                  <div className="rounded-full bg-lightMain w-12 h-12"></div>
                </>
              }
            </div>
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
        </div>
        </div>
        </div>
    )
  }
