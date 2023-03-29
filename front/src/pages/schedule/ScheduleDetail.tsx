import axios from "axios"
import { useEffect, useRef, useState, useCallback } from "react"
import { NavLink, useLocation } from "react-router-dom"
import  "../../css/detailSchedule.css"
import { ReviewForm, ReviewSet } from "./ScheduleReview"

interface REVIEW {
    cateQ : string | undefined
    commonQ : string | undefined
    contentOpen : boolean | undefined
    content : string | undefined
    photoOpen : boolean | undefined
    photos : string[] | undefined
}
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
        <div className='bg-lightMain2 mb-5'>
            {!showReview &&
            <div className="h-20 flex justify-center items-center">{state.scheduleTitle}</div>
            }
            <div className={"bg-white rounded-t-lg w-full flex flex-col items-between" + (!showReview ? 'p-2':'w-full')}>
                <div>
                    <div className="flex flex-col justify-center items-center">
                        <p className="text-md text-calendarDark">{place.date}</p>
                        <p className="m-1 text-xl">{place.name}</p>
                    </div>
                    {
                        !showReview&&
                        <div>
                            <div className={`detailImg h-full w-full rounded-lg max-h-[50vh] overflow-hidden flex items-center`}>
                                <img className="w-full" src={place.imgUrl} alt="" />
                            </div>
                            <div className="flex justify-end text-sm mb-5 pr-2">
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
                            <ReviewForm isReview={isReview} category={place.category} setShowReview={setShowReview}/>
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
