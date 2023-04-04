import React, { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { useRecoilState } from "recoil";
import { getPhotos } from "../../apis/api/schedule";
import { ScheduleMonth } from "../../components/common/ScheduleMonth";
import { userState } from "../../recoil/states/UserState";
type REVIEWIMG = {
    imageId : number
    imageUrl : string
    reviewId : number
    createdTime : string
    createdMonth : string
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
    useEffect(()=>{
        if(data){
            console.log('사진있삼~', data)
            setPhotos(data.map((d: { imageUrl: any; }) => ({
                imageUrl : d.imageUrl
            })))
        }
    },[data])

    return (
        <div className="bg-lightMain2">
            <div className="p-2 pt-10 text-white text-xl">사진 모아보기</div>
            <div className="bg-white rounded-lg w-full flex flex-wrap" >
                {
                    photos&&
                    photos.map((p)=>{
                        return(
                            <div className="w-[46%] overflow-hidden m-2 rounded-md">
                                <img src={p.imageUrl} alt='' />

                            </div>
                        )
                    })
                }
           </div>
        </div>
    )
}

