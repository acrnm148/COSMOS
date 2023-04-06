import { useEffect, useState } from "react";
import TMapResult from "../common/TMapResult";
import TMapWish from "../common/TMapWish";
import ListCard from "../common/ListCard";
import { Icon } from "@iconify/react";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { darkMode, userState } from "../../recoil/states/UserState";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { deleteWishCourse, getCourseDetail } from "../../apis/api/wish";
import Swal from "sweetalert2";

interface Place {
    placeId: number;
    name: string;
    score: number;
    address: string;
    detail: string;
    thumbNailUrl: string;
    phoneNumber: string;
    orders: number; // 코스 순서
    latitude: string; // 위도
    longitude: string; // 경도
}

// 기존 윈도우에 없는 객체에 접근할 때 에러 발생
// 임의로 값이 있다고 정의해주는 부분
// const Kakao = (window as any).Kakao;
declare const window: typeof globalThis & {
    Kakao: any;
};

export default function CourseDetail(props: { courseId: any }) {
    const isDark = useRecoilState(darkMode)[0];
    const navigate = useNavigate();
    const [places, setPlaces] = useState<Place[]>([]);
    const [userSeq, setUserSeq] = useRecoilState(userState);

    // api
    const { data } = useQuery({
        queryKey: ["getCourseDetail", "courseId"],
        queryFn: () =>
            getCourseDetail(props.courseId, userSeq.seq, userSeq.acToken),
    });
    const queryClient = useQueryClient();
    const mutation = useMutation(deleteWishCourse, {
        onSuccess: (data) => {
            Toast.fire({
                icon: "success",
                title: "삭제되었습니다. ",
            });

            queryClient.invalidateQueries();
        },
        onError: () => {
            Toast.fire({
                icon: "error",
                title: "삭제 실패했습니다. ",
            });
        },
    });
    const Toast = Swal.mixin({
        toast: true, // 토스트 형식
        position: "bottom-end", // 알림 위치
        showConfirmButton: false, // 확인버튼 생성 유무
        timer: 1000, // 지속 시간
        timerProgressBar: true, // 지속시간바 생성 여부
    });

    useEffect(() => {
        setPlaces(data?.places);
    }, [data]);

    // 카카오톡 공유하기
    useEffect(() => {
        // 카카오 sdk 찾도록 초기화
        if (!window.Kakao.isInitialized()) {
            window.Kakao.init(process.env.REACT_APP_KAKAO_SHARE_JS_MYE);
        }
    }, []);

    const shareKakao = () => {
        window.Kakao.Link.sendDefault({
            objectType: "feed",
            content: {
                title: "나랑 데이트가자!",

                description: `이번 여행 '${data.name}' 코스 어때?`,
                imageUrl: places[0].thumbNailUrl,
                link: {
                    webUrl: `https://j8e104.p.ssafy.io/`,
                    mobileWebUrl: "https://j8e104.p.ssafy.io/",
                },
            },
            buttons: [
                {
                    title: "코스 보러가기",
                    link: {
                        webUrl: `https://j8e104.p.ssafy.io/wish/course/${data.courseId}/detail/`,
                        mobileWebUrl: `https://j8e104.p.ssafy.io/wish/course/${data.courseId}/detail/`,
                    },
                },
            ],
        });
    };

    return (
        <div>
            <div className="text-center w-full max-w-[950px]">
                <TMapWish courseId={props.courseId} />

                <div className="">
                    <ListCard height={false}>
                        <div className="mb-5 text-lg font-bold text-left pb-3 mx-5 border-b border-slate-400">
                            {data?.name}
                        </div>

                        <div className="cardList">
                            {places?.map((p: Place) => (
                                <Item key={p.placeId} item={p}></Item>
                            ))}
                        </div>
                    </ListCard>
                </div>
            </div>

            <div
                className={
                    isDark
                        ? "cursor-pointer btns w-full h-12 bg-darkMain3 text-center flex fixed bottom-20 z-[100001]"
                        : "cursor-pointer btns w-full h-12 bg-lightMain3 text-center flex fixed bottom-20 z-[100001]"
                }
            >
                <div className="float-left w-1/3 m-auto" onClick={shareKakao}>
                    <Icon
                        icon="material-symbols:share-outline"
                        width="20"
                        height="20"
                        className="inline-block mr-2 mb-1 "
                    />
                    공유
                </div>
                <div
                    className="float-left w-1/3 m-auto"
                    onClick={() => {
                        navigate(`/wish/course/${props.courseId}/edit`);
                    }}
                >
                    <Icon
                        icon="material-symbols:edit-outline-rounded"
                        width="22"
                        height="22"
                        className="inline-block mr-2 mb-1"
                    />
                    편집
                </div>
                <div
                    className="float-left w-1/3 m-auto"
                    onClick={() => {
                        Swal.fire({
                            // title: `${props.item.title}을 찜 해제하시겠습니까?`,
                            text: `${data?.name} 코스를 삭제하시겠습니까?`,
                            icon: "question",

                            showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                            confirmButtonColor: isDark ? "#BE6DB7" : "#FF8E9E", // confrim 버튼 색깔 지정
                            cancelButtonColor: "#B9B9B9", // cancel 버튼 색깔 지정
                            confirmButtonText: "확인", // confirm 버튼 텍스트 지정
                            cancelButtonText: "취소", // cancel 버튼 텍스트 지정
                        }).then((result) => {
                            // 만약 Promise리턴을 받으면,
                            if (result.isConfirmed) {
                                // 만약 모달창에서 confirm 버튼을 눌렀다면
                                // 해당 장소 찜 목록에서 삭제

                                mutation.mutate(Number(props.courseId));
                            }
                        });
                    }}
                >
                    <Icon
                        icon="material-symbols:delete-forever-outline"
                        width="22"
                        height="22"
                        className="inline-block mr-2 mb-1"
                    />
                    삭제
                </div>
            </div>
        </div>
    );
}

function Item(props: { item: Place }) {
    const isDark = useRecoilState(darkMode)[0];

    return (
        <div>
            <div
                className={
                    isDark
                        ? "idx absolute left-3 pt-1 bg-darkMain text-white w-10 h-10 rounded-full text-xl font-bold"
                        : "idx absolute left-3 pt-1 bg-lightMain text-white w-10 h-10 rounded-full text-xl font-bold"
                }
            >
                {props.item.orders}
            </div>
            <div
                className={
                    isDark
                        ? "col-md-4 mb-4 ml-4 mr-4 p-3 bg-darkBackground2 rounded-lg"
                        : "col-md-4 mb-4 ml-4 mr-4 p-3 bg-calendarGray rounded-lg"
                }
            >
                <img
                    className="float-left w-24 h-[100px] mr-4 mt-1 rounded-md"
                    src={props.item.thumbNailUrl}
                    alt="img"
                />
                <div className="text-left mt-2 mb-2">
                    <div className="font-bold mb-2">{props.item.name}</div>
                    <div
                        className={
                            isDark
                                ? "mb-2 text-sm text-slate-300"
                                : "mb-2 text-sm text-gray-500"
                        }
                    >
                        {props.item.address}
                    </div>
                    <div className="text-sm">{props.item.detail}</div>
                    {!props.item.detail && <div className="h-9"></div>}
                </div>
            </div>
        </div>
    );
}
