import axios from "axios"
import React, { useEffect, useRef, useState, useCallback } from "react"
import { NavLink, useLocation, useNavigate } from "react-router-dom"
import  "../../css/detailSchedule.css"
import { ReviewForm, ReviewSet } from "./ScheduleReview"
import { useRecoilState } from "recoil"
import { darkMode, userState } from "../../recoil/states/UserState"
import { useQuery } from "react-query"
import { getPlaceCoupleReview } from "../../apis/api/schedule"
// 별점 이미지
import { FaStar } from 'react-icons/fa'
// styled component
import {Stars} from '../../components/review/StarStyledComponent'
import ReviewOurs from "../../components/common/ReviewOurs"
import { Icon } from "@iconify/react"

export interface REVIEW {
    reviewId : number|undefined
    cateQ : string | undefined
    commonQ : string | undefined
    contentOpen : boolean | undefined
    content : string | undefined
    photoOpen : boolean | undefined
    photos : string[] | undefined
    score : number,
    createdTime : string
    placeId : any
}
export function ScheduleDetail(){
    const location = useLocation()
    const { state } = location
    const placeId = state.placeId
    const place = state.place
    const [isReview, setIsReview] = useState<boolean>(false)
    const [showReview, setShowReview] = useState<boolean>(false)

    const [loginUser, setLoginUser] = useRecoilState(userState)
    // 장소에 대한 유저, 커플의 리뷰 불러오기
    const [offset, setOffset] = useState(0)
    const { data } = useQuery({
        queryKey: ["getPlaceCoupleReview", isReview, showReview], // authapi 요청시 acToken 변
        queryFn: () => getPlaceCoupleReview(loginUser.seq, Number(loginUser.coupleId), place.placeId, 10, offset),
      });
    
    const [showMine, setShowMine] = useState(true)
    const [userReview, setUserReview] = useState<REVIEW>()
    const [coupleReview, setCoupleReview] = useState<REVIEW>()

    const [isDark, x] = useRecoilState(darkMode)
    const [placeIdSwitch, setPlaceIdSwitch] = useState(place.placeId)
    useEffect(()=>{
        if(data){
            data.map((review: { reviewId:number,userId: number; score: any; images: any; contents: any; categories:{reviewCategoryCode:string}[], indiReviewCategories: { reviewCategory: any }[]; contentOpen: any; imageOpen: any; createdTime: any }) => {
                setIsReview(true)
                if(String(review.userId)===String(loginUser.seq)){
                    
                    setUserReview({
                        score : review.score, 
                        photos : review.images.map((img: { imageUrl: any }) => img.imageUrl), 
                        content:review.contents, 
                        cateQ :review.indiReviewCategories[0].reviewCategory,
                        commonQ: review.categories[0].reviewCategoryCode,
                        contentOpen : review.contentOpen,
                        photoOpen : review.imageOpen,
                        createdTime:review.createdTime,
                        reviewId : review.reviewId,
                        placeId : place.placeId
                    })
                } else {
                    setCoupleReview({
                        score : review.score, 
                        photos : review.images.map((img: { imageUrl: any }) => img.imageUrl), 
                        content:review.contents, 
                        cateQ :review.indiReviewCategories[0].reviewCategory,
                        commonQ: review.categories[0].reviewCategoryCode,
                        contentOpen : review.contentOpen,
                        photoOpen : review.imageOpen,
                        createdTime:review.createdTime,
                        reviewId : review.reviewId,
                        placeId : place.placeId
                    })
                }
                console.log('userReview, coupleReview',userReview, coupleReview)
            })
        }
        console.log(isReview)
    },[data])
    const navigate = useNavigate()
    function goToBack(){
        navigate(-1)
    }
    useEffect(()=>{
        const temp = placeIdSwitch
        setPlaceIdSwitch(0)
        setPlaceIdSwitch(temp)
    }, [showReview])
    return (
        <div className={(isDark?"bg-darkBackground2 ": "bg-white ")  +"zw-full "}>
            <div className={(isDark?"bg-darkBackground2 ": "bg-white ") + " pt- bg-white rounded-t-lg w-full flex flex-col items-between" + (!showReview ? 'p-2':'w-full')}>
                <div >
                    <div className={isDark&& "bg-darkMain p-2 rounded-t-lg"}>
                        <p onClick={() => goToBack()} className={(isDark?" text-white ":"") +"cursor-pointer"}><Icon icon="material-symbols:arrow-back-ios-new-rounded" />  </p>
                        <div className="flex flex-col justify-center items-center">
                            <p className="text-md text-calendarDark">{place.date}</p>
                            <p className={(isDark? "text-white ":"") + "m-1 text-xl"}>{place.name}</p>
                        </div>

                    </div>
                    {
                        !showReview&&
                        <div>
                            <div className={`detailImg h-full w-full rounded-lg max-h-[50vh] overflow-hidden flex items-center`}>
                                <img className="w-full h-full" src={place.imgUrl} alt="" />
                            </div>
                            <div className={(isDark? "text-white ":"")+ "flex justify-end text-sm mb-5 pr-2"}>
                                <div>{place.location}</div>
                                <div className="ml-1 mr-1 text-calendarDark">|</div>
                                <div>{place.category}</div>
                            </div>
                        </div>
                    }
                </div>
                <div className={(isDark?"bg-darkMain5 ":"bg-lightMain4 ") + "min-h-[screen] w-full h-full rounded-t-lg pb-[15vh]"}>
                    {showReview?
                        <div className=" w-full h-full flex flex-col justify-end items-center p-2">
                            <ReviewForm placeId={place.placeId} review={userReview} isReview={isReview} category={place.category} setShowReview={setShowReview} edit={isReview}/>
                        </div>
                        
                    :
                            <div className="">
                                <div className="누구리뷰 w-full flex h-10">
                                    <div onClick={()=>(setShowMine(true))}  className={showMine ? (isDark ? 'bg-darkMain ' : 'bg-lightMain ') + 'w-[50%] flex justify-center rounded-t-lg items-center' : 'w-[50%] flex justify-center items-center'}>나의리뷰</div>
                                    { !isDark &&
                                        <div onClick={()=>(setShowMine(false))} className={showMine ? ' w-[50%] flex justify-center rounded-t-lg items-center' : 'bg-lightMain w-[50%] flex justify-center items-center'}>너의리뷰</div>
                                    }
                                </div>

                                <div className="리뷰내용">
                                    {showMine ?
                                    <div className="">
                                        {
                                            isReview && userReview?
                                            <>
                                                <ReviewOurs placeId={placeIdSwitch} sol={true}/>
                                                <div className="h-20 w-full cursor-pointer w-full flex flex-col justify-end items-center p-4">
                                                    <div onClick={()=>setShowReview(true)} className="w-full"><ReviewSet edit={true}/></div>
                                                </div>
                                            </>
                                            :
                                            <div className="h-40 w-full  w-full flex flex-col justify-end items-center p-4">
                                                <p className="text-sm mb-2">일정을 마치셨나요?</p>
                                                <div onClick={()=>setShowReview(true)} className="w-full"><ReviewSet edit={false}/></div>
                                            </div>
                                        }
                                    </div>    
                                    :
                                    <div>
                                        {
                                            isReview?
                                            <>
                                                <ReviewOurs placeId={placeIdSwitch} sol={true}/>
                                                {/* <ReviewContents Review={coupleReview}  setShowReview={setShowReview} isMine={false}/> */}
                                            </>
                                            :
                                            <div className="h-40 w-full  w-full flex flex-col justify-end items-center p-4">
                                                <p className="text-sm mb-2">일정을 마치셨나요?</p>
                                                <div onClick={()=>setShowReview(true)} className="w-full"><ReviewSet edit={false} /></div>
                                            </div>
                                        }
                                    </div>    
                                }
                                </div>
                            </div>
                        
                
                        }
                </div>
            </div>
        </div>
    )
}
