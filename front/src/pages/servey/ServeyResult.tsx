import { useRecoilValue } from 'recoil';
import { useEffect, useState, } from "react";
import { useParams } from "react-router";

import "../../css/serveyResult.css"

// 설문조사 결과 이미지
const backgroundImage = {
// - 휴식(EAY) - 소소한 힐링 추구
// - 먹방(EAT) - 내기중독 먹보
// - 체험(JAT) - 여기 어때 JAT
// - 추억(EOT)- 행복의 완성은 사진 EOT
// - 예술(JOY) - 호기심 많은 게으름뱅이
// - 가성비(JAY) - 임금 뒷편의 권력
// - Flex(EOY) - 오늘은 제가 쏠게요
// - 인플루언서(JOT) - 화려한 조명이 나를 감싸네
    'JOT' : "bg-[url('https://user-images.githubusercontent.com/87971876/223929252-df69cc40-1a58-4fc5-b07c-8503bd659e12.gif')]",
    'JAT' : "bg-[url('https://user-images.githubusercontent.com/87971876/223928638-bc47005c-e85e-4311-b349-c5ede8087bed.gif')]",
    'JOY' : "bg-[url('https://user-images.githubusercontent.com/87971876/223930566-fc5f372e-945f-40e5-ab9d-08a71af8f6d4.gif')]",
    // 'JAY' : "bg-[url('https://user-images.githubusercontent.com/87971876/223930937-d553869c-baa9-4aba-8d7e-995c43edeb89.gif')]",
    //원본JAY 
    'JAY' : "bg-[url('https://user-images.githubusercontent.com/87971876/223932284-fe395bcf-3eed-46a1-b34a-9628fa517892.gif')]",
    'EOT' : "bg-[url('https://user-images.githubusercontent.com/87971876/223929175-dd275fbc-a6bb-48b3-85eb-1a059a8580c7.gif')]",
    'EOY' : "bg-[url('https://user-images.githubusercontent.com/87971876/223929359-bc4ee9e0-f04e-4276-914d-4bc3cc9375eb.gif')]",
    'EAY' : "bg-[url('https://i.pinimg.com/736x/75/b3/ba/75b3ba306c7f60a6d242c8ccd959d81c.jpg')]",
    'EAT':"bg-[url('https://user-images.githubusercontent.com/87971876/223905743-c2736c6e-0a0a-44ea-9363-eb1e137f8265.gif')]"
  }
const dateCate = {
    'JOT' : ["인플루언서","핫플 도장깨기 전문가"],
    'JAT' : ["체험","올 겨울 여기 어때"],
    'JOY' : ["예술","호기심 많은 게으름벵이"],
    'JAY' : ["가성비","효율적인 데이트플래너"],
    'EOT' : ["추억","그때의 조명..온도..습도.."],
    'EOY' : ["Flex","아낌없이 쓰는 나무"],
    'EAY' : ["휴식","소소한 힐링 추구"],
    'EAT': ["먹방","맛있는걸 찾아 떠나는 먹보"]
}
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
export default function ServeyPage(){
    const param = useParams()
    const cateNum = param.cateNum
    const cate = param.cate
    const firstKeyword = cate != null ? codeName[cate.slice(0,1) as keyof typeof codeName] : ''
    const secondKeyword = cate != null ? codeName[cate.slice(1,2) as keyof typeof codeName] : ''
    const thirdKeyword = cate != null ? codeName[cate.slice(2,3) as keyof typeof codeName] : ''

    return(
        <>
            <div className="h-screen bg-darkBackground place-content-center text-darkMain">
                <div className="flex content-center w-full items-start h-full">
                    <div className="flex w-full h-full justify-start items-center flex-col">
                        <h1 className="font-bold font-baloo text-2xl mb-3 p-5">데이트 취향설문 결과 </h1>
                        <div className={`w-full h-4/6 + ${backgroundImage[cate as keyof typeof backgroundImage]} bg-cover bg-center bg-no-repeat
                                        flex justify-end items-center flex-col
                        `}>
                            <div className='p-6 pt-2 bg-black bg-opacity-20 w-full'>
                                <div className="flex justify-end items-center flex-col text-white font-semibold">
                                    <p className='text-darkMain6'>{dateCate[cate as keyof typeof dateCate][1]}</p>
                                    <h1 className="text-xl">나에게 데이트는</h1>
                                    <h1  className="text-2xl"><span className="font-bold text-darkMain6">'{dateCate[cate as keyof typeof dateCate][0]}'</span>이다</h1>
                                </div>
                                <div className="mt-2 w-full bg-white h-0.5"></div>
                                <div className='graph-circle'>
                                    {/* 코드 모듈화 필요!!!!!!!!! */}
                                    <div className="text-white flex">
                                        <span className={(firstKeyword != '활발'?' text-opacity-0 text-darkMain':'')}>활발</span>
                                            { firstKeyword === '활발'?
                                                cateNum?.slice(0,1) === '2'?
                                                    <img src={resultImg['purple4']} alt="" />:
                                                    <img src={resultImg['purple6']} alt="" />
                                                :
                                                cateNum?.slice(0,1) === '2'?
                                                    <img src={resultImg['gray4']} alt="" />:
                                                    <img src={resultImg['gray6']} alt="" />
                                            }
                                            
                                        <span className={(firstKeyword != '차분'?' text-opacity-0 text-darkMain':'')}>차분</span>
                                    </div>
                                    <div className="text-white flex">
                                        <span className={(secondKeyword != '플렉스'?' text-opacity-0 text-darkMain':'')}>플렉스</span>
                                        { secondKeyword === '플렉스'?
                                                cateNum?.slice(1,2) === '2'?
                                                    <img src={resultImg['purple4']} alt="" />:
                                                    <img src={resultImg['purple6']} alt="" />
                                                :
                                                cateNum?.slice(1,2) === '2'?
                                                    <img src={resultImg['gray4']} alt="" />:
                                                    <img src={resultImg['gray6']} alt="" />
                                            }
                                        <span className={(secondKeyword != '가성비'?' text-opacity-0 text-darkMain':'')}>가성비</span>
                                    </div>
                                    <div className="text-white flex">
                                        <span className={(thirdKeyword != '실외형'?' text-opacity-0 text-darkMain ':'')}>실외형</span>
                                        { thirdKeyword === '실외형'?
                                                cateNum?.slice(2,3) === '2'?
                                                    <img src={resultImg['purple4']} alt="" />:
                                                    <img src={resultImg['purple6']} alt="" />
                                                :
                                                cateNum?.slice(2,3) === '2'?
                                                    <img src={resultImg['gray4']} alt="" />:
                                                    <img src={resultImg['gray6']} alt="" />
                                            }
                                        <span className={(thirdKeyword != '실내형'?' text-opacity-0 text-darkMain':'')}>실내형</span>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div className="w-full p-2 flex flex-col items-center text-sm ">
                            <button
                                className="w-full h-10 flex h-12 justify-center p-3 text-center rounded-lg w-full bg-darkMain5 text-darkBackground2"
                            >
                                난수가 들어가는 자리입니다
                            </button>
                            <p className="mt-5 mb-2 text-xs">애인에게 코드를 공유하고 코스모스의 커플 서비스를 사용하세요</p>
                            <button
                                className="w-full flex h-12 justify-center p-3 text-center rounded-lg w-full bg-darkMain6 text-darkBackground2"
                            >
                                카카오톡 공유하기
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
}
