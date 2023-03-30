import { useRecoilState, useRecoilValue } from 'recoil';
import { useContext, useEffect, useState, } from "react";
import { useParams } from "react-router";

import "../../css/serveyResult.css"
import axios from 'axios';
import { userState } from '../../recoil/states/UserState';
import { useQuery } from 'react-query';
import { UserDispatch } from '../../layouts/MainLayout';
import { getCoupleId, makeCouple, postUserType } from '../../apis/api/user';
import { invitedUserId, invitedCoupleId, bgPngUrl, backgroundImageGif, dateCateNames } from '../../recoil/states/ServeyPageState';
import { NavLink } from 'react-router-dom';
// import { coupleIdState } from '../../recoil/states/UserState';

const codeName = {
    'E' : '차분', 
    'J' : '활발', 
    'A' : '가성비',
    'O' : '플렉스',
    'Y': '실내형',
    'T' :'실외형'
}
const resultImg = {
    'purple4' : 'https://user-images.githubusercontent.com/87971876/224265410-0155ed72-db1d-4ca4-88c0-9a1b81c7e29d.png',
    'purple6' : 'https://user-images.githubusercontent.com/87971876/224265498-0924bd2a-17a4-448d-9dc8-a16a9fb70b3b.png',
    'gray4' : 'https://user-images.githubusercontent.com/87971876/224265103-18e0962e-72a7-48b7-b83b-741d9fc64b0e.png',
    'gray6' : 'https://user-images.githubusercontent.com/87971876/224265309-dc05453f-7f92-420e-b7f2-765ea5071367.png'
}
// 기존 윈도우에 없는 객체에 접근할 때 에러 발생
// 임의로 값이 있다고 정의해주는 부분
// const Kakao = (window as any).Kakao;
declare const window: typeof globalThis & {
    Kakao: any;
  };
  

export default function ServeyPage(){
    const [bgPng, setBgPng] = useRecoilState(bgPngUrl)
    const [backgroundImage, setBackgroundImage] = useRecoilState(backgroundImageGif)
    const [dateCate, setDateCate] = useRecoilState(dateCateNames)
    const param = useParams()
    const cateNum = param.cateNum
    const cate = param.cate ? param.cate : 'EAT'
    const firstKeyword = cate != null ? codeName[cate.slice(0,1) as keyof typeof codeName] : ''
    const secondKeyword = cate != null ? codeName[cate.slice(1,2) as keyof typeof codeName] : ''
    const thirdKeyword = cate != null ? codeName[cate.slice(2,3) as keyof typeof codeName] : ''

    // 생성된 유저 coupleId recoil에 저장
    const [user, setUser] = useRecoilState(userState)
    const [coupleId, setCoupleId] = useState<any>()
    // 커플초대로 온 경우 recoil에 담아둔 커플Id 유저정보에 저장
    const [ivtCoupleId, setIvtCoupleId] = useRecoilState(invitedCoupleId)
    const [CoupleUserId, setCoupleUserId] = useRecoilState(invitedUserId)
    
    // 커플 요청을 받아서 온 유저인지에 따라서 다른 api 요청
    const [isInvited, setIsInvited] = useState<boolean>(ivtCoupleId?true:false) // 커플초대로 온 경우 true: false

     // 커플Id 생성 요청
    const {data} = useQuery(
        ["getCoupleId"],
        getCoupleId,
        {
            enabled: !coupleId,
        }
    )

    const type2 = cate?.slice(0,2) + (cate?.slice(-1) === 'Y'? 'T' :'Y')
    // 유저 설문 유형 전송
    const types = useQuery({
        queryKey: ["postUserType"],
        queryFn: () => postUserType(user.acToken, Number(user.seq), cate, type2)
    })

    useEffect (() =>{
        // 커플매칭 요청
        if (ivtCoupleId){
            console.log('커플 매칭 요청을 받아 설문을 완료한 사람')
            setCoupleId(ivtCoupleId)
            setIsInvited(true)
        } 

        // A. 커플Id를 받은 coupleId초기화s
        if (data){
            setCoupleId(data)
        }
        if(coupleId){
            setUser({...user, coupleId : coupleId})
        }
        // 카카오 sdk 찾도록 초기화
        if (!window.Kakao.isInitialized()){
            window.Kakao.init(process.env.REACT_APP_KAKAO_SHARE_JS_MYE)
        }

    },[data])
    // const coupleMatched = useQuery(
    //     ["makeCouple",],
    //     makeCouple(coupleId, user.seq, Number(invite)),
    //     {
    //         enabled: !!isInvited,
    //     }
    // )
    const coupleMatched = useQuery({
        queryKey: ["makeCouple"],
        queryFn: () => {
            isInvited && Number(ivtCoupleId) && makeCouple( Number(ivtCoupleId), user.seq,Number(CoupleUserId),)
        }
    })
    useEffect(()=>{
        // if (isInvited){
        //     axios.post(`https://j8e104.p.ssafy.io/api/couples/accept/${coupleId}`,{
        //         headers:{
        //             Authorization: 'Baerer '+ user.acToken,
        //             ContentType : 'application/json'
        //         },
        //         data: {
        //             userSeq : user.seq,
        //             coupleUserSeq : Number(invite)
        //         }
        //     }).then((res)=>{
        //         console.log('커플 매칭 성공', res.data)
        //     }).catch((err)=>{
        //         console.log('커플 매칭 실패', err)
        //     })
        // }

    },[])



    const shareKakao = () => {
        window.Kakao.Link.sendDefault({
            objectType : 'feed',
            content:{
                title:"나의 데이트 유형은 '" + dateCate[cate as keyof typeof dateCate][0] +"'",
                description:'너도 데이트 유형 테스트하고 나랑 데이트가자!',
                imageUrl: bgPng[cate as keyof typeof backgroundImage],
                link:{
                    webUrl:`http://localhost:3000/`,
                    mobileWebUrl:'http://localhost:3000/',
                },
            },
            buttons:[
                {
                    title:'데이트 취향설문하기',
                    link:{
                        webUrl:`http://localhost:3000/servey/${coupleId}/${user.seq}`,
                        mobileWebUrl : `http://localhost:3000/servey/${coupleId}/${user.seq}`,
                    }
                }
            ]
        })
    }


    return(
        <>
            <div className="h-screen bg-darkBackground place-content-center text-darkMain w-full">
                <div className="flex content-center w-full items-start h-full">
                    <div className="flex w-full h-full justify-start items-center flex-col">
                        <h1 className="font-bold font-baloo text-2xl mb-3 p-5">데이트 취향설문 결과 </h1>
                        <div className={`w-full h-4/6 + ${backgroundImage[cate as keyof typeof backgroundImage]} bg-cover bg-center bg-no-repeat
                                        flex justify-end items-center flex-col
                        `}>
                            <div className='p-6 pt-2 bg-black bg-opacity-20 w-full hover:bg-opacity-0'>
                                <div className="flex justify-end items-center flex-col text-white font-semibold">
                                    <p className='text-darkMain6'>{dateCate[cate as keyof typeof dateCate][1]}</p>
                                    <h1 className="text-xl">나에게 데이트는</h1>
                                    <h1  className="text-2xl"><span className="font-bold text-darkMain6">'{dateCate[cate as keyof typeof dateCate][0]}'</span>이다</h1>
                                </div>
                                <div className="mt-2 w-full bg-white h-0.5"></div>
                                <div className='graph-circle'>
                                    {/* 코드 모듈화 완료 */}
                                    {
                                        [{0:firstKeyword,1:'활발',2:'차분'},{0:secondKeyword,1:'플렉스',2:'가성비'},{0:thirdKeyword, 1:'실외형',2:'실내형'}].map((items)=>{
                                            return(
                                                <div className="text-white flex items-center">
                                                    <span className={(items[0] != items[1]?' text-opacity-0 text-darkMain':'font-bold')}>{items[1]}</span>
                                                        { items[0] === items[1]?
                                                            cateNum?.slice(0,1) === '2'?
                                                                <img className="p-2" src={resultImg['purple4']} alt="" />:
                                                                <img className="p-2" src={resultImg['purple6']} alt="" />
                                                            :
                                                            cateNum?.slice(0,1) === '2'?
                                                                <img className="p-2" src={resultImg['gray4']} alt="" />:
                                                                <img className="p-2" src={resultImg['gray6']} alt="" />
                                                        }
                                                        
                                                    <span className={(items[0] != items[2]?' text-opacity-0 text-darkMain':'font-bold')}>{items[2]}</span>
                                                </div>
                                            )
                                        })
                                    }
                                
                                </div>
                            </div>
                        </div>
                        {
                            isInvited?
                            // 커플 초대로 설문을 마친사람
                                <div className="w-full p-2 flex flex-col items-center ">
                                        <p className="mt-4 mt-4w-full h-10 flex h-12 justify-center p-3 text-center rounded-lg w-full bg-darkMain6 text-darkBackground2 ">
                                            커플매칭에 성공했습니다!
                                        </p>
                                        <p className="mt-4 cursor-pointer w-full h-10 flex h-12 justify-center p-3 text-center rounded-lg w-full bg-darkMain5 text-darkBackground2 ">
                                            <NavLink to="/mypage" >마이페이지로</NavLink>
                                        </p>
                                </div>
                                
                            // 커플Id가 없는사람
                            :
                            <div className="w-full p-2 flex flex-col items-center text-sm ">
                                <button
                                    className="w-full h-10 flex h-12 justify-center p-3 text-center rounded-lg w-full bg-darkMain5 text-darkBackground2"
                                >
                                    {coupleId ? coupleId : data}
                                </button>
                                <p className="mt-5 mb-2 text-xs">애인에게 코드를 공유하고 코스모스의 커플 서비스를 사용하세요</p>
                                <button
                                    onClick={shareKakao}
                                    className="cursor-pointer w-full flex h-12 justify-center p-3 text-center rounded-lg w-full bg-darkMain6 text-darkBackground2"
                                >
                                    카카오톡 공유하기
                                </button>
                            </div>
                        }
                        
                    </div>
                </div>
            </div>
        </>
    )
}

