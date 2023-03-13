import { serveyChoice, serveyPage } from "../../states/servey/ServeyPageState"
import { useRecoilState } from 'recoil';
import { useState } from "react";

export default function Servey2(){
    const [serveyCho, setServeyCho] = useRecoilState(serveyChoice)
    const [serveyPg, setServeyPage] = useRecoilState(serveyPage)

    function submit2(key:any){
        // selected가 -1이면 선택해달라고 모달
        let update2 = {}
        if (key == 0){
            // A형
            update2 = {2:'A'}
        } else{
            // O형
            update2 = {2:'O'}
        }
        setServeyCho(serveyCho => ({
            ...serveyCho, ...update2
        }))
        setServeyPage(serveyPg+1)
        
    }

    return(
        <div className="w-full">
            <h1 className="font-bold font-baloo text-2xl">데이트 취향설문</h1>
            <div className="mt-7 mb-7 w-full text-center">
                <span className="font-baloo text-base text-darkMain">2 / 9</span>
                <div className="border-solid border-2 h-5 border-darkMain w-full"><div className="h-full bg-darkMain w-20"></div></div>
            </div>
            <h1 className="font-baloo text-base text-white mb-2">2. 데이트 시 주로 사용하는 이동수단은?</h1>
            <ul className="flex flex-wrap w-full grid-rows-2 gap-2 justify-center">
            {['뚜벅이', '부릉이'].map((item, key) => {
                return (
                <div
                    key={key}
                    onClick={()=>submit2(key)}
                    className={'flex justify-center w-40 h-72 p-5 content-center text-center bg-darkMain4 pt-10 pb-10 rounded-lg break-normal hover:bg-opacity-75  border-solid border-2 border-darkMain4'}
                    >
                    <div className="text-white">{item}</div>
                </div>
                );
            })}
            </ul>
            <div className="flex grid-rows-2 gap-2">
                <button
                    onClick={()=>setServeyPage(serveyPg-1)}
                    className="flex border-solid border-2 border-darkMain justify-center mt-5 text-darkMain p-3 text-center rounded-lg w-full mx-auto 
                    hover:bg-darkMain hover:text-white"
                >
                    이전
                </button>
            </div>
        </div>
    )
}