import axios from "axios"
import { useEffect, useState } from "react"
import { useRecoilState } from "recoil"
import { userSeqState } from "../../recoil/states/UserState"

interface userInfo {
    'userId' : number,
    'userName' : string,
    'phoneNumber' : string,
    'profileImageUrl' : string,
    'coupleYn' : string,
    'age' : number,
    'type1' : string,
    'type2' : string,
    'birthYear' : number,
    'coupleUserId' : number
}
export default function MyPage(){
    const userSeq = useRecoilState(userSeqState)
    const [userInfo, setUserInfo] = useState<userInfo>()
    const [coupleInfo, setCoupleInfo] = useState<userInfo>()
    useEffect(()=>{
        if (userSeq){
            console.log('로그인된유저', userSeq)
            const bringUser = (seq:number, me:string) =>{
                axios.get((`/api/accounts/userInfo/${seq}`))
                    .then((res) =>{
                        if(me==='me'){setUserInfo( res.data )}
                        else {setCoupleInfo(res.data)}

                        if((!coupleInfo) && (userInfo?.coupleUserId)){
                            bringUser(userInfo.coupleUserId, 'you')
                        }
                    })
                    .catch((err)=>{
                        console.log('err', err)
                    })
            }
            bringUser(Number(userSeq), 'me')
        } 
    })
    return(
        <div className="flex w-full items-center content-center ">
            <div className="w-full flex flex-col justify-center md:w-5/6 lg:w-3/6">
                <div className="profile justify-center items-end flex mt-10 mb-4">
                    <div className="rounded-full bg-lightMain w-16 h-16"></div>
                    <div className="flex text-lightMain items-end m-3">
                        <div className="w-10 h-px bg-lightMain"></div>
                            <p className="text-sm">n일째 코스모스중</p>
                        <div className="w-10 h-px bg-lightMain"></div>
                    </div>
                    <div className="rounded-full bg-lightMain w-12 h-12"></div>
                </div>
                <div className="dateCategory w-full flex flex-col">
                    <div className="dateCategory w-full h-60 bg-lightMain3"> 1순위 : {userInfo?.type1}</div>
                    <div className="h-20 bg-lightMain4 flex justify-center items-center">2순위 : {userInfo?.type2}형 코스모스</div>
                    <div className="h-20 w-full border-solid border-2 border-lightMain4  flex justify-center items-center">취향설문 다시하기</div>

                </div>

                <div className="recent w-full m-2">
                    <div className="ml-4 mr-4 text-lightMain2 font-bold">최근 본 내역</div>
                    <div className="recentItem flex justify-around">
                    {
                        ['일광당', '삼락생태공원', '부산현대미술관'].map((item)=>{
                            return(
                                <div className="w-1/3">
                                    <div className="m-2 bg-lightMain4 h-28 rounded-md">이미지</div>
                                    <div className="m-2 text-sm font-bold">{item}</div>
                                </div>
                                )
                            })
                        }
                    </div>
                </div>
            </div>
        </div>
    )
}