import { useState } from "react"
import { NavLink } from "react-router-dom"
import galleryIcon from "../../assets/schedule/gallery.png"

export function ScheduleMonth(){
    const [y, setYear] = useState<number|string>(new Date().getFullYear())
    const [m, setMonth] = useState<number|string>(new Date().getMonth() +1) // 오늘 몇월

    return(
        <div className="bg-lightMain2 h-20 flex items-center justify-between p-5 text-xl font-bold text-white">
           <NavLink to={"/schedule/month"}><p >{y}년 {m}월</p> </NavLink>
           <NavLink to={'/schedule/gallery'}> <img src={galleryIcon}/></NavLink>
        </div>
    )
}