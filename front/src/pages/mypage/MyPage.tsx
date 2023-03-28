import axios from "axios"
import { useContext, useEffect, useState } from "react"
import { NavLink, useNavigate } from "react-router-dom"
import { useRecoilState } from "recoil"
import { LUser, userState } from "../../recoil/states/UserState"
import { getUserInfo  } from "../../apis/api/user"
import { useQuery } from "react-query"
import { UserDispatch } from "../../layouts/MainLayout"

interface USERINFORMATION {
  userId: number
  userName: string
  phoneNumber: string | null
  profileImgUrl: string
  coupleYn: string
  age: number
  type1: string
  type2: string
  birthYear: number
  coupleId : number | null
  coupleUserId: number
  reviews: any
}

declare const window: typeof globalThis & {
    Kakao: any;
  };
  
  export default function MyPage(){
      const [LoginUser, setLoginUser] = useRecoilState<LUser>(userState)
      const [userInfo, setUserInfo] = useState<USERINFORMATION|null>(null)
      const [coupleInfo, setCoupleInfo] = useState<USERINFORMATION>()
      const navigate = useNavigate();
      let dispatch = useContext(UserDispatch);
      
      // if ((LoginUser.seq > -1)&& (LoginUser.acToken)){
      //     console.log('로그인된유저', LoginUser.seq)
      //   }
        const {data} =  useQuery({
            queryKey: ["getUserInfo"],
            queryFn: () => getUserInfo(LoginUser.seq,dispatch)
        })
        
        // 유저정보 받아와서 마이페이지에서 표출할 정보로 가공
        // 생성된 유저 coupleId recoil에 저장
        const [user, setUser] = useRecoilState(userState)
        useEffect(()=>{
          if(data){
            console.log('유저', data)
            setUserInfo({
              userId : data.userId,
              userName : data.userName,
              phoneNumber : data.phoneNumber,
              profileImgUrl : data.profileImgUrl,
              coupleYn : data.coupleYn,
              age: data.age,
              type1 : data.type1,
              type2 : data.type2,
              birthYear : data.birthYear,
              coupleId : data.coupleId,
              coupleUserId : data.coupleUserId,
              reviews : data.reviews
            })
          }
        },[data])

    



  return (
    <div className="h-screen">
      <div className="flex w-screen items-center content-center ">
        <div className="w-full flex m-auto flex-col justify-center md:w-5/6 lg:w-3/6">
          <div className="profile justify-center items-end flex mt-10 mb-4">
          <div className={"rounded-full w-16 h-16 max-w-[950px]"}>
            <img src={userInfo? userInfo.profileImgUrl : ""} className="w-full h-full rounded-full" alt="" />
          </div>
            <div className="flex text-lightMain items-end m-3">
              {userInfo?.coupleYn === 'Y' &&
                <>
                <div className="w-10 h-px bg-lightMain"></div>
                  <p className="text-sm">n일째 코스모스중</p>
                  <div className="w-10 h-px bg-lightMain"></div>
                  <div className="rounded-full bg-lightMain w-12 h-12"></div>
                </>
              }
            </div>
          </div>
          {userInfo?.coupleYn === 'Y' ?
            <div className="dateCategory w-full flex flex-col ">
              <div className="dateCategory w-full h-60 bg-lightMain3 p-2">
                {" "}
                1순위 : {userInfo?.type1}
              </div>
              <div className="h-20 bg-lightMain4 flex justify-center items-center">
                2순위 : {userInfo?.type2}형 코스모스
              </div>
              <div className="h-20 w-full border-solid border-2 border-lightMain4  flex justify-center items-center">
                <NavLink to="/servey" >취향설문 다시하기</NavLink>
              </div>
            </div>
            :
            <div className="dateCategory w-full h-60 flex flex-col justify-center items-center">
                <NavLink to="/servey" >
                  <div className=" text-xl text-white bg-lightMain2 px-8 py-4 rounded-lg">데이트 취향 알아보기</div>
                </NavLink>
            </div>
          }
          {/* <div
            className="cursor-pointer bg-lightMain p-10 text-white"
          >
            로그아웃
          </div> */}
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
