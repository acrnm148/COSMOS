import { DatePicker, LocalizationProvider } from "@mui/lab";
import { TextField } from "@mui/material";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";
import { SetStateAction, forwardRef, useState } from "react"

export default function DatePickerTest (){
    const [startDate, setStartDate] = useState<Date | null >()
    const ExampleCustomInput = forwardRef(({ value, onClick }:any, ref) => (
        <button className="example-custom-input" onClick={onClick}>
        {value}
        </button>
    ));
    return(
        <LocalizationProvider dateAdapter={AdapterDayjs}>
        <DatePicker 
                value = {new Date()}
                onChange = {(d: SetStateAction<Date | null | undefined>) => setStartDate(d)}
                renderInput = {(params: JSX.IntrinsicAttributes) => <TextField {...params} />}
            />
        </LocalizationProvider>
    )
}