import axios from "axios"
import React, { useContext, useEffect, useState } from "react"
import { NavLink, useNavigate } from "react-router-dom"
import { useRecoilState } from "recoil"
import { LUser, darkMode, userState } from "../../recoil/states/UserState"
import { getUserInfo  } from "../../apis/api/user"
import { useQuery } from "react-query"
import { UserDispatch } from "../../layouts/MainLayout"
import { backgroundImageGif, bgPngUrl, bgPngUrlTailwind, dateCateNames } from "../../recoil/states/ServeyPageState"
import { onLoginSuccess } from "../login/KakaoLogin"
import Swal from "sweetalert2"
import { COURSE } from "../schedule/DaySchedulePage"
import { getWishCourseList, getWishPlaceList } from "../../apis/api/wish"

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
interface Place{
  placeId:number,
  name : string,
  thumbNailUrl : string
}
  export default function MyPage(){
    const [bgPng, setBgPng] = useRecoilState(bgPngUrlTailwind)
    const [backgroundImage, setBackgroundImage] = useRecoilState(backgroundImageGif)
    const [dateCate, setDateCateNames] =useRecoilState(dateCateNames)
    const [LoginUser, setLoginUser] = useRecoilState<LUser>(userState)
    const [userInfo, setUserInfo] = useState<USERINFORMATION|null>(null)
    const [coupleInfo, setCoupleInfo] = useState<USERINFORMATION>()
    const navigate = useNavigate();
    const [isDark, setIsDark] = useRecoilState(darkMode)
    console.log(isDark)
    
        const {data} =  useQuery({
            queryKey: ["getUserInfo"],
            queryFn: () => getUserInfo(LoginUser.seq,LoginUser.acToken)
        })
        
        
        // 유저정보 받아와서 마이페이지에서 표출할 정보로 가공
        const [user, setUser] = useRecoilState(userState)
        useEffect(()=>{
          if(data){
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
          console.log('data in mypage', data)
          console.log('userInfo', userInfo)
        },[data])

    // 찜한코스
    const [wishPlaces, setWishPlaces] =useState<Place[]>([])
    // 찜 코스 불러오기
    const wish = useQuery({
        queryKey: ["getWishPlaceList", user.acToken], // authapi 요청시 acToken 변
        queryFn: () => getWishPlaceList(user.seq),
      });
  
    useEffect(()=>{
     if(wish.data){
        // console.log(wish.data, '찜한장소')
        let temptemp:Place[] = []
        for (let i = 0; i < 3; i ++){
          if (wish.data[i]){
            let temp = {placeId:wish.data[i].placeId,name:wish.data[i].name, thumbNailUrl:wish.data[i].thumbNailUrl}
            temptemp.push(temp)
          } 
        }
        setWishPlaces(temptemp)
     }   
    },[wish.data])

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
        onLoginSuccess(user.seq, user.acToken, false, user.coupleId, setUser)
        axios.get((`https://j8e104.p.ssafy.io/api/accounts/logout/${user.seq}`),
          {
            headers:{
              Authorization : 'baerer ' + user.acToken
            }
          }
              ).then((res)=>{
                // console.log(res)
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
  function deleteCouple(){
    Swal.fire({
      title: `커플을 끊으시겠습니까?`,
      icon: "warning",
      showCancelButton:true,
      cancelButtonColor: "#FF8E9E",
      cancelButtonText: "돌아가기",
      confirmButtonColor: "#D9D9D9", // confrim 버튼 색깔 지정
      confirmButtonText: "커플끊기", // confirm 버튼 텍스트 지정
    }).then((result) => {
      if (result.isConfirmed) {
                axios.delete((`https://j8e104.p.ssafy.io/api/couples/${user.coupleId}`),
              ).then((res)=>{
                console.log(res)
                setLoginUser({...user, coupleId:"" })
                // getUserInfo(LoginUser.seq,dispatch)
              }
              ).catch((err)=>{
                console.log(err)
              })
      } 
    });
  }
  const th = 3
  return (
    <div className="h-full w-screen">
      <div className="flex items-center content-center mx-4">
        <div className="w-full flex m-auto flex-col justify-center items-center  md:w-5/6 lg:w-3/6 overflow-x-hidden mb-[10vh]">
          <div className="profile justify-center items-end flex mt-5 mb-2 mt-2 " >
              {userInfo?.coupleYn === 'Y' ?
              <>
              <div className={"rounded-full w-[14vw] h-[14vw] hover:w-[15vw] hover:h-[15vw] max-w-[950px]"}>
                <img src={userInfo? userInfo.profileImgUrl : ""} className="w-full h-full rounded-full" alt="" />
              </div>
              <div className="flex text-lightMain items-end m-3">
                <div className="w-10 h-px bg-lightMain"></div>
                  <p className="text-sm">코스모스중</p>
                  <div className="w-10 h-px bg-lightMain"></div>
              </div>
                <div className={"rounded-full  w-[14vw] h-[14vw] hover:w-[15vw] hover:h-[15vw] max-w-[950px]"}>
                  <img src={userInfo? userInfo.coupleProfileImgUrl : ""} className="w-full h-full rounded-full" alt="" />
                </div>
              </>
              :
              <div className={"rounded-full  w-[24vw] h-[24vw] hover:w-[26vw] hover:h-[26vw] max-w-[950px] mb-2"}>
                <img src={userInfo? userInfo.profileImgUrl : ""} className="w-full h-full rounded-full" alt="" />
              </div>
              }
              </div>
          {userInfo?.type1 ?
            <div className="dateCategory w-full flex flex-col ">
              <div className={`flex dateCategory flex-col justify-end items-center w-full h-[40vh] bg-lightMain3 bg-center bg-cover bg-no-repeat ${backgroundImage[userInfo?.type1  as keyof typeof backgroundImage]}`}>
                <div className="h-full bg-zinc-500/30 w-full flex flex-col justify-center text-white items-center">
                  <p>1순위</p>
                  <p>{dateCate[userInfo?.type1  as keyof typeof dateCate][1]}</p>
                  <p><span>{dateCate[userInfo?.type1  as keyof typeof dateCate][0]}</span>형 코스모스</p>
                </div>
              </div>
              <div className={`h-28 flex justify-center items-center`+ isDark?'bg-darkMain h-20':'bg-lightMain2'}>
                <div className="h-full w-full flex flex-col justify-center text-white items-center">
                2순위 
                <p><span>{dateCate[userInfo?.type2  as keyof typeof dateCate][0]}</span>형 코스모스</p>
                </div>
              </div>
              <div className={isDark?'text-white h-20 w-full border-solid border-2 border-darkMain4  flex justify-center items-center hover:bg-darkMain3 hover:text-lg':
               "h-20 w-full border-solid border-2 border-lightMain4  flex justify-center items-center hover:bg-lightMain3 hover:text-lg"}>
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
          <div className="recent w-full m-4 mb-8 min-h-36">
            <div className={isDark?'text-darkMain2':'text-lightMain2' + `ml-4 mr-4 font-bold`}>
              최근 찜 내역
            </div>
            <div className="recentItem flex justify-around h-36">

              {wishPlaces?.map((item) => {
                return (
                  <div onClick={()=>navigate('/wish')} className="w-1/3 cursor-pointer h-full">
                    <div className="m-2 hover:h-[12.5vh] bg-lightMain4 h-[12vh] rounded-md md:h-10rem overflow-hidden">
                      <img className="h-full w-full" src={item.thumbNailUrl} alt="" />
                    </div>
                    <div className={isDark?"text-white m-2 text-sm font-bold" : "m-2 text-sm font-bold"}>{item.name}</div>
                  </div>
                );
              })}
            </div>

          </div>            
          <div
            onClick={logout} 
            className={isDark?"hover:bg-darkMain3 mt-2 w-full cursor-pointer text-xl text-calendarDark bg-calendarGray px-8 py-4 h-16 rounded-lg flex justify-center hover:bg-lightMain3"
                      :"mt-2 w-full cursor-pointer text-xl text-calendarDark bg-calendarGray px-8 py-4 h-16 rounded-lg flex justify-center hover:bg-lightMain3"
          }
            >로그아웃
            </div>
          <div
            onClick={deleteCouple} 
            className="mt-2 w-full cursor-pointer text-xl text-calendarDark bg-calendarGray px-8 py-4 h-16 rounded-lg flex justify-center hover:bg-darkMain2"
            > 커플 끊기
          </div>
          </div>
        </div>
        </div>
    )
  }
