import axios from "axios"
import { useEffect, useRef, useState, useCallback, MouseEvent } from "react"
import { NavLink, useLocation } from "react-router-dom"
//alert
import Swal from "sweetalert2";

//mui switch
import Switch, { SwitchProps } from '@mui/material/Switch'
import { styled  as muiStyle } from '@mui/material/styles'

// 별점 이미지
import { FaStar } from 'react-icons/fa'
// styled component
import {Stars} from '../../components/review/StarStyledComponent'

export function ScheduleReview(){
    return (
        <div>
            일정 상세 리뷰
        </div>
    )
}

interface CATE_QA {
    [key : string] : string[], '음식' : string[], '카페' : string[], '문화' : string[],'쇼핑' : string[], '관광' : string[], '운동' : string[],'축제' : string[]
}
{/* 카테고리별 선택지 */}
const CATEGORY_QA:CATE_QA = {
    '음식' : ['음식이 맛있어요', '서비스가 좋아요', '가게만의 메뉴가 있어요', '가성비가 좋아요', '가게가 깨끗해요'],
    '카페' : ['메뉴가 다양해요', '커피가 맛있어요', '인테리어가 감각적이에요', '테라스가 있어요', '공간이 넓고 쾌적해요'],
    '문화' : ['이용이 편리해요', '서비스가 좋아요','편의시설이 잘 되어있어요', '컨텐츠가 유익해요',  '공간이 깔끔해요' ],
    '쇼핑' : ['매장이 다양해요', '서비스가 좋아요','편의시설이 잘 되어있어요', '가성비가 좋아요',  '공간이 깔끔해요' ],
    '관광' : ['볼거리가 다양해요', '안내가 잘 되어있어요','부대시설이 잘 되어있어요', '풍경맛집이에요',  '사람이 많아요'],
    '운동' : ['활동시설이 다양해요', '서비스가 친절해요','편의시설이 잘 되어있어요', '가성비가 좋아요',  '시설이 깔끔해요' ],
    '축제' : ['볼거리가 다양해요', '서비스가 좋아요','편의시설이 잘 되어있어요', '컨텐츠가 유익해요',  '공간이 깔끔해요' ]
}
{/* 공통 선택지 */}
const COMMON_QA = ['접근성이 좋아요', '분위기가 좋아요', '반려동물 동반이 가능해요', '주차 지원이 가능해요', '사진찍기 좋아요']

export function ReviewForm(props:{isReview:boolean, category:string, setShowReview:Function}){
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
    // 별점 관련 끝//////////////////////////////////  
    
    // Toast
    const Toast = Swal.mixin({
        toast:true,
        position: "bottom-end",
        showConfirmButton: true,
    })

    // 사진 등록
    const [uploadedImg, setUplodedImg] = useState<string[]>([])
    const handleChangeFile = (event: any) => {
        if (uploadedImg.length >= 3){
            Toast.fire({
                icon : "warning",
                title: "리뷰 사진은 최대 3장 등록가능합니다."
            })
            return
        }
        const formData = new FormData();
        const sizeLimit = 300 * 10000;
        // 300만 byte 넘으면 경고문구 출력
        if (event.target.files[0].size > sizeLimit) {
            Toast.fire({
                icon : "warning",
                title: "사진 크기가 3MB를 넘을 수 없습니다."
            })
            alert('사진 크기가 3MB를 넘을 수 없습니다.');
        } else {
            if (event.target.files[0]) {
                formData.append('file', event.target.files[0]); // 파일 상태 업데이트
            }
            // imgFile 을 보내서 S3에 저장된 url받기
            const getImgUrl = async () => {
                const API_URL = `https://j8e104.p.ssafy.io/api/file`;
                await axios
                .post(API_URL, formData, {
                    headers: { 'Content-Type': 'multipart/form-data' },
                })
                .then((con: { data: any }) => {
                    console.log('이미지주소불러오기 성공', con.data);
                    setUplodedImg([...uploadedImg, con.data])
                    // setImgUrlS3(con.data);
                })
                .catch((err: any) => {
                    console.log('이미지주소불러오기 실패', err);
                });
            };
            getImgUrl();
        }
    };

    // 리뷰 submit
    function submit(){
        // 리뷰 수정/ 등록
        console.log('cateQ', cateQ)
        console.log('commonQ', commonQ)
        console.log('content', content)
        console.log('score', score)
        console.log('contentOpen', contentOpen)
        console.log('photoOpen',photoOpen)
        console.log('image', uploadedImg)

        // 리뷰등록/수정 닫고 장소 상세페이지로 이동
        // props.setShowReview(false)
    }

    // 사진 삭제
    const deleteToast = Swal.mixin({
        toast : true,
        position : "bottom-end",
        showConfirmButton: true,

        showCancelButton: true,
        confirmButtonColor: "#FF8E9E", // confrim 버튼 색깔 지정
        cancelButtonColor: "#B9B9B9", // cancel 버튼 색깔 지정
        confirmButtonText: "삭제", // confirm 버튼 텍스트 지정
        cancelButtonText: "취소", // cancel 버튼 텍스트 지정

    })
    function deleteHandler(idx:number){
        deleteToast.fire({
            icon : "warning",
            title: "삭제하시겠습니까?"
        }).then((res) =>{
            if(res.isConfirmed){
                setUplodedImg(uploadedImg.filter((img,id) => id !== idx));
            }
        })
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
                <div className="flex flex-col items-center w-full border-2 border-solid border-lightMain rounded-lg">
                    {/* 텍스트 */}

                    <div className="flex w-full justify-between">
                        <div className="min-w-[80px] text-white"></div>
                        <p className="text-serveyCircle p-2">리뷰</p>
                        <div className="flex text-lightMain2 items-center">
                            <div onClick={()=>setContentOpen(!contentOpen)}><IOSSwitch sx={{ m: 1 }} defaultChecked /></div>
                            <p className="min-w-[40px]">{contentOpen?'공개':'비공개'}</p>
                        </div>
                    </div>
                    <div className="mx-2 h-20 w-full min-h-[50px]">
                        <textarea onChange={(e)=>setContent(e.target.value)} className="w-full h-full rounded-md focus:outline-none p-2 " placeholder="리뷰를 작성해주세요"/>
                    </div>
                </div>
                <div className="w-full bg-white rounded-lg mb-2 text-xs p-2 flex flex-col items-center">
                    {/* 사진 */}
                    <div className="flex w-full justify-between">
                        <div className="min-w-[80px] text-white"></div>
                        <p className="text-serveyCircle p-2">사진</p>
                        <div className="flex text-lightMain2 items-center">
                            <div onClick={()=>setPhotoOpen(!photoOpen)}><IOSSwitch sx={{ m: 1 }} defaultChecked /></div>
                            <p className="min-w-[40px]">{photoOpen?'공개':'비공개'}</p>
                        </div>
                    </div>
                    <div className="w-full h-40 bg-lightMain cursor-pointer rounded-md flex justify-left items-center p-2" onClick={uploadImgBtn}>
                        {uploadedImg &&
                            uploadedImg.map((image:string, idx:number)=>{
                                return(
                                    <div className="h-36 max-w-[33%] relative mx-[1px]">
                                        <img className="h-full w-full rounded-md z-0 " src={image} alt={image}></img>
                                        <div
                                            onClick={(e)=>{
                                                e.stopPropagation()
                                                deleteHandler(idx)
                                            }}
                                             className="text-xl absolute z-2 top-[0.5%] right-[1%] text-white hover:text-lightMain p-2 rounded-md">x</div>
                                        
                                    </div>
                                    )
                            })
                        }
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
export function ReviewSet(){
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