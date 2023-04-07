import { Star } from "@mui/icons-material";
import { Icon } from "@iconify/react";
import Swal from "sweetalert2";
import { getWishPlaceList, deleteWishPlace } from "../../apis/api/wish";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";
import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { darkMode } from "../../recoil/states/UserState";

/* eslint-disable jsx-a11y/alt-text */
interface Place {
    placeId: number;
    name: string;
    score: number;
    address: string;
    detail: string;
    thumbNailUrl: string;
}

export default function WishPlace() {
    const isDark = useRecoilState(darkMode)[0];
    const navigate = useNavigate();
    const userSeq = useRecoilState(userState);
    const [list, setList] = useState<Place[]>();
    const { data } = useQuery({
        queryKey: ["getWishPlaceList", "userSeq"],
        queryFn: () => getWishPlaceList(userSeq[0].seq),
    });

    useEffect(() => {
        setList(data);
    }, [data]);

    return (
        <div>
            <div className="mx-5 mt-5 h-full">
                <div className="title font-medium text-xl inline-block">
                    찜한 장소
                </div>
                <div className="cnt ml-2 font-bold text-red-600 text-xl inline-block">
                    {list?.length}
                </div>

                <div className="mt-4 h-[56vh] overflow-y-auto">
                    {list?.map((p: Place) => (
                        <Item
                            key={p.placeId}
                            item={p}
                            userSeq={userSeq[0].seq}
                        ></Item>
                    ))}
                </div>

                <div
                    className={
                        isDark
                            ? "cursor-pointer w-full h-12 bg-darkMain text-white font-bold text-center text-xl pt-2.5 mt-3 rounded-full"
                            : "cursor-pointer w-full h-12 bg-lightMain2 text-white font-bold text-center text-xl pt-2.5 mt-3 rounded-full"
                    }
                    onClick={() => {
                        navigate(`/wish/makeCourse`);
                    }}
                >
                    찜한 장소로 코스 만들기
                </div>
            </div>
        </div>
    );
}

function Item(props: { item: Place; userSeq: number }) {
    const isDark = useRecoilState(darkMode)[0];

    const Toast = Swal.mixin({
        toast: true, // 토스트 형식
        position: "bottom-end", // 알림 위치
        showConfirmButton: false, // 확인버튼 생성 유무
        timer: 1000, // 지속 시간
        timerProgressBar: true, // 지속시간바 생성 여부
    });

    const queryClient = useQueryClient();
    const mutation = useMutation(deleteWishPlace, {
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

    let detail =
        props.item.detail.length > 45
            ? props.item.detail.slice(0, 45).concat("...")
            : props.item.detail;

    return (
        <div
            className={
                isDark
                    ? "col-md-4 mb-4 p-3 rounded-lg bg-darkBackground2"
                    : "col-md-4 mb-4 p-3 rounded-lg bg-calendarGray"
            }
        >
            <div className="cursor-pointer heart mb-2 float-right">
                <Icon
                    icon="mdi:cards-heart"
                    color={isDark ? "#BE6DB7" : "#FF8E9E"}
                    width="28"
                    height="28"
                    onClick={() => {
                        Swal.fire({
                            text: `${props.item.name}을(를) 찜 해제하시겠습니까?`,
                            icon: "question",

                            showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                            confirmButtonColor: isDark ? "#BE6DB7" : "#FF8E9E", // confrim 버튼 색깔 지정
                            cancelButtonColor: "#B9B9B9", // cancel 버튼 색깔 지정
                            confirmButtonText: "승인", // confirm 버튼 텍스트 지정
                            cancelButtonText: "취소", // cancel 버튼 텍스트 지정
                        }).then((result) => {
                            // 만약 Promise리턴을 받으면,
                            if (result.isConfirmed) {
                                // 만약 모달창에서 confirm 버튼을 눌렀다면
                                // 해당 장소 찜 목록에서 삭제

                                console.log(props.item.placeId);
                                mutation.mutate({
                                    placeId: props.item.placeId,
                                    userSeq: props.userSeq,
                                });

                                const Toast = Swal.mixin({
                                    toast: true, // 토스트 형식
                                    position: "bottom-end", // 알림 위치
                                    showConfirmButton: false, // 확인버튼 생성 유무
                                    timer: 1000, // 지속 시간
                                    timerProgressBar: true, // 지속시간바 생성 여부
                                });

                                Toast.fire({
                                    icon: "success",
                                    title: "삭제되었습니다. ",
                                });
                            }
                        });
                    }}
                />
            </div>

            <div className="font-bold mb-2">{props.item.name}</div>

            <img
                className="float-left w-24 h-[100px] mr-2 rounded-md"
                src={props.item.thumbNailUrl}
            />

            <div className="star ml-2 mb-1">
                <Star
                    fontSize="small"
                    sx={{ color: isDark ? "#BE6DB7" : "#FF8E9E" }}
                />
                <p
                    className={
                        isDark
                            ? "inline-block text-sm ml-1 text-darkMain"
                            : "inline-block text-sm ml-1 text-lightMain"
                    }
                >
                    {props.item.score}
                </p>
            </div>
            <div
                className={
                    isDark
                        ? "mb-2 text-sm text-slate-300"
                        : "mb-2 text-sm text-gray-500"
                }
            >
                {props.item.address}
            </div>

            {props.item.detail === "" ? (
                <div
                    className="h-12
                "
                ></div>
            ) : (
                <div className="text-sm py-3">{detail}</div>
            )}
        </div>
    );
}
