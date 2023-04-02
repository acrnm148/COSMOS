import { useEffect, useRef, useState } from "react";
import { NavLink, useLocation } from "react-router-dom";
import { PlaceItem } from "../../components/common/PlaceItem";
import { ScheduleMonth } from "../../components/common/ScheduleMonth";
import { WeekCalendar } from "../../components/common/WeekCalendar";
import { useMutation, useQuery } from "react-query";

// framer-motion
import { motion } from "framer-motion";

// 지울예정
import 파주출판단지 from "../../assets/schedule/파주출판단지.png"
import 녹두 from "../../assets/schedule/녹두.png"
import 베지앙 from "../../assets/schedule/베지앙.png"

import { getDayCourse, postSchedule } from "../../apis/api/course";
import { userState } from "../../recoil/states/UserState";
import { useRecoilState } from "recoil";
import { Iron } from "@mui/icons-material";
import axios from "axios";

import Modal from "../../components/common/Modal";
//mui datepicker
import { DatePicker, MobileDatePicker } from '@mui/x-date-pickers';
import { LocalizationProvider } from '@mui/x-date-pickers'
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs'
import { DesktopDatePicker } from '@mui/x-date-pickers'

//alert
import Swal from "sweetalert2";
import { getWishCourseList } from "../../apis/api/wish";
import { lstat } from "fs";
import dayjs from "dayjs";

// TODO : db를 영어에서 한글로 바꾸거나 받은 데이터 변환
const PLACECATE = {
    'cafe' : '카페'
}
interface Place{
    idx : number,
    name : string,
    imgUrl : string,
    category : string,
    location : string,
    date: string,
    placeId : number
}

// const testPlace:Place[] = [{idx:0,name:'파주 출판단지', imgUrl:'파주출판단지', category:'관광', location:"경기도", date:'2023년 2월 28일'},
//                         {idx:1,name:'베지앙', imgUrl:베지앙, category:'카페', location:"경기도", date:'2023년 2월 28일'},
//                         {idx:2,name:'녹두', imgUrl:녹두, category:'음식', location:"경기도", date:'2023년 2월 28일'}
// ]
interface COURSE {
    courseId: any;
    seq:number, name:string, places:Object[]
}
export function DaySchedulePage(){
    let location = useLocation();
    const [scheduleTitle, setScheduleTitle] = useState('파릇파릇파주여행')
    const [places, setPlaces] = useState<Place[]>([])
    const [dayClicked, setDayClicked] = useState(String(location.state.day.day.getDate()))
    const [loginUser, setLoginUser] = useRecoilState(userState)
    const [showModal, setShowModal] = useState(false)
    const [showWishModal, setShowWishModal] = useState<boolean>(false)
    
    // 일정여부
    const [isPlan, setIsPlan] = useState(false)
    // 코스여부
    const [isCourse, setIsCourse] = useState(false)
    
    // 일정 시작.종료일
    const [startDate, setStartDate] = useState<any|undefined>()
    const [endDate, setEndDate] = useState<any|undefined>()
    // 일정명
    const [planName, setPlanName] = useState<string|undefined>()
    
    const [month, getMonth] = useState(location.state.month.month)
    const[year, setYear] = useState(location.state.year)
    
    // 찜한코스
    const [wishPlaces, setWishPlaces] =useState<COURSE[]>([])
    // 찜 코스 불러오기
    const { data } = useQuery({
        queryKey: ["getWishCourseList", loginUser.acToken], // authapi 요청시 acToken 변
        queryFn: () => getWishCourseList(loginUser.seq, loginUser.acToken),
      });
    const [selectedDate, setSelectedDate] = useState<string>()
    const [showCourse, setShowCourse] = useState<number|undefined>()

    useEffect(()=>{
     if(data){
        let temptemp: COURSE[] = []
        data.map((course:COURSE)=>{
            let temp = {seq:course.seq, courseId:course.courseId,name:course.name, places:[...course.places]}
            temptemp.push(temp)
        })
        setWishPlaces([...temptemp])
     }   
    },[data])


    useEffect(()=>{
        // setPlaces(testPlace)
        setShowModal(false)
        setStartDate(undefined)
        setEndDate(undefined)
        setPlanName(undefined)
        
        const yearStr =  year.year
        const day = String(Number(dayClicked)).length === 1? '0'+String(Number(dayClicked)) :dayClicked

        const coupleId = loginUser.coupleId
        const date = yearStr+(month.length === 1?'0'+ month:month)+day

        if(dayClicked){
            axios.get(`https://j8e104.p.ssafy.io/api/plans/${coupleId}/day/${date}`)
            .then((res)=>{
                console.log('res.data, ',res)
                if(res.data.planName){
                    setScheduleTitle(res.data.planName)
                    setIsPlan(true)
                } else{
                    setScheduleTitle('일정 생성하기')
                    setIsPlan(false)
                }
                if(res.data.courses.length > 0){
                    setIsCourse(true)
                    // 코스 표출
                    console.log('해당일에 일정이 있음', res.data)
                    // let temp = res.data.coures[0].map(())
                    setPlaces(res.data.courses[0].places.map((place: {
                        placeId: any; name: any; thumbNailUrl: any; type: any; address: any; 
}, key: any) =>({
                        idx : key,
                        name : place.name,
                        imgUrl : place.thumbNailUrl,
                        category : PLACECATE[place.type as keyof typeof PLACECATE],
                        location : place.address,
                        date : res.data.courses[0].date,
                        placeId : place.placeId
                    })))

                    // interface Place{
                    //     idx : number,
                    //     name : string,
                    //     imgUrl : string,
                    //     category : string,
                    //     location : string,
                    //     date: string,
                    // }
                }
            }).catch((err)=>{
                console.log(err)
            })
        }
    }, [dayClicked])

    /////////////// 드래그앤드랍 ///////////////
    const draggingIdx = useRef<null | number>(null)
    const draggingOverIdx = useRef<null | number>(null)
    // 드래그 시작 (아이템 확인)
    const onDragStart = (e: any, idx: number) =>{
        draggingIdx.current = idx
        e.target.classList.add('grabbing')
    }
    // 드래그 놓았을 때 변화한 리스트 적용
    const onDragEnter = (e: any, idx: number) => {
        draggingOverIdx.current = idx
        const copyList = [...places]
        const dragItemContent = copyList[draggingIdx.current!]
        copyList.splice(draggingIdx.current!, 1)
        copyList.splice(draggingOverIdx.current, 0, dragItemContent)
        draggingIdx.current = draggingOverIdx.current
        draggingOverIdx.current = null
        setPlaces(copyList)
        e.target.classList.remove('grabbing')
    }
    // 드래그 이동 종료
    const onDragEnd = (e: any) =>{
        e.target.classList.remove('grabbing')
    }
    // 드래그 하는 중 아이템이 오버랩 된 상태
    const onDragOver = (e: { preventDefault: () => void; }) =>{
        e.preventDefault()
        
    }
    

    const Toast = Swal.mixin({
        toast: true,
        position: "bottom-end",
        showConfirmButton: false,
        timer: 2000,
        timerProgressBar: true,
      })

    function makeShedules(){
        if(!isPlan){
            setShowModal(true)
        }
    }
    const apiDateFormat = (date : string) =>{
        // 00000000 --> 0000-00-00
        return date.slice(0,4)+'-'+date.slice(4,6)+'-'+date.slice(6,8)
    }
    const dateToApiFormat = (date:Date) => dayjs(date).format("YYYY-MM-DD")

    const postPlan = useMutation(postSchedule)
    const [scheduleDaysList, setScheduleDaysList] = useState<{day:string, contents:[]|[COURSE], seq:any}[]>()
    function submitSchedules(){
        let courseIdAndDateList = (scheduleDaysList?.map((sd)=>({
            date : apiDateFormat(sd.day), //format 변경
            courseId :sd.seq
        })))
        //일정생성 데이터
        const dt = {
            "coupleId" : loginUser.coupleId,
            "planName" : planName,
            "startDate" : dateToApiFormat(startDate),
            "endDate" : dateToApiFormat(endDate),
            "courseIdAndDateList" : courseIdAndDateList
        }
        postPlan.mutate({
            schedule : dt,
            ac : loginUser.acToken,
            userSeq : loginUser.seq
        })
        // {
        //     "coupleId": 51106719,
        //     "planName": "부산 기장 벚꽃여행",
        //     "startDate": "2023-04-01",
        //     "endDate": "2023-04-02",
        //     "courseIdAndDateList" :[
        //         {
        //             "courseId" : 88,
        //             "date" : "2023-03-06"
        //         }
        //     ]
        //   }
    }
    
    // const [allDays, setAllDays] = useState<string[]>([])
    let allDays: any[] = []
    useEffect(()=>{
        if(!startDate || !endDate){return}
        // startDate와 EndDate가 새로 설정될때마다 날짜 리스트를 새로 만들기
        const sd = startDate.$d
        const ed = endDate.$d
        while(sd.getTime() <= ed.getTime()){
            let mon = sd.getMonth() + 1
            mon = mon < 10 ? '0'+mon : mon
            let da = sd.getDate()
            da = da < 10 ? '0'+da : da
            allDays.push(sd.getFullYear() + mon + da)
            // setAllDays([...allDays,(sd.getFullYear() + mon + da)])
            sd.setDate(sd.getDate() + 1)
        }
        let temp:{day:string, contents:[], seq:any}[]

        // allDays.forEach((day) => {
        //   temp[day] = [];
        // });
        // setScheduleDaysList(temp);
        temp = allDays.map((day:string)=>({
            seq : undefined,
            day : day,
            contents : []
        }))
        
        setScheduleDaysList(temp)
        
    },[startDate, endDate])
    const getDateDiff = (sd:Date, ed:Date) => {
        const date1 = new Date(sd);
        const date2 = new Date(ed);
        
        const diffDate = date1.getTime() - date2.getTime();
        
        return Math.abs(diffDate / (1000 * 60 * 60 * 24)); // 밀리세컨 * 초 * 분 * 시 = 일
      }
    const checkStartDate = (newValue: any) =>{
        if (endDate && (getDateDiff(endDate, newValue) > 7)){
            Toast.fire({
                title: '일정은 최대 일주일까지 등록할 수 있습니다'
            })
            setStartDate(undefined)
            return
        }
        if (endDate && endDate < newValue){
            Toast.fire({
                title: '시작일이 종료일 이후입니다!'
            })
            setStartDate(undefined)
            return
        } else{
            setStartDate(newValue)
        }
    }

    function checkEndDate(newValue: any) {
        if (startDate && (getDateDiff(newValue, startDate)) > 7){
            Toast.fire({
                title: '일정은 최대 일주일까지 등록할 수 있습니다'
            })
            setEndDate(undefined)
            return
        }
        if (startDate && startDate > newValue){
            Toast.fire({
                title: '종료일이 시작일 이전입니다!'
            })
            setEndDate(undefined)
            return
        } else{
            setEndDate(newValue)
        }
    }

    

    function getWishCourse(day : string){
        // 1. 찜코스 모달에 띄우기
        // 선택한 코스가 있을 때 
        setShowWishModal(true)
        // console.log(wishPlaces)
        setSelectedDate(day)
        // 2. 모달에서 코스 선택하면 해당 날짜로 scheduleDaysList에 데이터 담고 코스이름 표출
        // 3. 코스 이름박스 클릭시 dropdown으로 코스에  장소이미지, 장소이름 보여주기(열고닫을 수 있게)
    }

    const closeModal = (e: React.MouseEvent) => {
        e.stopPropagation()
        setShowWishModal(false)
      }

    function setCourseOnDate(idx : number, courseId:number){
        setShowWishModal(false)

        let temp:{day:string, contents:[]|[COURSE], seq:any}[] | undefined
        temp = scheduleDaysList?.map((scd)=>({
            seq : courseId,
            day : scd.day,
            contents : (scd.day === selectedDate)?[wishPlaces[idx]]:scd.contents
        }))
        
        setScheduleDaysList(temp)

    }
    return (
        <div className="bg-lightMain2 ">
            <ScheduleMonth year={year.year} month={month}/>
            <div className="bg-white rounded-t-lg w-full h-full " >
                <div className="ml-2 mr-2 flex flex-col items-center content-center h-full">
                    <div className="flex flex-col w-full justify-center md:w-5/6 lg:w-4/6  h-full">
                        <WeekCalendar setDayClicked={setDayClicked} day={location.state.day} week={location.state.week}/>
                            <div onClick={makeShedules} className="cursor-pointer">
                                {!showModal && <ScheduleTitle scheduleTitle={scheduleTitle}/>}
                            </div>
                            {isPlan? 
                                <div className="h-full overflow-scroll">
                                {
                                    places?.map((place, key)=>{
                                        return(
                                            <div
                                            className=""
                                            // onClick={} 페이지이동
                                            onDragStart={(e) => onDragStart(e, key)}
                                            onDragEnter={(e) => onDragEnter(e, key)}
                                            onDragOver={onDragOver}
                                            onDragEnd={onDragEnd}
                                            draggable
                                            key={key}
                                            >
                                                <NavLink to="/schedule/detail" state={{placeId: key, place:place, scheduleTitle:scheduleTitle}}>
                                                    <PlaceItem place={place} key={key}/>
                                                </NavLink>
                                            </div>
                                        ) 
                                    })
                                }
                                </div>
                                :
                                <div className="h-full max-h-[1000vh]">                                    
                                    {showModal&&
                                    <>
                                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                                        <div className='rounded-lg p-2 border-2 border-solid border-lightMain2 m-2' >
                                            일정명 : <input onChange={(e)=>setPlanName(e.target.value)} type="text" />
                                        </div>
                                        <div className='rounded-lg p-2 border-2 border-solid border-lightMain2 m-2 flex items-center'>
                                            <DesktopDatePicker
                                                label={"시작일"}
                                                value={startDate??startDate}
                                                onChange={(newValue) => {
                                                    checkStartDate(newValue)   
                                                    
                                                }}
                                                // format="dd-MMM-yyyy"
                                                views={["year", "month", "day"]}       
                                                />
                                            <div>~</div>
                                            <DesktopDatePicker
                                                label={"종료일"}
                                                value={endDate??endDate}
                                                // format={"year-month-day"}
                                                onChange={(newValue) => {
                                                    checkEndDate(newValue)
                                                }}
                                                />
                                        </div>
                                        </LocalizationProvider>
                                        <div className="text-sm p-2">시작일 ~ 종료일을 선택하고 날짜별 코스를 선택하세요! </div>
                                        <div className='rounded-lg p-2 border-2 border-solid border-lightMain2 m-2 min-h-[30vh] h-full max-h-[1000vh]'>
                                            {(startDate && endDate)&&
                                                <div>
                                                    {scheduleDaysList&&
                                                            // Object.keys(scheduleDaysList).map((day)=>{
                                                            scheduleDaysList.map((d:{day:string, contents:[]|[COURSE]}, key:number)=>{
                                                                return(
                                                                    <div className="날짜별코스선택-토글박스 mb-2 border-2 rounded-lg border-solid border-lightMain3 flex flex-col">
                                                                        <div className="h-16 border-2 rounded-lg flex items-center justify-between">
                                                                            <div className="flex justify-center items-center px-8 h-full rounded-l-lg bg-lightMain3">{d.day.slice(4,6)}월 {d.day.slice(6)}일</div>
                                                                            <div className="text-lightMain font-bold">
                                                                                {d.contents&&
                                                                                    d.contents.map((ee)=>{
                                                                                        return <div onClick={()=>{
                                                                                            (showCourse === key ? setShowCourse(undefined) : setShowCourse(key))
                                                                                        }
                                                                                        }>{ee.name}</div>
                                                                                    })
                                                                                }
                                                                            </div>
                                                                            <div onClick={()=>getWishCourse(d.day)} className="px-8 bg-lightMain3 h-full flex items-center justify-center cursor-pointer">코스선택</div>
                                                                        </div>
                                                                            {
                                                                                (d.contents.length !== 0 && showCourse===key) &&
                                                                                <div className="mt-2">
                                                                                   
                                                                                    {
                                                                                        d.contents.map((course, key:number) =>{
                                                                                            return <CourseComponent places={course.places} key={key} />
                                                                                        })
                                                                                    }
                                                                                </div>
                                                                            }
                                                                    </div>
                                                            ) 
                                                        })
                                                    }
                                                    
                                                </div>
                                            }
                                        </div>
                                        <div className="min-h-[20vh]" onClick={submitSchedules}>
                                            <ScheduleTitle scheduleTitle={scheduleTitle}/>
                                        </div>
                                        </>
                                    }
                                </div>
                        }
                    </div>
                </div>
            </div>
            <div className="찜한코스모달 ">
                <Modal
                    open={showWishModal}
                    close={closeModal}
                    header="찜한 코스"
                    size="large"
                >
                <div className="w-full flex overflow-x-scroll scrollbar-hide">
                    {wishPlaces&&
                        wishPlaces.map((course, key)=>{
                            return(
                                <div className="w-full">
                                    <div className="w-full h-[1px] bg-lightMain3 mb-2"></div>
                                    <CourseComponent places={course.places} key={key}/>
                                    <div className="w-full bg-lightMain3 rounded flex justify-between rounded-lg mb-2">
                                        <div className="p-2 px-6">{course.name}</div>
                                        <div 
                                            className=" p-2 px-6 bg-lightMain2 rounded-r-lg cursor-pointer"
                                            onClick={()=>setCourseOnDate(key, course.courseId)}
                                        >선택</div>
                                    </div>
                                </div>
                            ) 
                        })}
                   
                </div>
                </Modal>
            </div>
        </div>
    )
}

function ScheduleTitle(props:{scheduleTitle:string}) {
    return(
        <div className="bg-lightMain2 h-10 flex justify-center items-center font-bold text-white rounded-lg">{props.scheduleTitle}</div>
    )
}
const CourseComponent = (props:{places:any[]}) =>{
    return(
        <div className="w-full h-36 flex overflow-x-scroll scrollbar-hide">
            {
                props.places.map((p:any)=>{
                    let name = p.name.length > 7 ? p.name.slice(0, 7).concat("...") : p.name
                    return(
                        <div
                        
                        key={p.placeId}
                        className="float-left flex-none w-28 min-h-28 max-h-34 mr-3 text-center"
                        >
                        <img
                            className="w-full h-24 rounded-lg"
                            src={p.thumbNailUrl}
                            alt="img"
                            />
                        <div className="w-full h-8 mt-2 text-sm">
                            {name}
                        </div>
                    </div>
                )
            })
        }
        </div>
    )
}