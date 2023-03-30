import { useEffect, useRef, useState } from "react";
import { NavLink, useLocation } from "react-router-dom";
import { PlaceItem } from "../../components/common/PlaceItem";
import { ScheduleMonth } from "../../components/common/ScheduleMonth";
import { WeekCalendar } from "../../components/common/WeekCalendar";
import { useMutation, useQuery } from "react-query";

// framer-motion
import { motion } from "framer-motion";

// 지울예정
import 파주출판단지 from "../../assets/schedule/파주출판단지.png"
import 녹두 from "../../assets/schedule/녹두.png"
import 베지앙 from "../../assets/schedule/베지앙.png"

import { getDayCourse } from "../../apis/api/course";
import { userState } from "../../recoil/states/UserState";
import { useRecoilState } from "recoil";
import { Iron } from "@mui/icons-material";
import axios from "axios";

import Modal from "../../components/common/Modal";
//mui datepicker
import { DatePicker, MobileDatePicker } from '@mui/x-date-pickers';
import { LocalizationProvider } from '@mui/x-date-pickers'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { DesktopDatePicker } from '@mui/x-date-pickers'

//alert
import Swal from "sweetalert2";

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
export function DaySchedulePage(){
    let location = useLocation();
    const [scheduleTitle, setScheduleTitle] = useState('파릇파릇파주여행')
    const [places, setPlaces] = useState<Place[]>([])
    const [dayClicked, setDayClicked] = useState(String(location.state.day.day.getDate()))
    const [loginUser, setLoginUser] = useRecoilState(userState)
    const [showModal, setShowModal] = useState(false)

    // 일정여부
    const [isPlan, setIsPlan] = useState(false)
    // 코스여부
    const [isCourse, setIsCourse] = useState(false)

    // 일정 시작.종료일
    const [startDate, setStartDate] = useState<any|undefined>()
    const [endDate, setEndDate] = useState<any|undefined>()
    // 일정명
    const [planName, setPlanName] = useState<string|undefined>()

    const [month, getMonth] = useState(location.state.month)
    const[year, setYear] = useState(location.state.year)
    let slicedMonth = month.month.slice(0,-1)

    
    useEffect(()=>{
        // setPlaces(testPlace)
        setShowModal(false)
        setStartDate(undefined)
        setEndDate(undefined)
        setPlanName(undefined)
        
        const yearStr =  year.year.slice(0,-1)
        const month = slicedMonth.length === 1?'0'+ slicedMonth:slicedMonth
        const day = String(Number(dayClicked)).length === 1? '0'+String(Number(dayClicked)) :dayClicked

        const coupleId = loginUser.coupleId
        const date = yearStr+month+day

        if(dayClicked){
            axios.get(`https://j8e104.p.ssafy.io/api/plans/${coupleId}/day/${date}`)
            .then((res)=>{
                if(res.data.planName){
                    setScheduleTitle(res.data.planName)
                    setIsPlan(true)
                } else{
                    setScheduleTitle('일정 생성하기')
                    setIsPlan(false)
                }
                if(res.data.courses){
                    setIsCourse(true)
                    // 코스 표출
                }
            }).catch((err)=>{
                console.log(err)
            })
        }
    }, [dayClicked])

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
    

    const Toast = Swal.mixin({
        toast: true,
        position: "bottom-end",
        showConfirmButton: false,
        timer: 2000,
        timerProgressBar: true,
      })

    function makeShedules(){
        if(!isPlan){
            // 찜 코스 불러오기
            // axios.get(`https://j8e104.p.ssafy.io/api/plans/${coupleId}/day/${date}`)
            // .then((res)=>{

            // }).catch((err)=>{

            // })
            setShowModal(true)
        }
    }
    function submitSchedules(){

    }
    
    const [scheduleDaysList, setScheduleDaysList] = useState<Object>()
    // const [allDays, setAllDays] = useState<string[]>([])
    let allDays: any[] = []
    useEffect(()=>{
        if(!startDate || !endDate){return}
        // startDate와 EndDate가 새로 설정될때마다 날짜 리스트를 새로 만들기
        const sd = startDate.$d
        const ed = endDate.$d
        while(sd.getTime() <= ed.getTime()){
            let mon = sd.getMonth() + 1
            mon = mon < 10 ? '0'+mon : mon
            let da = sd.getDate()
            da = da < 10 ? '0'+da : da
            allDays.push(sd.getFullYear() + mon + da)
            // setAllDays([...allDays,(sd.getFullYear() + mon + da)])
            sd.setDate(sd.getDate() + 1)
        }
        console.log('allDays', allDays)
        let temp:any = {}
        // allDays.map((day)=>{
        //     return temp[day]:[]
        // })
        setScheduleDaysList(allDays.map((day)=>{
            const temp = day
            return temp
        }))
        
    },[startDate, endDate])
    const checkStartDate = (newValue: any) =>{
        if (endDate && endDate < newValue){
            Toast.fire({
                title: '시작일이 종료일 이후입니다!'
            })
            setStartDate(undefined)
            return
        } else{
            setStartDate(newValue)
        }
    }

    function checkEndDate(newValue: any) {
        if (startDate && startDate > newValue){
            Toast.fire({
                title: '종료일이 시작일 이전입니다!'
            })
            setEndDate(undefined)
            return
        } else{
            setEndDate(newValue)
        }
    }

    return (
        <div className="bg-lightMain2 h-screen">
            <ScheduleMonth year={year.year} month={month.month}/>
            <div className="bg-white rounded-lg w-full h-full" >
                <div className="ml-2 mr-2 flex flex-col items-center content-center">
                    <div className="flex flex-col w-full justify-center md:w-5/6 lg:w-4/6">
                        <WeekCalendar setDayClicked={setDayClicked} day={location.state.day} week={location.state.week}/>
                        <div className="">
                            <div onClick={makeShedules}>
                                {!showModal && <ScheduleTitle scheduleTitle={scheduleTitle}/>}
                                
                            </div>
                            {isPlan?
                                <>
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
                                                <NavLink to="/schedule/detail" state={{placeId: key, place:place, scheduleTitle:scheduleTitle}}>
                                                    <PlaceItem place={place} key={key}/>
                                                </NavLink>
                                            </div>
                                        ) 
                                    })
                                }
                                </>
                                :
                                <div>
                                    {!showModal && <div>일정이 없습니다</div>}
                                    
                                    {showModal&&
                                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                                        <div className='rounded-lg p-2 border-2 border-solid border-lightMain2 m-2' >
                                            일정명 : <input type="text" />
                                        </div>
                                        <div className='rounded-lg p-2 border-2 border-solid border-lightMain2 m-2 flex items-center'>
                                            <DesktopDatePicker
                                                label={"시작일"}
                                                value={startDate}
                                                onChange={(newValue) => {
                                                        checkStartDate(newValue)   

                                                    }}
                                                    />
                                            <div>~</div>
                                            <DesktopDatePicker
                                                label={"종료일"}
                                                value={endDate}
                                                onChange={(newValue) => {
                                                        checkEndDate(newValue)
                                                    }}
                                                    />
                                        </div>
                                        <div>
                                            <div className="text-sm p-2">시작일 ~ 종료일을 선택하고 날짜별 코스를 선택하세요! </div>
                                            <div className='rounded-lg p-2 border-2 border-solid border-lightMain2 m-2 h-full min-h-[30vh]'>
                                                {(startDate && endDate)&&
                                                    <div>
                                                        날짜선택

                                                        {/* {scheduleDaysList&&
                                                            scheduleDaysList.map((dayList) =>{
                                                                return <div>{Object.keys(dayList)}</div>
                                                            })
                                                        } */}
                                                        {allDays &&
                                                            allDays.map((day) =>{
                                                                return <div>{day}</div>
                                                            })
                                                        }
                                                    </div>
                                                }
                                            </div>
                                        </div>
                                        <div onClick={submitSchedules}>
                                            <ScheduleTitle scheduleTitle={scheduleTitle}/>
                                        </div>
                                        </LocalizationProvider>
                                    }
                                </div>
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