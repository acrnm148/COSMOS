import React, { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useRecoilState } from "recoil";
import { getPhotos } from "../../apis/api/schedule";
import { ScheduleMonth } from "../../components/common/ScheduleMonth";
import { darkMode, userState } from "../../recoil/states/UserState";
import { setDate } from "date-fns";
import { Icon } from '@iconify/react';
import { useNavigate } from "react-router-dom";
type REVIEWIMG = {
    imageId : number
    imageUrl : string
    reviewId : number
    createdTime : string
    createdMonth : string
}
type IMAGE = {
    imageUrl : string,
    createdTime : string,
    imageId : number,
    reviewId : number
}
export function GalleryPage(){
    // 다크모드
    const [isDark, x] = useRecoilState(darkMode)
    // 로그인 유저
    const [loginUser, setLoginUser] = useRecoilState(userState)
    // 갤러리 사진 저장
    const [photos, setPhotos] = useState<REVIEWIMG[]>()
    // 무한스크롤
    const [offset, setOffset] = useState(0)
    const limit = 30
    // 갤러리 사진 요청
    const {data} = useQuery({
        queryKey : ["getPhotos"],
        queryFn: () => getPhotos(loginUser.coupleId, limit, offset)
    })
    // 표출 년월 저장하는 배열
    const [setDateTags, setSetDateTags] = useState<string[]>([]) //202303
    

    const navigate = useNavigate()
    useEffect(()=>{
        if(data){
            // console.log('사진있삼~', data)
            setPhotos(data.map((d:IMAGE) => ({
                imageUrl : d.imageUrl,
                createdTime : d.createdTime,
                imageId : d.imageId, 
                reviewId : d.reviewId,
                createdMonth : d.createdTime.slice(0,6)
            })))
        } else{
            console.log('사진 없삼')
        }
    },[data])
    function goToBack(){
        navigate(-1)
    }

    return (
        <div className={(isDark? "bg-darkMain2" :"bg-lightMain2")+" min-h-screen "}>
            <div className="flex justify-around p-4 pt-10 text-white text-xl h-[10vh]">
                <p onClick={() => goToBack()} className="cursor-pointer"><Icon icon="material-symbols:arrow-back-ios-new-rounded" />  </p>
            <p>사진 모아보기</p>
                <p className="opacity-0"><Icon icon="material-symbols:arrow-back-ios-new-rounded" /></p>
            </div>
                {
                    
                    photos?.length&&
                    photos.map((p:REVIEWIMG)=>{
                        // 표출 년월이 존재하지 않으면
                        if ( -1 == setDateTags.indexOf(p.createdMonth)){
                            setSetDateTags([...setDateTags, p.createdMonth])
                            return(
                                <div>
                                </div>
                            )
                        }
                    })
                }
            <div className={(isDark? "bg-darkBackground text-white ":"bg-white") + " rounded-lg w-full flex min-h-screen overflow-y-scroll flex-wrap px-2" }>
                {photos?.length&&
                setDateTags?
                    setDateTags.map((tgs)=>{
                        return(<div className="">
                            <div className="w-screen h-10 p-2 font-bold font-lg">{tgs.slice(0,4)+'년 '+tgs.slice(4,6)+'월'}</div>
                            <div className="flex">
                            {
                                photos.map((p:REVIEWIMG)=>{
                                    if(tgs === p.createdMonth)
                                    return( <div className={`max-w-[43%] rounded-md h-full overflow-hidden`}>
                                        <img src={p.imageUrl}></img>
                                    </div>)
                                })
                            }
                            </div>
                            
                        </div>)
                    })
                    :
                    <div className="text-white w-full h-[100%] flex justify-center items-center flex-col">
                        <div className="text-xl font-bold">사진이 없습니다!</div>
                        작성한 리뷰의 사진을 모아볼 수 있습니다.
                    </div>
                }
           </div>
        </div>
    )
}

