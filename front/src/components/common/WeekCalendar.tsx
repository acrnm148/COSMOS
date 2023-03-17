import { useState } from "react"

const DAYS:string[] = ['일', '월', '화', '수', '목', '금', '토', '일', '월', '화', '수', '목', '금', '토']
export function WeekCalendar(){
    // 오늘 날짜 기준으로 일주일 표출
    let date = new Date().toLocaleDateString()
    
    const today = new Date().getDate() // 오늘 몇일
    const dayday = DAYS[new Date().getDay()] // 오늘 무슨요일
    let dayIdx = DAYS.indexOf(dayday) -1 // 요일 인덱스 시작
    
    let result = []
    result.push(date.slice(-3, -1))
    let dday = Date.parse(date)
    for (let i = 0; i<6; i++){
        dday += 86400000
        result.push(new Date(dday).toLocaleDateString().slice(-3, -1))
    }
    function isToday(day:string){
        return day === String(today)
    }
    // 선택한 날짜
    const [clickDate, SetClickDate] = useState(String(today))
    function isClicked(day:string){
        return clickDate === day
    }
    return(
        <div className="flex h-24 w-full mt-2 justify-between">
            {
                result.map((day, key) => {
                    return (
                        <div onClick={()=>SetClickDate(day)} className="flex flex-col justify-center content-center items-center" key={key}>
                            <div className="text-sm font-semibold">
                                { isToday(day)?'오늘':DAYS[++dayIdx]}
                            </div>
                            <div 
                                className={
                                    (isClicked(day)? 'bg-lightMain text-white': isToday(day)?'bg-calendarDark':'bg-calendarGray')
                                + ' flex rounded-full h-12 w-12 justify-center content-center items-center sm:h-16 sm:w-16 md:h-20 md:w-20'
                             }>{day}
                             </div>
                        </div>
                    )
                })
            }
        </div>
            
    )
}