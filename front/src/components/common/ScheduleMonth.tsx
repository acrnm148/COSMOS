import galleryIcon from "../../assets/schedule/gallery.png"

export function ScheduleMonth(){
    const year = new Date().getFullYear()
    const month = new Date().getMonth() +1 // 오늘 몇월
    return(
        <div className="bg-lightMain2 h-20 flex items-center justify-between p-5 text-xl font-bold text-white">
           <p>{year}년 {month}월</p> 
           <img src={galleryIcon} />
        </div>
    )
}