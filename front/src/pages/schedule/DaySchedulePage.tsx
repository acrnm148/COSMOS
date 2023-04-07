import React, { useEffect, useRef, useState } from "react";
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

import { getDayCourse, postSchedule, putSchedule } from "../../apis/api/course";
import { darkMode, userState } from "../../recoil/states/UserState";
import { useRecoilState } from "recoil";
import { Iron } from "@mui/icons-material";
import axios from "axios";

import Modal from "../../components/common/Modal";
//react datepicker
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import { ko } from 'date-fns/esm/locale';import { LocalizationProvider } from '@mui/x-date-pickers'

//alert
import Swal from "sweetalert2";
import { getWishCourseList } from "../../apis/api/wish";
import { lstat } from "fs";
import dayjs from "dayjs";
import DatePickerTest from "../../components/schedule/datePickerTest";
 
import { PLACECATE } from "../../recoil/states/PlaceState"
import { Place } from "../../recoil/states/PlaceState"
import { start } from "repl";

// const testPlace:Place[] = [{idx:0,name:'파주 출판단지', imgUrl:'파주출판단지', category:'관광', location:"경기도", date:'2023년 2월 28일'},
//                         {idx:1,name:'베지앙', imgUrl:베지앙, category:'카페', location:"경기도", date:'2023년 2월 28일'},
//                         {idx:2,name:'녹두', imgUrl:녹두, category:'음식', location:"경기도", date:'2023년 2월 28일'}
// ]
export interface COURSE {
    courseId: any,
    planName: string,
    seq:number, name:string, places:Object[]
}
// interface EditSchedule {
//     name : string,
//     startDate : 
// }
export function DaySchedulePage(){
    let location = useLocation();
    const [scheduleTitle, setScheduleTitle] = useState<string>()
    const [places, setPlaces] = useState<Place[]>([])
    const [dayClicked, setDayClicked] = useState(String(location.state.day.day.getDate()))
    const [loginUser, setLoginUser] = useRecoilState(userState)
    const [showModal, setShowModal] = useState(false)
    const [showWishModal, setShowWishModal] = useState<boolean>(false)
    
    // 일정여부
    const [isPlan, setIsPlan] = useState(false)
    // 일정 수정중 여부
    const [isEditing, setEditing] = useState(false)
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

    const[isDark, x] = useRecoilState(darkMode)

    useEffect(()=>{
    // if(data && Object.keys(data).includes('status') && (data.status === '401')){
    //     GetNewAcToken()
    //     return
    // }
     if(data){
        let temptemp: COURSE[] = []
        data.map((course:COURSE, key:number)=>{
            let temp = {seq:key, planName : course.planName, courseId:course.courseId,name:course.name, places:[...course.places]}
            temptemp.push(temp)
        })
        setWishPlaces([...temptemp])
     }   
    },[data])

    
    const [planId, setPlanId] = useState<number>()
    const yearStr =  year.year
    useEffect(()=>{
        // setPlaces(testPlace)
        setShowModal(false)
        setStartDate(undefined)
        setEndDate(undefined)
        setPlanName(undefined)
        
        const day = String(Number(dayClicked)).length === 1? '0'+String(Number(dayClicked)) :dayClicked
        
        const coupleId = loginUser.coupleId ? loginUser.coupleId : "0"
        const date = yearStr+(month.length === 1?'0'+ month:month)+day
        
        
        if(dayClicked){
            setEditing(false)
            if(dayClicked === '-1'){return}
            setStartDate(new Date(makeStartDateDateFormat()))
            axios.get(`https://j8e104.p.ssafy.io/api/plans/${coupleId}/day/${date}`)
            .then((res)=>{
                console.log("dayClicked", res)
                if(res.data.planName){
                    setScheduleTitle(res.data.planName)
                    setIsPlan(true)
                } else{
                    setScheduleTitle("")
                    setIsPlan(false)
                }
                if(res.data.courses&&(res.data.courses.length > 0)){
                    setPlanId(res.data.planId)
                    setIsCourse(true)
                    // 코스 표출
                    // console.log('해당일에 일정이 있음', res.data)
                    setEndDate(new Date(res.data.endDate))
                    setIsPlan(true)
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
                        placeId : place.placeId,
                    })))
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
    const Toast2 = Swal.mixin({
    toast: true,
    position: "bottom-end",
    showConfirmButton: true,
    showCancelButton :true,
    confirmButtonColor : "#FF8E9E",
    cancelButtonColor: "#B9B9B9",
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
    const putPlan = useMutation(putSchedule)
    const [scheduleDaysList, setScheduleDaysList] = useState<{day:string, contents:[]|[COURSE], seq:any, name:string}[]>()

    
    // const [allDays, setAllDays] = useState<string[]>([])
    let allDays: any[] = []
    useEffect(()=>{
        if(!startDate || !endDate){return}
        if(isPlan && !isEditing){return}
        if(isEditing && scheduleDaysList && scheduleDaysList[0].seq !== undefined){
            console.log('여기야?', isEditing, scheduleDaysList)
            return
            
        }
        // startDate와 EndDate가 새로 설정될때마다 날짜 리스트를 새로 만들기
        const sd = startDate
        const ed = endDate
        while(sd.getTime() <= ed.getTime()){
            let mon = sd.getMonth() + 1
            mon = mon < 10 ? '0'+mon : mon
            let da = sd.getDate()
            da = da < 10 ? '0'+da : da
            allDays.push(sd.getFullYear() + mon + da)
            // setAllDays([...allDays,(sd.getFullYear() + mon + da)])
            sd.setDate(sd.getDate() + 1)
        }
    
        let temp:{day:string, contents:[], seq:any, name:string}[]

        temp = allDays.map((day:string)=>({
            seq : undefined,
            day : day,
            contents : [],
            name : ''
        }))
        
        setScheduleDaysList(temp)
        
    },[startDate, endDate])


    function submitSchedules(){
        console.log(endDate)
        if(planName == undefined){
            Toast.fire({
                title : '일정명을 작성해주세요.'
            }).then(()=> {return})
        } else if(endDate == undefined){
            Toast.fire({
                title : '종료일을 선택해주세요.'
            }).then(()=> {return})
        } else{
            Toast2.fire({
                title : isEditing ? "일정을 수정하시겠습니까?" :"일정을 생성하시겠습니까?"
            }).then((result)=>{
                if (result.isConfirmed) {
                    let courseIdAndDateList = (scheduleDaysList?.map((sd)=>({
                        date : apiDateFormat(sd.day), //format 변경
                        courseId :sd.seq
                    })))
                    //일정생성 데이터
                    const dt = {
                        "coupleId" : loginUser.coupleId?loginUser.coupleId:"0",
                        "planName" : planName,
                        "courseIdAndDateList" : courseIdAndDateList,
                        "startDate" : courseIdAndDateList?.[0].date, 
                        "endDate" : courseIdAndDateList?.[courseIdAndDateList?.length-1].date,
                        "planId" : planId
                    }
                    console.log('dt-------------', dt)
                    if(isEditing){
             
                        putPlan.mutate({
                            schedule : dt,
                            ac : loginUser.acToken,
                            userSeq : loginUser.seq
                        })
                    } else{
                        postPlan.mutate({
                            schedule : dt,
                            ac : loginUser.acToken,
                            userSeq : loginUser.seq
                        })
                    }
                    
                    setScheduleTitle(planName)
                    setIsPlan(true)
                    setEditing(false)
                }
                })

        }
        
    }



    const getDateDiff = (sd:Date, ed:Date) => {
        const date1 = new Date(sd);
        const date2 = new Date(ed);
        
        const diffDate = date1.getTime() - date2.getTime();
        
        return Math.abs(diffDate / (1000 * 60 * 60 * 24)); // 밀리세컨 * 초 * 분 * 시 = 일
      }
    const checkStartDate = (newValue: any) =>{
        console.log('mui datetiem', newValue)
        const today = new Date()
        if (endDate && (getDateDiff(endDate, newValue) > 7)){
            Toast.fire({
                title: '일정은 최대 일주일까지 등록할 수 있습니다'
            })
            setStartDate(undefined)
            return
        }
        if(today.setDate(today.getDate()-1) > newValue){
            console.log(startDate, endDate)
            Toast.fire({
                title : '지난 날짜의 일정을 생성할 수 없습니다'
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

        // console.log('일정 종료일 수정 요망 요망 ',newValue)
        const today = new Date()
        if (startDate && (getDateDiff(newValue, startDate)) > 7){
            Toast.fire({
                title: '일정은 최대 일주일까지 등록할 수 있습니다'
            })
            setEndDate(undefined)
            return
        }
        console.log('tartDate.getTime() > newValue.getTime()', startDate.getTime(), newValue.getTime())
        if(startDate.getTime() > newValue.getTime()){
            console.log(startDate, endDate)
            Toast.fire({
                title : '지난 날짜의 일정을 생성할 수 없습니다'
            })
            setEndDate(undefined)
            return
        // }
        // if (startDate && startDate.getTime() > newValue.getTime()){
        //     Toast.fire({
        //         title: '종료일이 시작일 이전입니다!'
        //     })
        //     setEndDate(undefined)
        //     return
        } else{
            // console.log('enddate수정수정', endDate)
            // console.log("startDate", startDate)
            setEndDate(newValue)
        }
    }

    

    function getWishCourse(day : string){
        // 코스리스트

        // 1. 찜코스 모달에 띄우기
        // 선택한 코스가 있을 때 $
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
        let temp:{day:string, contents:[]|[COURSE], seq:any, name:string}[] | undefined
        temp = scheduleDaysList?.map((scd)=>({
            seq : (scd.day === selectedDate)?courseId:scd.seq,
            day : scd.day,
            contents : (scd.day === selectedDate)?[wishPlaces[idx]]:scd.contents,
            name : scd.name
        }))
        
        setScheduleDaysList(temp)
        setShowCourse(idx)

    }

    function alreadyInPlan(courseId :number){
        let result = false
        scheduleDaysList?.map(scd => {
            if(scd.seq === courseId){result = true}
        })
        return result
    }
    function editSchedule(){
        axios.get(`https://j8e104.p.ssafy.io/api/plans/${loginUser.coupleId?loginUser.coupleId:"0"}/${planId}`)
        .then((res) => {
            // 일정명 세팅
            setPlanName(res.data.planName)
            // 기존의 시작, 종료일 세팅
            setStartDate(new Date(res.data.startDate))
            setEndDate(new Date(res.data.endDate))
            const temp = dayClicked
            setDayClicked('')
            setDayClicked(temp)
            return res.data.courses
        }).then((res)=>{
            console.log('여기',res)
            const editSche = res.map((cor: { id: any; name:string, date: string; coursePlaces: { map: () => any; }; }, key: any) => ({
                seq : cor.id,
                day: cor.date.replaceAll('-', ''), // 20232023 형식// (cor.date.slice(5,7)+ '월' + cor.date.slice(8,10) + '일'),
                contents : [],
                name : cor.name
            }))
            // console.log('editSche',editSche)
            setScheduleDaysList([...editSche])

            setIsPlan(false)
            setEditing(true)
        })
    }

    // startDate를 문자열로 리턴하는 함수
    function makeStartDateFormat(){
        return (year.year+'년 '+month+'월 '+(dayClicked.length==1? '0'+dayClicked : dayClicked)+'일')
    }
    // startDate를 문자열로 리턴하는 함수
    function makeStartDateDateFormat(){
        return (year.year+'. '+month+'. '+(dayClicked.length==1? '0'+dayClicked : dayClicked))
    }
    return (
        <div className={(isDark?"bg-darkBackground2" :"bg-white")+" w-full h-full"}>
            <ScheduleMonth year={year.year} month={month}/>
            <div className={"rounded-t-lg w-full h-full pb-20"} >
                <div className={"ml-2 mr-2 flex flex-col items-center content-center h-full"}>
                    <div className={"flex flex-col w-full justify-center md:w-5/6 lg:w-4/6  h-full"}>
                        <WeekCalendar setDayClicked={setDayClicked} day={location.state.day} week={location.state.week}/>
                            <div onClick={makeShedules} className="cursor-pointer">
                                {!showModal && scheduleTitle && !isEditing && <ScheduleTitle scheduleTitle={scheduleTitle}/>}
                            </div>
                            {isPlan? 
                                <div className="h-full overflow-scroll">
                                {
                                    ///////////////////////////////////////////// 코스표출 START /////////////////////////////////////////////
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
                                                <NavLink to="/schedule/detail" state={{placeId: key, place:place, scheduleTitle:scheduleTitle, planId:planId}}>
                                                    <PlaceItem place={place} key={key}/>
                                                </NavLink>
                                            </div>
                                        ) 
                                    })
                                     ///////////////////////////////////////////// 코스표출 END /////////////////////////////////////////////
                                }
                                <div className="일정수정 cursor-pointer" onClick={()=>editSchedule()}>
                                    <ScheduleTitle scheduleTitle={"일정 수정하기"}/>
                                </div>
                                </div>
                                :
                                ///////////////////////////////////////////// 일정생성/수정 START /////////////////////////////////////////////
                                <div className="h-full max-h-[1000vh]">                                    
                                        <div className={(isDark?"hover:border-darkMain2 text-white ":"hover:border-lightMain2 ")+' flex px-2 mx-2 h-16 items-center rounded-lg border-2 border-solid'} >
                                            <label className="min-w-[70px]">일정명 : </label><input className="w-full bg-transparent mx-2 focus:outline-none" onChange={(e)=>setPlanName(e.target.value)} type="text" value={planName?planName:''} />
                                        </div>
                                        <div className={(isDark?'bg-darkMain5 ':'bg-lightMain3 ') + ' rounded-lg p-2 m-2 flex items-center'}>
                                            <DatePickerTest checkDate={null} defaultDate={makeStartDateFormat()} isDate={!isEditing}/>
                                            <div>-</div>
                                            
                                            <DatePicker 
                                                selected = {endDate}
                                                onChange = {(d:any) => {
                                                    // setEndDate(d)
                                                    checkEndDate(d)
                                                }}
                                                placeholderText={(isEditing&&endDate)&& String(endDate.getMonth()+1) + '월' + String(endDate.getDate()) + '일'}
                                                // value={props.defaultDate&&props.defaultDate}
                                                // placeholderText={endDate?.toLocaleDateString()}
                                                locale={ko}
                                                disabled={false}
                                                dateFormat="yyyy년 MM월 dd일"
                                                className={(isDark?"bg-darkMain5 " :"bg-lightMain3 ") +  "outline-none cursor-pointer text-[15px] w-full flex justify-center text-center"}
                                                />
                                        </div>

                                        
                                        <div className={(isDark&&"text-white ") +"text-sm px-4"}> 시작일 ~ 종료일을 선택하고 날짜별 코스를 선택하세요! </div>
                                        <div className={(isDark?"border-darkMain2 ":'border-lightMain2 ') +'rounded-lg p-2 border-2 border-solid m-2 min-h-[30vh] h-full max-h-[1000vh]'}>
                                            {(startDate && endDate)&&
                                                <div>
                                                    {scheduleDaysList&&
                                                            scheduleDaysList.map((d:{
                                                                name: string; day:string, contents:[]|[COURSE]}, key:number)=>{
                                                                    // console.log('scheduleDaysList', scheduleDaysList)
                                                                return(
                                                                    <div className="날짜별코스선택-토글박스 mb-2 rounded-lg flex flex-col">
                                                                        <div className="h-16 border-2 rounded-lg flex items-center justify-between">
                                                                            <div className={(isDark?"bg-darkMain3 text-white ":"bg-lightMain3 ") +"flex justify-center items-center px-8 h-full rounded-l-lg"}>{d.day.slice(4,6)}월 {d.day.slice(6)}일</div>
                                                                            <div className={(isDark? "text-darkMain " : "text-lightMain") + " font-bold"}>
                                                                                {d.contents.length !== 0?
                                                                                    d.contents.map((ee)=>{
                                                                                        return <div onClick={()=>{
                                                                                            (showCourse === key ? setShowCourse(undefined) : setShowCourse(key))
                                                                                        }
                                                                                    }>{ee.name}</div>
                                                                                    })
                                                                                :
                                                                                <div>{d.name}</div>
                                                                                }
                                                                            
                                                                            </div>
                                                                        <div onClick={()=>getWishCourse(d.day)} className={(isDark ? "bg-darkMain3 text-white " : "bg-lightMain3 ") + " cursor-pointer px-8 h-full flex items-center justify-center cursor-pointer"}>코스선택</div>
                                                                        </div>
                                                                            {
                                                                                (d.contents.length !== 0 && showCourse===key) &&
                                                                                <div className={(isDark?"bg-darkMain5 ":"lightMain5 ") +" rounded-lg py-2 mt-2"}>
                                                                                   
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
                     
                                        <div className="min-h-[20vh] cursor-pointer" onClick={() => submitSchedules()}>
                                            <ScheduleTitle scheduleTitle={isEditing ? "일정 수정하기" : "일정 만들기"}/>
                                        </div>
                                </div>
                                ///////////////////////////////////////////// 일정생성/수정 END /////////////////////////////////////////////
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
                <div className="w-full flex flex-col overflow-y-scroll scrollbar-hide">
                    {wishPlaces&& 
                        wishPlaces.map((course, key)=>{
                            if (alreadyInPlan(course.courseId)) {// wishPlacesd에 있는 것 중 이미 일정에 담긴 코스는 출력x
                                return
                            }
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

function ScheduleTitle(props:{scheduleTitle:any}) {
    const[isDark, x] = useRecoilState(darkMode)
    return(
        <div className={(isDark?"bg-darkMain4 ":"bg-lightMain2 ")+ "h-10 flex justify-center items-center font-bold text-white rounded-lg"}>{props.scheduleTitle}</div>
    )
}
const CourseComponent = (props:{places:any[]}) =>{
    return(
        <div className="px-2 w-full h-36 flex overflow-x-scroll scrollbar-hide">
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
