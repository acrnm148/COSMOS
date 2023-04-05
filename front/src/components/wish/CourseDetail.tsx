import { useEffect, useState } from "react";
import TMapResult from "../common/TMapResult";
import TMapWish from "../common/TMapWish";
import ListCard from "../common/ListCard";
import { Icon } from "@iconify/react";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";
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

export default function CourseDetail(props: { courseId: any }) {
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

            <div className="btns w-full h-12 bg-lightMain3 text-center flex fixed bottom-20 z-[100001]">
                <div className="float-left w-1/3 m-auto">
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
                            confirmButtonColor: "#FF8E9E", // confrim 버튼 색깔 지정
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
    let address =
        props.item.address.length > 15
            ? props.item.address.slice(0, 15).concat("...")
            : props.item.address;

    let detail =
        props.item.detail.length > 33
            ? props.item.detail.slice(0, 33).concat("...")
            : props.item.detail;

    return (
        <div>
            <div className="idx absolute left-3 pt-1 bg-lightMain text-white w-10 h-10 rounded-full text-xl font-bold">
                {props.item.orders}
            </div>
            <div className="col-md-4 mb-4 ml-4 mr-4 p-3 bg-calendarGray rounded-lg">
                <img
                    className="float-left w-24 h-[100px] mr-4 mt-1 rounded-md"
                    src={props.item.thumbNailUrl}
                    alt="img"
                />
                <div className="text-left mt-2 mb-2">
                    <div className="font-bold mb-2">{props.item.name}</div>
                    <div className="mb-2 text-sm text-gray-500">{address}</div>
                    <div className="text-sm">{detail}</div>
                    {!detail && <div className="h-9"></div>}
                </div>
            </div>
        </div>
    );
}
