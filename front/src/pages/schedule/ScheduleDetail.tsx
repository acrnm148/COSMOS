import axios from "axios"
import { useEffect, useRef, useState, useCallback } from "react"
import { NavLink, useLocation } from "react-router-dom"
import  "../../css/detailSchedule.css"

//mui switch
import Switch, { SwitchProps } from '@mui/material/Switch'
import { styled  as muiStyle } from '@mui/material/styles'

// 별점 이미지
import { FaStar } from 'react-icons/fa'
// styled component
import styled from 'styled-components'

interface CATE_QA {
    [key : string] : string[], '음식' : string[], '카페' : string[], '문화' : string[],'쇼핑' : string[], '관광' : string[], '운동' : string[],'축제' : string[]
}
{/* 카테고리별 선택지 */}
const CATEGORY_QA:CATE_QA = {
    '음식' : ['음식이 맛있어요', '서비스가 좋아요', '사람이 많고 웨이팅이 있어요', '가성비가 좋아요', '가게가 깨끗해요'],
    '카페' : [],
    '문화' : [],
    '쇼핑' : [],
    '관광' : [],
    '운동' : [],
    '축제' : []
}
{/* 공통 선택지 */}
const COMMON_QA = ['접근성이 좋아요', '분위기가 좋아요', '반려동물 동반이 가능해요', '주차 지원이 가능해요', '사진찍기 좋아요']

interface REVIEW {
    cateQ : string | undefined
    commonQ : string | undefined
    contentOpen : boolean | undefined
    content : string | undefined
    photoOpen : boolean | undefined
    photos : string[] | undefined
}
export function ScheduleDetail(){
    const location = useLocation()
    const { state } = location
    const placeId = state.placeId
    const place = state.place

    const [isReview, setIsReview] = useState<boolean>(false)
    const [showReview, setShowReview] = useState<boolean>(false)

    useEffect(()=>{
    })
    

    return (
        <div className='bg-lightMain2 mb-5'>
            {!showReview &&
            <div className="h-20 flex justify-center items-center">{state.scheduleTitle}</div>
            }
            <div className={"bg-white rounded-t-lg w-full flex flex-col items-between" + (!showReview ? 'p-2':'w-full')}>
                <div>
                    <div className="flex flex-col justify-center items-center">
                        <p className="text-md text-calendarDark">{place.date}</p>
                        <p className="m-1 text-xl">{place.name}</p>
                    </div>
                    {
                        !showReview&&
                        <div>
                            <div className="detailImg h-full w-full bg-calendarGray rounded-lg"> <img className="w-full h-full" src={place.imgUrl} alt="" /></div>
                            <div className="flex justify-end text-sm mb-5">
                                <div>{place.location}</div>
                                <div className="ml-1 mr-1 text-calendarDark">|</div>
                                <div>{place.category}</div>
                            </div>
                        </div>
                    }
                </div>
                <div className="bg-lightMain4 w-full h-full rounded-lg">
                    {showReview?
                        <div className=" w-full h-full flex flex-col justify-end items-center p-2">
                            <ReviewForm isReview={isReview} category={place.category} setShowReview={setShowReview}/>
                        </div>
                        
                    :isReview?
                            <div>리뷰가 있습니다</div>
                        :
                            <div className="h-40 w-full  w-full flex flex-col justify-end items-center p-4">
                                <p className="text-sm mb-2">일정을 마치셨나요?</p>
                                <div onClick={()=>setShowReview(true)} className="w-full"><ReviewSet /></div>
                            </div>
                        }
                </div>
            </div>
        </div>
    )
}

function ReviewForm(props:{isReview:boolean, category:string, setShowReview:Function}){
    const inputRef = useRef<HTMLInputElement | null>(null);
    const uploadImgBtn = useCallback(() =>{
        inputRef.current?.click()
    },[])

    // 리뷰 정보
    const [cateQ, setCateQ] = useState<string | undefined>()
    const [commonQ, setCommonQ] = useState<string | undefined>()
    const [contentOpen, setContentOpen] = useState< boolean>(true)
    const [content, setContent] =  useState<string | undefined>()
    const [photoOpen, setPhotoOpen] = useState<boolean>(true)
    const [photos, setPhotos] = useState<string[] | undefined>()
    const [score, setScore] = useState<number>()

    const [clicked, setClicked] = useState([false, false, false, false, false])

    // 별점 관련
    useEffect(()=>{
        sendReview()
    },[clicked])

    const sendReview = () => {
        let scr = clicked.filter(Boolean).length
        setScore(scr)
    }

    const handleStarClick = (index:any) => {
        let clickStates = [...clicked]
        for (let i = 0; i < 5; i++) {
            clickStates[i] = i <= index ? true : false
        }
        setClicked(clickStates)
      }
      const Stars = styled.div`
      display: flex;
      padding-top: 5px;
      
      & svg {
        color: gray;
        cursor: pointer;
      }
      
      :hover svg {
        color: #fcc419;
      }
      
      & svg:hover ~ svg {
        color: gray;
      }
      
      .yellowStar {
        color: #fcc419;
      }`
    // 별점 관련 끝//////////////////////////////////  
    
    // 사진 등록
    const handleChangeFile = (event: any) => {
    const formData = new FormData();
    const sizeLimit = 300 * 10000;
    // 300만 byte 넘으면 경고문구 출력
    if (event.target.files[0].size > sizeLimit) {
    alert('사진 크기가 3MB를 넘을 수 없습니다.');
    } else {
    if (event.target.files[0]) {
        formData.append('file', event.target.files[0]); // 파일 상태 업데이트
    }
    // imgFile 을 보내서 S3에 저장된 url받기
    // const getImgUrl = async () => {
    //     const API_URL = `https://i8e208.p.ssafy.io/api/files/upload/`;
    //     await axios
    //     .post(API_URL, formData, {
    //         headers: { 'Content-Type': 'multipart/form-data' },
    //     })
    //     .then((con: { data: any }) => {
    //         console.log('이미지주소불러오기 성공', con.data);
    //         // setImgUrlS3(con.data);
    //     })
    //     .catch((err: any) => {
    //         console.log('이미지주소불러오기 실패', err);
    //     });
    // };
    // getImgUrl();
    }
    };

    // 리뷰 submit
    function submit(){
        // 리뷰 수정/ 등록
        console.log('cateQ', cateQ)
        console.log('commonQ', commonQ)
        console.log('content', content)
        console.log('score', score)
        // console.log('contentOpen', contentOpen)

        // 리뷰등록/수정 닫고 장소 상세페이지로 이동
        props.setShowReview(false)
    }
    return(
        <div className="w-full">
            {/* 카테고리별 선택지 */}
            <div className="mb-5 w-full grid grid-col-2">
                {
                    CATEGORY_QA[props.category as keyof CATE_QA].map((ask:string, i:number)=>{
                        if (i === 2){
                            return(<div onClick={() => setCateQ(ask)}  className={`cursor-pointer col-span-2 rounded-lg flex justify-center items-center text-xs h-8 mx-1 my-0.5 mw-s"
                                            ${cateQ === ask ? "bg-lightMain font-white" : "bg-white"}`}
                            >{ask}</div>)
                            
                        } else{
                            return(
                                <div onClick={() => setCateQ(ask)} className={`cursor-pointer rounded-lg flex justify-center items-center text-xs h-8 mx-1 my-0.5 mw-s"
                                             ${cateQ === ask ? "bg-lightMain font-white" : "bg-white"}`}
                            >{ask}</div>
                            )
                        }

                    })
                }
            </div>
            {/* 공통 선택지 */}
            <div className="mb-5 w-full grid grid-col-2">
                {
                    COMMON_QA.map((ask, i)=>{
                        if (i === 2){
                            return(<div onClick={() => setCommonQ(ask)} className={`cursor-pointer col-span-2 rounded-lg flex justify-center items-center text-xs h-8 mx-1 my-0.5 mw-s"
                                            ${commonQ === ask ? "bg-lightMain font-white" : "bg-white"}`} 
                            >{ask}</div>)
                            
                        } else{
                            return(
                                <div onClick={() => setCommonQ(ask)} className={`cursor-pointer rounded-lg flex justify-center items-center text-xs h-8 mx-1 my-0.5 mw-s"
                                            ${commonQ === ask ? "bg-lightMain font-white" : "bg-white"}`}
                                >{ask}</div>
                            )
                        }

                    })
                }
            </div>
            
            {/* 리뷰 */}
            <div className="w-full bg-white rounded-lg mb-5 text-xs p-2 flex flex-col items-center">
                <div className="flex flex-col items-center w-full border-2 border-solid border-lightMain">
                    {/* 텍스트 */}

                    <div className="flex w-full justify-between">
                        <div className="text-white"> 공간공간공간</div>
                        <p className="text-serveyCircle">리뷰</p>
                        <div className="flex text-lightMain2 items-center">
                            <div onClick={()=>setContentOpen(!contentOpen)}><IOSSwitch sx={{ m: 1 }} defaultChecked /></div>
                            <p>공개</p>
                        </div>
                    </div>
                    <div className="mx-2 h-20 w-full min-h-[50px]">
                        <input onChange={(e)=>setContent(e.target.value)} className="w-full h-full rounded-md " type="text" placeholder="리뷰를 작성해주세요"/>
                    </div>
                </div>
                <div className="w-full bg-white rounded-lg mb-2 text-xs p-2 flex flex-col items-center">
                    {/* 사진 */}
                    <div className="flex w-full justify-between">
                        <div className="text-white"> 공간공간공간</div>
                        <p>사진</p>
                        <div className="flex text-lightMain2 items-center">
                            <div onClick={()=>setContentOpen(!contentOpen)}><IOSSwitch sx={{ m: 1 }} defaultChecked /></div>
                            <p>공개</p>
                        </div>
                    </div>
                    <div className="w-full h-40 bg-lightMain cursor-pointer rounded-md" onClick={uploadImgBtn}>
                        여길 누르면 파일 업로드가 될거임
                    </div>
                        <input
                        className="w-full h-full"
                            type="file"
                            name="imgFile"
                            accept="image/*"
                            ref={inputRef}
                            id="imgFile"
                            onChange={handleChangeFile}
                            style={{ display: 'none' }}
                        />
                </div>
            </div>
            {/* 별점 */}
                <div className="flex w-full justify-center mb-4">
                    <Stars>
                        {[0,1,2,3,4].map((el, idx) => {
                            return (
                                <FaStar
                                key={idx}
                                size="50"
                                onClick={() => handleStarClick(el)}
                                className={clicked[el] ? 'yellowStar':''}
                                />
                            )
                            })}    
                    </Stars>
                </div>  
            <div onClick={submit} className="w-full"><ReviewSet /></div>         
        </div>
    )
}
function ReviewSet(){
    return(
        <div className="cursor-pointer bg-white w-full h-10 text-sm rounded-lg text-lightMain flex justify-center items-center">
            리뷰남기기
        </div>
    )
}
const IOSSwitch = muiStyle((props: SwitchProps) => (
    <Switch focusVisibleClassName=".Mui-focusVisible" disableRipple {...props} />
  ))(({ theme }) => ({
    width: 42,
    height: 26,
    padding: 0,
    '& .MuiSwitch-switchBase': {
      padding: 0,
      margin: 2,
      transitionDuration: '300ms',
      '&.Mui-checked': {
        transform: 'translateX(16px)',
        color: '#fff',
        '& + .MuiSwitch-track': {
          backgroundColor: theme.palette.mode === 'dark' ? '#FF8E9E' : '#FF8E9E',
          opacity: 1,
          border: 0,
        },
        '&.Mui-disabled + .MuiSwitch-track': {
          opacity: 0.5,
        },
      },
      '&.Mui-focusVisible .MuiSwitch-thumb': {
        color: '#FF8E9E',
        border: '6px solid #fff',
      },
      '&.Mui-disabled .MuiSwitch-thumb': {
        color:
          theme.palette.mode === 'light'
            ? theme.palette.grey[100]
            : theme.palette.grey[600],
      },
      '&.Mui-disabled + .MuiSwitch-track': {
        opacity: theme.palette.mode === 'light' ? 0.7 : 0.3,
      },
    },
    '& .MuiSwitch-thumb': {
      boxSizing: 'border-box',
      width: 22,
      height: 22,
    },
    '& .MuiSwitch-track': {
      borderRadius: 26 / 2,
      backgroundColor: theme.palette.mode === 'light' ? '#E9E9EA' : '#39393D',
      opacity: 1,
      transition: theme.transitions.create(['background-color'], {
        duration: 500,
      }),
    },
  }));