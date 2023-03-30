//mui datepicker
import { DatePicker } from '@mui/x-date-pickers';
import { LocalizationProvider } from '@mui/x-date-pickers'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { DesktopDatePicker } from '@mui/x-date-pickers'
import {TextField} from "@mui/material";

export function GalleryPage(){
    return (
        <div>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
                <DesktopDatePicker
                    label={"나온다"}
                    // value={startDate}
                    // onChange={(newValue) => {
                    //     setStartDate(newValue)
                    // }}
                />
                </LocalizationProvider>
        </div>
    )
}