import { useState } from "react";
import { NavLink } from "react-router-dom";
import { PlaceItem } from "../../components/common/PlaceItem";
import { ScheduleMonth } from "../../components/common/ScheduleMonth";
import { WeekCalendar } from "../../components/common/WeekCalendar";

interface Place{
    idx : number,
    name : string,
    imgUrl : string,
    category : string,
    location : string,
    date: string,
}

const testPlace:Place[] = [{idx:0,name:'파주 출판단지', imgUrl:'', category:'관광', location:"경기도", date:'2023년 2월 28일'},
                        {idx:1,name:'베지앙', imgUrl:'', category:'카페', location:"경기도", date:'2023년 2월 28일'},
                        {idx:2,name:'녹두', imgUrl:'', category:'음식', location:"경기도", date:'2023년 2월 28일'}
]
export function DaySchedulePage(){
    const [scheduleTitle, setScheduleTitle] = useState('파릇파릇파주여행')
    return (
        <div className="bg-lightMain2 h-screen">
            <ScheduleMonth />
            <div className="bg-white rounded-lg w-full h-full">
                <div className="ml-2 mr-2 flex flex-col items-center content-center">
                    <div className="flex flex-col w-full justify-center md:w-5/6 lg:w-4/6">
                        <WeekCalendar />
                        <div className="">
                            <ScheduleTitle scheduleTitle={scheduleTitle}/>
                            {
                                testPlace.map((place, key)=>{
                                    return(
                                        <NavLink to="/schedule/detail" state={{placeId: key, place:place, scheduleTitle:scheduleTitle}}>
                                            <PlaceItem place={place} key={key}/>
                                        </NavLink>
                                    ) 
                                })
                            }
                        </div>
                    </div>
                </div>
            </div>
        </div>
    )
}

function ScheduleTitle(props:{scheduleTitle:string}) {
    return(
        <div className="bg-lightMain2 h-10 flex justify-center items-center font-bold text-white rounded-lg">{props.scheduleTitle}</div>
    )
}