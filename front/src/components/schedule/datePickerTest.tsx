import React, { useState } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { ko } from 'date-fns/esm/locale';
import { addDays } from "date-fns";
export default function DatePickerTest (props:{checkDate:Function|null, defaultDate:string|undefined, isDate:boolean}){
    // console.log(props.defaultDate)
    const [endDate, setEndDate] = useState<Date>()
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
                placeholderText={props.defaultDate?props.defaultDate:String(endDate?.getMonth())}
                // value={props.defaultDate?props.defaultDate:endDate?.toLocaleDateString()}
                // placeholderText={endDate?.toLocaleDateString()}
                locale={ko}
                disabled={props.isDate}
                dateFormat="yyyy년 MM월 dd일"
                className="bg-lightMain3 outline-none cursor-pointer text-[15px] w-full flex justify-center"
                />

        </>
    )
}