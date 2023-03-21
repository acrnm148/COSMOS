import { useEffect, useRef, useState } from "react";
import { NavLink } from "react-router-dom";
import { PlaceItem } from "../../components/common/PlaceItem";
import { ScheduleMonth } from "../../components/common/ScheduleMonth";
import { WeekCalendar } from "../../components/common/WeekCalendar";

// framer-motion
import { motion } from "framer-motion";

// 지울예정
import 파주출판단지 from "../../assets/schedule/파주출판단지.png"
import 녹두 from "../../assets/schedule/녹두.png"
import 베지앙 from "../../assets/schedule/베지앙.png"

interface Place{
    idx : number,
    name : string,
    imgUrl : string,
    category : string,
    location : string,
    date: string,
}

const testPlace:Place[] = [{idx:0,name:'파주 출판단지', imgUrl:파주출판단지, category:'관광', location:"경기도", date:'2023년 2월 28일'},
                        {idx:1,name:'베지앙', imgUrl:베지앙, category:'카페', location:"경기도", date:'2023년 2월 28일'},
                        {idx:2,name:'녹두', imgUrl:녹두, category:'음식', location:"경기도", date:'2023년 2월 28일'}
]
interface Props {
    week? : Date[] | string,
    day? : Date | string
}
export function DaySchedulePage({week ='', day = ''}:Props){

    const [scheduleTitle, setScheduleTitle] = useState('파릇파릇파주여행')
    const [places, setPlaces] = useState<Place[]>([])
    // 일정 정보 초기화
    useEffect(()=>{
        setPlaces([...testPlace])
    },[])

    /////////////// 드래그앤드랍 ///////////////
    const draggingIdx = useRef<null | number>(null)
    const draggingOverIdx = useRef<null | number>(null)
    // 드래그 시작 (아이템 확인)
    const onDragStart = (e: any, idx: number) =>{
        draggingIdx.current = idx
        e.target.classList.add('grabbing')
    }
    // 드래그 놓았을 때 변화한 리스트 적용
    const onDragEnter = (e: any, idx: number) => {
        draggingOverIdx.current = idx
        const copyList = [...places]
        const dragItemContent = copyList[draggingIdx.current!]
        copyList.splice(draggingIdx.current!, 1)
        copyList.splice(draggingOverIdx.current, 0, dragItemContent)
        draggingIdx.current = draggingOverIdx.current
        draggingOverIdx.current = null
        setPlaces(copyList)
        e.target.classList.remove('grabbing')
    }
    // 드래그 이동 종료
    const onDragEnd = (e: any) =>{
        e.target.classList.remove('grabbing')
    }
    // 드래그 하는 중 아이템이 오버랩 된 상태
    const onDragOver = (e: { preventDefault: () => void; }) =>{
        e.preventDefault()
    }

    /////////////// motion framer ///////////////
    const [isVisible, SetIsVisible] = useState<boolean>(true)
    const hide = {
        opacity : 0,
        transitionEnd:{
            display : "none"
        }
    }
    const show = {
        opacity: 1,
        display: "block"
      };
    

    return (
        <div className="bg-lightMain2 h-screen">
            <motion.button onClick={()=>SetIsVisible(!isVisible)}>
                <ScheduleMonth />
            </motion.button>
            <motion.div className="bg-white rounded-lg w-full h-full" animate={isVisible ? show : hide}>
                <div className="ml-2 mr-2 flex flex-col items-center content-center">
                    <div className="flex flex-col w-full justify-center md:w-5/6 lg:w-4/6">
                        <WeekCalendar />
                        <div className="">
                            <ScheduleTitle scheduleTitle={scheduleTitle}/>
                            {
                                places?.map((place, key)=>{
                                    return(
                                        <div
                                            // onClick={} 페이지이동
                                            onDragStart={(e) => onDragStart(e, key)}
                                            onDragEnter={(e) => onDragEnter(e, key)}
                                            onDragOver={onDragOver}
                                            onDragEnd={onDragEnd}
                                            draggable
                                            key={key}
                                        >
                                            {/* <NavLink to="/schedule/detail" state={{placeId: key, place:place, scheduleTitle:scheduleTitle}}> */}
                                                <PlaceItem place={place} key={key}/>
                                            {/* </NavLink> */}
                                        </div>
                                    ) 
                                })
                            }
                        </div>
                    </div>
                </div>
            </motion.div>
        </div>
    )
}

function ScheduleTitle(props:{scheduleTitle:string}) {
    return(
        <div className="bg-lightMain2 h-10 flex justify-center items-center font-bold text-white rounded-lg">{props.scheduleTitle}</div>
    )
}