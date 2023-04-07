import { useEffect, useState } from "react"
import { NavLink } from "react-router-dom"
import { useRecoilState } from "recoil"
import galleryIcon from "../../assets/schedule/gallery.png"
import { darkMode } from "../../recoil/states/UserState"

export function ScheduleMonth(props:{year:string, month:string}){
    const[isDark, x] = useRecoilState(darkMode)
    return(
        <div className={(isDark?
            "bg-darkMain2 h-20 flex "
            :"bg-lightMain2 h-20 flex items-center justify-between p-5 text-xl font-bold text-white cursor-pointer") 
            + " items-center justify-between p-5 text-xl font-bold text-white cursor-pointer"}>           
                <NavLink to={"/schedule/month"}><p >{props.year}년 {props.month}월</p> </NavLink>
           <p className="cursor-pointer"><NavLink to={'/schedule/gallery'}> <img src={galleryIcon}/></NavLink></p>
        </div>
    )
}

