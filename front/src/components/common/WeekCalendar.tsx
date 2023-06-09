import { useEffect, useState } from "react"
import { useRecoilState } from "recoil"
import { darkMode } from "../../recoil/states/UserState"

const DAYS:string[] = ['일', '월', '화', '수', '목', '금', '토', '일', '월', '화', '수', '목', '금', '토']
export function WeekCalendar(props:{day:any, week:any, setDayClicked:Function}){
    // 오늘 날짜 기준으로 일주일 표출
    let date = props.day.day.toLocaleDateString()

    const today = new Date().getDate() // 오늘 몇일
    const [dayday, setDayday] = useState(DAYS[props.day.day.getDay()]) // 오늘 무슨요일

    const [isDark, x] = useRecoilState(darkMode)
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
    const [clickDate, SetClickDate] = useState<string>(props.day.day.getDate())
    // console.log(props.day.day.getDate())
    function isClicked(day:string, key:number){
        // console.log('clickDate, day',typeof(clickDate), typeof(day))
        return clickDate == day
    }
    return(
        <div className="flex h-24 w-full my-2 justify-between ">
            {
                result.map((day, key) => {
                    return (
                        <div 
                            onClick={()=>{
                                SetClickDate(day)
                                props.setDayClicked(day)
                            }} 
                            className=" flex flex-col justify-center content-center items-center lg-w-16 lg:mx-2" key={key}>
                            <div className={(isDark?"text-white": "" )+ " text-sm font-semibold "}>
                                { DAYS[++dayIdx]}
                            </div>
                            <div 
                                className={
                                    (isClicked(day, key)? isDark?'bg-darkMain ':'bg-lightMain text-white ': isToday(day)?'bg-calendarDark ':'bg-calendarGray ')
                                + (isDark ? "hover:bg-darkMain2 ":"hover:bg-lightMain2 ") 
                                +' cursor-pointer flex rounded-full h-12 w-12 justify-center content-center items-center sm:h-16 sm:w-16'
                             }>{day}
                             </div>
                        </div>
                    )
                })
            }
        </div>
            
    )
}