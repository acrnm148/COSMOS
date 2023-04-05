import React, { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useRecoilState } from "recoil";
import { getPhotos } from "../../apis/api/schedule";
import { ScheduleMonth } from "../../components/common/ScheduleMonth";
import { userState } from "../../recoil/states/UserState";
import { setDate } from "date-fns";
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
    const [setDateTags, setSetDateTags] = useState<string[]>([])
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

    return (
        <div className="bg-lightMain2">
            <div className="p-2 pt-10 text-white text-xl">사진 모아보기</div>
            <div className="bg-white rounded-lg w-full flex flex-wrap" >
                {
                    photos&&
                    photos.map((p:REVIEWIMG)=>{
                        // 표출 년월이 존재하지 않으면
                        if (-1 == setDateTags.indexOf(p.createdMonth)){
                            setSetDateTags([...setDateTags, p.createdMonth])
                            return(
                                <div>
                                    <div className="사진월 w-screen">{p.createdMonth.slice(0,4)+'년 '+ p.createdMonth.slice(4,6) + '월'}</div>
                                    <div className="w-[46%] overflow-hidden m-2 rounded-md">
                                        <img src={p.imageUrl} alt='' />
                                    </div>
                                </div>
                            )
                        }
                        else{
                            return(
                                <div className="w-[46%] overflow-hidden m-2 rounded-md">
                                    <img src={p.imageUrl} alt='' />
                                </div>
                            )
                        }
                        
                    })
                }
           </div>
        </div>
    )
}

