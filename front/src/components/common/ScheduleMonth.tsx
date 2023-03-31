import { useEffect, useState } from "react"
import { NavLink } from "react-router-dom"
import galleryIcon from "../../assets/schedule/gallery.png"

export function ScheduleMonth(props:{year:string, month:string}){
    return(
        <div className="bg-lightMain2 h-20 flex items-center justify-between p-5 text-xl font-bold text-white">
           <NavLink to={"/schedule/month"}><p >{props.year} {props.month}</p> </NavLink>
           <NavLink to={'/schedule/gallery'}> <img src={galleryIcon}/></NavLink>
        </div>
    )
}

