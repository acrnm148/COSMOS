import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { ko } from 'date-fns/esm/locale';
import { addDays } from "date-fns";
import { useRecoilState } from "recoil";
import { darkMode } from "../../recoil/states/UserState";
export default function DatePickerTest (props:{checkDate:Function|null, defaultDate:string|undefined, isDate:boolean}){
    const [endDate, setEndDate] = useState<Date>()
    const [isDark, x] = useRecoilState(darkMode)
    function checkDates(d:any){
        if(props.checkDate){
            props.checkDate(d)
        }
        setEndDate(d)
        checkDefaultDate()
    }
 
    function checkDefaultDate(){
        console.log('props.defaultDate', props.defaultDate)
        console.log(props.defaultDate?true:false)
        console.log('endDate', endDate)
    }
    return(
        <>
        <DatePicker 
                selected = {endDate}
                onChange = {(d:any) => {
                    // setEndDate(d)
                    checkDates(d)
                }}
                placeholderText={props.defaultDate&&props.defaultDate}
                // value={props.defaultDate&&props.defaultDate}
                // placeholderText={endDate?.toLocaleDateString()}
                locale={ko}
                disabled={props.isDate}
                dateFormat="yyyy년 MM월 dd일"
                className={(isDark?"bg-darkMain5 " :"bg-lightMain3 ") +  "outline-none cursor-pointer text-[15px] w-full flex justify-center"}
                />

        </>
    )
}