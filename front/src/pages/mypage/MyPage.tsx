import axios from "axios"
import { useContext, useEffect, useState } from "react"
import { NavLink, useNavigate } from "react-router-dom"
import { useRecoilState } from "recoil"
import { LUser, userState } from "../../recoil/states/UserState"
import { getUserInfo  } from "../../apis/api/user"
import { useQuery } from "react-query"
import { UserDispatch } from "../../layouts/MainLayout"
import { backgroundImageGif, bgPngUrl, bgPngUrlTailwind, dateCateNames } from "../../recoil/states/ServeyPageState"
import { OnLoginSuccess } from "../login/KakaoLogin"
import Swal from "sweetalert2"

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
  coupleProfileImgUrl : string
  createTime : string | Date
}

declare const window: typeof globalThis & {
    Kakao: any;
  };
  
  export default function MyPage(){
      const [bgPng, setBgPng] = useRecoilState(bgPngUrlTailwind)
      const [backgroundImage, setBackgroundImage] = useRecoilState(backgroundImageGif)
      const [dateCate, setDateCateNames] =useRecoilState(dateCateNames)
      const [LoginUser, setLoginUser] = useRecoilState<LUser>(userState)
      const [userInfo, setUserInfo] = useState<USERINFORMATION|null>(null)
      const [coupleInfo, setCoupleInfo] = useState<USERINFORMATION>()
      const navigate = useNavigate();
      let dispatch = useContext(UserDispatch);
      
      // if ((LoginUser.seq > -1)&& (LoginUser.acToken)){
          // console.log('로그인된유저', LoginUser.acToken)
      //   }
        const {data} =  useQuery({
            queryKey: ["getUserInfo"],
            queryFn: () => getUserInfo(LoginUser.seq,dispatch)
        })
        
        // 유저정보 받아와서 마이페이지에서 표출할 정보로 가공
        const [user, setUser] = useRecoilState(userState)
        useEffect(()=>{
          // console.log(LoginUser)
          if(data){
            // console.log('유저', data)
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
              reviews : data.reviews,
              coupleProfileImgUrl : data.coupleProfileImgUrl,
              createTime : data.createTime
            })

          }
        },[data])

  // 로그아웃

  function logout(){
    Swal.fire({
      title: `로그아웃 하시겠습니까?`,
      icon: "warning",
      showCancelButton:true,
      cancelButtonColor: "#FF8E9E",
      cancelButtonText: "돌아가기",
      confirmButtonColor: "#D9D9D9", // confrim 버튼 색깔 지정
      confirmButtonText: "로그아웃", // confirm 버튼 텍스트 지정
    }).then((result) => {
      if (result.isConfirmed) {
        
        // 자동 로그인 중단
        OnLoginSuccess(user.seq, user.acToken, false)
        axios.get((`https://j8e104.p.ssafy.io/api/accounts/logout/${user.seq}`),
          {
            headers:{
              Authorization : 'baerer ' + user.acToken
            }
          }
              ).then((res)=>{
                console.log(res)
                setLoginUser({...user,acToken:'',seq:0, isLoggedIn:false, coupleId:"" })
              }
              ).catch((err)=>{
                console.log(err)
              })
            // 카카오 sdk 찾도록 초기화
            if (!window.Kakao.isInitialized()){
                window.Kakao.init(process.env.REACT_APP_KAKAO_LOGIN_JS_SUN)
            }
            navigate('/')
            window.Kakao.Auth.logout()
      } 
      else{
        //로그아웃취소
      }
    });
  }

  return (
    <div className="">
      <div className="flex w-screen items-center content-center ">
        <div className="w-full flex m-auto flex-col justify-center items-center md:w-5/6 lg:w-3/6 overflow-y-scroll mb-[1000px] overflow-x-hidden">
          <div className="profile justify-center items-end flex mt-10 ">
              {userInfo?.coupleYn === 'Y' ?
              <>
              <div className={"rounded-full w-16 h-16 max-w-[950px]"}>
                <img src={userInfo? userInfo.profileImgUrl : ""} className="w-full h-full rounded-full" alt="" />
              </div>
              <div className="flex text-lightMain items-end m-3">
                <div className="w-10 h-px bg-lightMain"></div>
                  <p className="text-sm">코스모스중</p>
                  <div className="w-10 h-px bg-lightMain"></div>
                <div className={"rounded-full w-16 h-16 max-w-[950px]"}>
                  <img src={userInfo? userInfo.coupleProfileImgUrl : ""} className="w-full h-full rounded-full" alt="" />
                </div>
              </div>
              </>
              :
              <div className={"rounded-lg w-28 h-28 max-w-[950px]"}>
                <img src={userInfo? userInfo.profileImgUrl : ""} className="w-full h-full rounded-lg" alt="" />
              </div>
              }
              </div>
              <div className="mb-2">{userInfo?.userName}</div>
          {userInfo?.type1 ?
            <div className="dateCategory w-full flex flex-col ">
              <div className={`flex dateCategory flex-col justify-end items-center w-full h-[40vh] bg-lightMain3 bg-center bg-cover bg-no-repeat ${backgroundImage[userInfo?.type1  as keyof typeof backgroundImage]}`}>
                <div className="h-full bg-zinc-500/30 w-full flex flex-col justify-center text-white items-center">
                  <p>1순위</p>
                  <p>{dateCate[userInfo?.type1  as keyof typeof dateCate][1]}</p>
                  <p><span>{dateCate[userInfo?.type1  as keyof typeof dateCate][0]}</span>형 코스모스</p>
                </div>
              </div>
              <div className={`h-24 bg-lightMain2 flex justify-center items-center`}>
                <div className="h-full w-full flex flex-col justify-center text-white items-center">
                2순위 
                <p><span>{dateCate[userInfo?.type2  as keyof typeof dateCate][0]}</span>형 코스모스</p>
                </div>
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
          <div
            onClick={logout} 
            className="mt-8 w-full cursor-pointer text-xl text-calendarDark bg-calendarGray px-8 py-4 h-16 rounded-lg flex justify-center hover:bg-lightMain3">로그아웃</div>
        </div>
        </div>
        </div>
    )
  }
