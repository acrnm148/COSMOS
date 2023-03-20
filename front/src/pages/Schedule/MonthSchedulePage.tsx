import { ScheduleMonth } from "../../components/common/ScheduleMonth";
// import { format, compareAsc, startOfWeek, startOfMonth, endOfWeek, endOfMonth, startOfDay, addDays, addMonths, subMonths } from 'date-fns'
import { JSXElementConstructor, ReactElement, ReactFragment, ReactPortal, useEffect, useState } from "react";
import galleryIcon from "../../assets/schedule/gallery.png"

const {
    format,
    startOfWeek, 
    startOfMonth, 
    endOfWeek, 
    endOfMonth, 
    startOfDay, 
    addDays, 
    addMonths, 
    subMonths
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
    const [selectedDate, setSelectedDate] = useState(new Date())
    const data = takeMonth(currentDate)()
    const [year, setYear] = useState(format(currentDate, 'yyyy년'))
    const [month, setMonth] = useState(format(currentDate, 'M월'))
    const [schedule, setSchedule] = useState(new Map())

    const nextMonth = () => {
        setCurrentDate(addMonths(currentDate, 1))
        setMonth(format(currentDate, 'M월'))
    };
    const prevMonth = () => {
        setCurrentDate(subMonths(currentDate, 1))
        setMonth(format(currentDate, 'M월'))
    };
    function setsetSchedule(){
        setSchedule((prev) => new Map([...prev, [20, ['일광당','일광온오프','베지나랑']]]))
        setSchedule((prev) => new Map([...prev, [28, ['고고고','경주 머시기','머머머']]]))
    }

    return (
        <div className="bg-lightMain2 w-screen h-screen">
           <div className="bg-lightMain2 h-20 flex items-center justify-between p-5 text-xl font-bold text-white">
                <p>{year} {month}</p> 
                <img src={galleryIcon} />
            </div>
            <div className="box-border w-full h-full">
                <button onClick={setsetSchedule} className="bg-white rounded-xl p-4">정보 넣기</button>
                
                <div className="flex justify-between">
                    <div onClick={prevMonth}>이전달</div>
                    <div onClick={nextMonth}>다음달</div>
                </div>
                    <div className="border">
                        <WeekNames/>
                        {data.map((week, index) => <div className="grid grid-cols-7" key={index}>
                            {week.map((day:any) =>{
                                return(
                                <div onClick={() => setSelectedDate(day)}
                                    className={`h-28 flex flex-col items-center border-b border-r`}
                                    key={day}>
                                    <div className={`flex text-xs font-bold h-6 w-6 justify-center items-center cursor-pointer`}> 
                                        {format(day, 'dd')}
                                        {/* {Number(format(day, 'dd'))} */}
                                        {schedule.has(Number(day)) &&
                                            <div>
                                                <div>{
                                                        schedule.get(Number(day)).map((scd: any)=>{
                                                            return <div>{scd}</div>
                                                        })
                                                    }</div>
                                            </div>
                                        }
                                    </div>
                                </div>)}
                                )}
                        </div>)}
                    </div>
            </div>
        </div>
    )
}
