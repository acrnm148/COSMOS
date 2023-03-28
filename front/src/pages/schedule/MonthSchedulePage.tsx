import { ScheduleMonth } from "../../components/common/ScheduleMonth";
// import { format, compareAsc, startOfWeek, startOfMonth, endOfWeek, endOfMonth, startOfDay, addDays, addMonths, subMonths } from 'date-fns'
import { JSXElementConstructor, ReactElement, ReactFragment, ReactPortal, useEffect, useState } from "react";
import galleryIcon from "../../assets/schedule/gallery.png"
import { NavLink } from "react-router-dom";

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
    let data = takeMonth(currentDate)()
    const [monthLR, setMonthLR] = useState<string>()
    useEffect(()=>{
        data = takeMonth(currentDate)()
        setSchedule((prev) => new Map([...prev, [20, ['기장여행','일광당','일광온오프','베지나랑']]]))
        setSchedule((prev) => new Map([...prev, [28, ['경주 벚꽃여행','고고고','경주 머시기','머머머']]]))
        if((month === '12월') && (monthLR === 'R')){setYear(format(subYears(currentDate, 1), 'yyyy년'))}
        else if((month === '1월') && (monthLR === 'L')){setYear(format(addYears(currentDate, 1), 'yyyy년'))}
        setMonth(format(currentDate, 'M월'))
    }, [currentDate])
    const [year, setYear] = useState(format(currentDate, 'yyyy년'))
    const [month, setMonth] = useState(format(currentDate, 'M월'))
    const [schedule, setSchedule] = useState(new Map())

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
                <p>{year} {month}</p> 
                <img src={galleryIcon} />
            </div>
            <div className="box-border w-full h-full bg-white">
                
                <div className="flex justify-between p-8 py-4">
                    <button onClick={prevMonth} className="bg-lightMain2 p-6 py-4 rounded-md">이전달</button>
                    <button onClick={nextMonth} className="bg-lightMain2 p-6 py-4 rounded-md">다음달</button>
                </div>
                    <div className="border">
                        <WeekNames/>
                        {data.map((week, index) => <div className="grid grid-cols-7" key={index}>
                            {week.map((day:any) =>{
                                if(format(day, 'M월') === month){
                                    return(
                                    <div
                                        className={`min-h-[8rem] flex flex-col items-center border-b border-r p-2`}
                                        key={day}>
                                        <NavLink to={"/schedule/day"} state={{week:{week}, day:{day}}}>
                                        <div className={`number flex text-xs font-bold h-6 w-6 justify-center items-center cursor-pointer`}> 
                                            {format(day, 'dd')}
                                        </div>
                                            {schedule.has(Number(format(day, 'dd'))) &&
                                                schedule.get(Number(format(day, 'dd'))).map((scd: string, idx:number)=>{
                                                    if (idx === 0){
                                                        return <div className="bg-lightMain text-white font-bold text-sm m-0.5 w-full rounded-md p-0.5 overflow-hidden "><p>{scd}</p></div>
                                                    }
                                                    return <div className="text-sm m-0.5 w-full text-darkBackground2 whitespace-nowrap bg-lightMain3 rounded-md p-0.5 overflow-hidden"><p>{scd}</p></div>
                                                })
                                                    
                                            }
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
