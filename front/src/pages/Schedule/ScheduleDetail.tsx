import { useEffect, useState } from "react"
import { NavLink, useLocation } from "react-router-dom"
import  "../../css/detailSchedule.css"

interface CATE_QA {
    [key : string] : string[], '음식' : string[], '카페' : string[], '문화' : string[],'쇼핑' : string[], '관광' : string[], '운동' : string[],'축제' : string[]
}
{/* 카테고리별 선택지 */}
const CATEGORY_QA:CATE_QA = {
    '음식' : ['음식이 맛있어요', '서비스가 좋아요', '사람이 많고 웨이팅이 있어요', '가성비가 좋아요', '가게가 깨끗해요'],
    '카페' : [],
    '문화' : [],
    '쇼핑' : [],
    '관광' : [],
    '운동' : [],
    '축제' : []
}
{/* 공통 선택지 */}
const COMMON_QA = ['접근성이 좋아요', '분위기가 좋아요', '반려동물 동반이 가능해요', '주차 지원이 가능해요', '사진찍기 좋아요']
export function ScheduleDetail(){
    const location = useLocation()
    const { state } = location
    const placeId = state.placeId
    const place = state.place

    const [isReview, setIsReview] = useState<boolean>(false)
    const [showReview, setShowReview] = useState<boolean>(false)

    useEffect(()=>{
    })

    return (
        <div className='bg-lightMain2 h-full mb-5'>
            <div className="h-20 flex justify-center items-center">{state.scheduleTitle}</div>
            <div className={"bg-white rounded-t-lg w-full h-full flex flex-col items-between" + (!showReview ? 'p-2':'w-full')}>
                <div>
                    <div className="flex flex-col justify-center items-center">
                        <p className="text-md text-calendarDark">{place.date}</p>
                        <p className="m-1 text-xl">{place.name}</p>
                    </div>
                    {
                        !showReview&&
                        <div>
                            <div className="detailImg h-full w-full bg-calendarGray rounded-lg"></div>
                            <div className="flex justify-end text-sm mb-5">
                                <div>{place.location}</div>
                                <div className="ml-1 mr-1 text-calendarDark">|</div>
                                <div>{place.category}</div>
                            </div>
                        </div>
                    }
                </div>
                <div className="bg-lightMain4 w-full h-full rounded-lg">
                    {showReview?
                        <div className=" w-full h-full flex flex-col justify-end items-center p-2">
                            <ReviewForm isReview={isReview} category={place.category}/>
                            <div onClick={()=>setShowReview(false)} className="w-full"><ReviewSet /></div>
                        </div>
                        
                    :isReview?
                            <div>리뷰가 있습니다</div>
                        :
                            <div className="h-40 w-full  w-full flex flex-col justify-end items-center p-4">
                                <p className="text-sm mb-2">일정을 마치셨나요?</p>
                                <div onClick={()=>setShowReview(true)} className="w-full"><ReviewSet /></div>
                            </div>
                        }
                </div>
            </div>
        </div>
    )
}
function ReviewForm(props:{isReview:boolean, category:string}){
    return(
        <div className="w-full">
            {/* 카테고리별 선택지 */}
            <div className="mb-5 w-full grid grid-col-2">
                {
                    CATEGORY_QA[props.category as keyof CATE_QA].map((ask, i)=>{
                        if (i === 2){
                            return(<div className="col-span-2 bg-white rounded-lg flex justify-center items-center text-xs h-8 mx-1 my-0.5 mw-s">{ask}</div>)
                            
                        } else{
                            return(
                                <div className="bg-white rounded-lg flex justify-center items-center text-xs h-8 mx-1 my-0.5 mw-s">{ask}</div>
                            )
                        }

                    })
                }
            </div>
            {/* 공통 선택지 */}
            <div className="mb-5 w-full grid grid-col-2">
                {
                    COMMON_QA.map((ask, i)=>{
                        if (i === 2){
                            return(<div className="col-span-2 bg-white rounded-lg flex justify-center items-center text-xs h-8 mx-1 my-0.5 mw-s">{ask}</div>)
                            
                        } else{
                            return(
                                <div className="bg-white rounded-lg flex justify-center items-center text-xs h-8 mx-1 my-0.5 mw-s">{ask}</div>
                            )
                        }

                    })
                }
            </div>
            
            {/* 리뷰 */}
            <div className="w-full h-60 bg-white rounded-lg mb-5 text-xs p-2 flex flex-col items-center">
                <div className="">
                    {/* 텍스트 */}
                    <p>리뷰</p>
                    <div>
                        <p>공개여부</p>
                        <div>아이콘</div>
                    </div>
                </div>
                <div>
                    리뷰내용
                </div>
                <div>
                    {/* 사진 */}
                    <p>사진</p>
                    <div>
                        <p>공개여부</p>
                        <div>아이콘</div>
                    </div>
                </div>
                <div>
                    <img src="" alt="이미지" />
                </div>
            </div>
            {/* 별점 */}
                <div>별점</div>           
        </div>
    )
}
function ReviewSet(){
    return(
        <div className="bg-white w-full h-10 text-sm rounded-lg text-lightMain flex justify-center items-center">
            리뷰남기기
        </div>
    )
}