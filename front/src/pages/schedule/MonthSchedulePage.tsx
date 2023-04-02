import { ScheduleMonth } from "../../components/common/ScheduleMonth";
// import { format, compareAsc, startOfWeek, startOfMonth, endOfWeek, endOfMonth, startOfDay, addDays, addMonths, subMonths } from 'date-fns'
import { JSXElementConstructor, ReactElement, ReactFragment, ReactPortal, useEffect, useState } from "react";
import galleryIcon from "../../assets/schedule/gallery.png"
import { NavLink } from "react-router-dom";
import { useQuery } from "react-query";
import { getMonthSchedule } from "../../apis/api/schedule";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";
import axios from "axios";

const {
    format,
    startOfWeek, 
    startOfMonth, 
    endOfWeek, 
    endOfMonth, 
    startOfDay, 
    addDays, 
    addMonths, 
    subMonths,
    addYears,
    subYears,
} = require('date-fns')

function takeWeek(start = new Date()) {
    let date = startOfWeek(startOfDay(start))

    return function () {
        const week = [...Array(7)].map((_, index) => addDays(date, index))
        date = addDays(week[6], 1)
        return week
    }
}


function WeekNames() {
    const weeks =['일', '월', '화', '수', '목', '금', '토']
    return (
        <div className="grid grid-cols-7 text-xs text-gray-500">
            {weeks.map((dayName)=>(
                <div className="h-8 flex items-center justify-center border-r" key={dayName}>{dayName}</div>
            ))}
        </div>
    );
}

function takeMonth(start = new Date()) {
    let month: any[] = []
    let date = start
    function lastDayOfRange(range: string | any[]) {
        return range[range.length - 1][6]
    }

    return function () {
        const weekGen = takeWeek(startOfMonth(date))
        const endDate = startOfDay(endOfMonth(date))
        month.push(weekGen())

        while (lastDayOfRange(month) < endDate) {
            month.push(weekGen())
        }

        const range = month
        month = [];
        date = addDays(lastDayOfRange(range), 1)
        return range
    }
}

//////////////////////////////////////////////////////////////////////////
export function MonthSchedulePage(){
    const [currentDate, setCurrentDate] = useState(new Date());
    let monthCalendar = takeMonth(currentDate)()
    const [monthLR, setMonthLR] = useState<string>()
    const [year, setYear] = useState(format(currentDate, 'yyyy'))
    const [month, setMonth] = useState(format(currentDate, 'M'))
    const [schedule, setSchedule] = useState(new Map())
    const [apiDate, setApiDate] = useState<string>(year + (month.length === 2 ? month : '0' + month))
    const [loginUser, setLoginUser] = useRecoilState(userState)
    
    // 월별 일정조회
    useEffect(()=>{
        const day = (year + (month.length === 2 ? month : '0' + month))
        let data:any
        axios.get(`https://j8e104.p.ssafy.io/api/plans/${loginUser.coupleId}/month/${day}`)
        .then((res) =>{
            data = res.data
            if(data){
                data.map((plan: { courses: { name:string, places: { name: any; }[]; date:string}[]; }) =>{
                    console.log('data', data)
                    if (plan.courses.length === 0){return}
                    const planDate = plan.courses[0].date.slice(-2)
                    let planPlaces: any[] = []
                    planPlaces.push(plan.courses[0].name)
                    plan.courses[0].places.map((place: { name: any; }) =>{
                        planPlaces.push(place.name)
                    })
                    // return [planDate, planPlaces]
                    console.log(new Map( [...schedule, [planDate, planPlaces]]))
                    setSchedule((prev) => new Map( [...prev,[planDate, planPlaces]]))
                })
            }else{
                setSchedule(new Map())
            }
        })
        
    },[month, year])

    // 월 달력 생성 함수
    function setCalendar(){
    }
    useEffect(()=>{
        monthCalendar = takeMonth(currentDate)()
        if((month === '12') && (monthLR === 'R')){setYear(format(subYears(currentDate, 1), 'yyyy'))}
        else if((month === '1') && (monthLR === 'L')){setYear(format(addYears(currentDate, 1), 'yyyy'))}
        setMonth(format(currentDate, 'M'))
        setCalendar()
    }, [currentDate])

    const nextMonth =  () => {
        setCurrentDate(addMonths(currentDate, 1))
        setMonthLR('R')
    };
    const prevMonth = () => {
        setCurrentDate(subMonths(currentDate, 1))
        setMonthLR('L')
    };

    function setSelectedDateAndWeek(week:Date[],day:Date){
        console.log('선택된 주',week)
        console.log('선택된 일', day)
    }
    return (
        <div className="bg-lightMain2 w-screen h-screen">
           <div className="bg-lightMain2 h-20 flex items-center justify-between p-5 text-xl font-bold text-white cursor-pointer">
                <p>{year}년 {month}월</p> 
                <img src={galleryIcon} />
            </div>
            <div className="box-border w-full h-full bg-white">
                
                <div className="flex justify-between p-8 py-4">
                    <button onClick={prevMonth} className="bg-lightMain2 p-6 py-4 rounded-md">이전달</button>
                    <button onClick={nextMonth} className="bg-lightMain2 p-6 py-4 rounded-md">다음달</button>
                </div>
                    <div className="border">
                        <WeekNames/>
                        {monthCalendar.map((week, index) => <div className="grid grid-cols-7" key={index}>
                            {week.map((day:any) =>{
                                if(format(day, 'M') === month){
                                    return(
                                    <div
                                        className={`overflow-hidden min-h-[8rem] flex flex-col items-center border-b border-r p-2`}
                                        key={day}>
                                        <NavLink to={"/schedule/day"} state={{year:{year}, month:{month}, week:{week}, day:{day}}}>
                                        <div className={`number flex flex-col text-xs font-bold  w-full max-w-[100%] justify-center items-center cursor-pointer`}> 
                                            {format(day, 'dd')}
                                            {schedule.has(format(day, 'dd')) &&
                                                schedule.get(format(day, 'dd')).map((scd: string, idx:number)=>{
                                                    if (idx === 0){
                                                        return <div className="bg-lightMain text-white font-bold text-sm m-0.5 w-full rounded-md p-0.5"><p>{scd}</p></div>
                                                    }
                                                    return <div className="text-sm h-1 m-0.5 w-full text-darkBackground2 whitespace-nowrap bg-lightMain3 rounded-md p-0.5 overflow-hidden"><p></p></div>
                                                })
                                                
                                            }
                                            </div>
                                        </NavLink>
                                    </div>

                                    
                                    )}
                                    else{
                                        return(
                                            <div className={`min-h-[8rem] flex flex-col items-center border-b border-r p-2 bg-gray`}
                                                    key={day}>
                                                    <div className={`number flex text-xs font-bold h-6 w-6 justify-center items-center cursor-pointer`}> 
                                                        {/* {format(day, 'dd')} */}
                                                    </div>
                                            </div>
                                        )
                                    }
                                }
                                )}
                        </div>)}
                    </div>
            </div>
        </div>
    )
}
