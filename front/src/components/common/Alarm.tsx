import { Icon } from "@iconify/react";
import { Badge } from "@mui/material";
import React, { useEffect, useState } from "react";
import { useQueries, useQueryClient, useMutation } from "react-query";
import {
    getAlarmList,
    getUnReadAlarm,
    deleteAlarm,
    deleteAll,
} from "../../apis/api/alarm";
import { useRecoilState } from "recoil";
import { darkMode, userState } from "../../recoil/states/UserState";
import { EventSourcePolyfill } from "event-source-polyfill";
import Swal from "sweetalert2";

export default function Alarm() {
    const isDark = useRecoilState(darkMode)[0];
    const [userSeq, setUserSeq] = useRecoilState(userState);
    let subscribeUrl =
        process.env.REACT_APP_API_URL + `/noti/subscribe/${userSeq.seq}`;
    const [click, setClick] = useState(false);
    const [isShow, setShow] = useState(false);
    const [clickModal, setClickModal] = useState(false);

    // 모달 닫기
    const el = React.useRef() as React.MutableRefObject<HTMLDivElement>;
    const handleCloseModal = (e: any) => {
        e.stopPropagation();

        if (!el.current || !el.current.contains(e.target)) {
            setClickModal(false);
        } else {
            setClickModal(true);
        }
    };

    useEffect(() => {
        window.addEventListener("click", handleCloseModal);
        return () => {
            window.removeEventListener("click", handleCloseModal);
        };
    }, []);

    useEffect(() => {
        if (!clickModal && isShow) {
            setShow(false);
        }
    }, [clickModal]);

    // sse : eventSource
    useEffect(() => {
        let eventSource = new EventSourcePolyfill(subscribeUrl, {
            headers: {
                "Content-Type": "text/event-stream",
                "Access-Control-Allow-Origin": "",
                AccessToken: `Bearer ${userSeq.acToken}`,
                "Cache-Control": "no-cache",
            },
            heartbeatTimeout: 100000,
            withCredentials: true,
        });
        eventSource.onerror = function () {
            eventSource.close();
            eventSource = new EventSourcePolyfill(subscribeUrl, {
                headers: {
                    "Content-Type": "text/event-stream",
                    "Access-Control-Allow-Origin": "",
                    AccessToken: `Bearer ${userSeq.acToken}`,
                    "Cache-Control": "no-cache",
                },
                heartbeatTimeout: 100000,
                withCredentials: true,
            });
            eventSource.addEventListener("connect", function (e: any) {
                // console.log("connect: " + e.data);
            });
            // 커플 연결 매칭
            eventSource.addEventListener("makeCouple", function (e: any) {
                console.log(e.data);
            });
            // 커플 연결 끊김
            eventSource.addEventListener("removeCouple", function (e: any) {
                console.log(e.data);
            });
            // 일정 등록 알림
            eventSource.addEventListener("makePlan", function (e: any) {
                console.log(e.data);
            });
            // 찜하기 알림 (상대방이 찜한 경우 알림)
            eventSource.addEventListener("makeWish", function (e: any) {
                console.log(e.data);
            });
            // 상대방 리뷰 등록 알림
            eventSource.addEventListener("makeReview", function (e: any) {
                console.log(e.data);
            });
            // 리뷰 요청 알림 (일정 종료 후)
            eventSource.addEventListener("requestReview", function (e: any) {
                console.log(e.data);
            });
            eventSource.addEventListener("error", function (e: any) {
                // console.log("error");
            });
        };

        eventSource.addEventListener("connect", function (e: any) {
            // console.log("connect: " + e.data);
        });
        // 커플 연결 매칭
        eventSource.addEventListener("makeCouple", function (e: any) {
            console.log(e.data);
        });
        // 커플 연결 끊김
        eventSource.addEventListener("removeCouple", function (e: any) {
            console.log(e.data);
        });
        // 일정 등록 알림
        eventSource.addEventListener("makePlan", function (e: any) {
            console.log(e.data);
        });
        // 찜하기 알림 (상대방이 찜한 경우 알림)
        eventSource.addEventListener("makeWish", function (e: any) {
            console.log(e.data);
        });
        // 상대방 리뷰 등록 알림
        eventSource.addEventListener("makeReview", function (e: any) {
            console.log(e.data);
        });
        // 리뷰 요청 알림 (일정 종료 후)
        eventSource.addEventListener("requestReview", function (e: any) {
            console.log(e.data);
        });
        eventSource.addEventListener("error", function (e: any) {
            // console.log("error");
        });

        return () => {
            eventSource.close();
        };
    }, []);

    // api
    const queryClient = useQueryClient();
    const res = useQueries([
        {
            // list
            queryKey: ["getAlarmList", userSeq, click],
            queryFn: () =>
                getAlarmList({ userSeq: userSeq.seq, clicked: click ? 1 : 0 }),
        },
        {
            // 안 읽은 알림 개수
            queryKey: ["getUnReadAlarm", userSeq, click],
            queryFn: () => getUnReadAlarm(userSeq.seq),
        },
    ]);

    queryClient.setQueryData(`getClick`, click); //데이터 수정시 사용
    queryClient.invalidateQueries(`getClick`);

    const mutation = useMutation(deleteAlarm, {
        onSuccess: (data) => {
            queryClient.invalidateQueries();
        },
        onError: () => {
            console.log("삭제 실패");
        },
    });
    const delAllMutation = useMutation(deleteAll, {
        onSuccess: (data) => {
            queryClient.invalidateQueries();
        },
        onError: () => {
            console.log("삭제 실패");
        },
    });

    const badgeStyle = {
        "& .MuiBadge-badge": {
            color: "black",
            backgroundColor: isDark ? "#E4C3E1" : "#FF8E9E",
            borderRadius: "50%",
            fontSize: "17px",
            width: "24px",
            height: "24px",
        },
    };

    return (
        <div
            // ref={el}
            className="alertIcon absolute right-8 top-1/2 -translate-y-1/2 cursor-pointer"
        >
            <Badge
                badgeContent={res[1].data}
                sx={badgeStyle}
                overlap="circular"
                onClick={(e) => {
                    setShow(!isShow);
                    setClickModal(true);
                    e.stopPropagation();
                }}
            >
                <Icon
                    icon="heroicons-solid:bell"
                    color="white"
                    width="35"
                    height="40"
                />
            </Badge>

            {isShow && (
                <div
                    ref={el}
                    className="p-5 absolute w-[340px] h-[550px] bg-white right-0 top-11 rounded-lg border border-calendarDark overflow-y-auto"
                >
                    <div className="text-xl border-b border-gray-500 pb-3">
                        알림
                        {res[0].data?.length > 0 && (
                            <div
                                className="float-right text-base font-"
                                onClick={() =>
                                    Swal.fire({
                                        // title: `${props.item.title}을 찜 해제하시겠습니까?`,
                                        text: `알림을 전체 삭제합니다. `,
                                        icon: "question",

                                        showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
                                        confirmButtonColor: isDark
                                            ? "#BE6DB7"
                                            : "#FF8E9E", // confrim 버튼 색깔 지정
                                        cancelButtonColor: "#B9B9B9", // cancel 버튼 색깔 지정
                                        confirmButtonText: "확인", // confirm 버튼 텍스트 지정
                                        cancelButtonText: "취소", // cancel 버튼 텍스트 지정
                                    }).then((result) => {
                                        // 만약 Promise리턴을 받으면,
                                        if (result.isConfirmed) {
                                            // 만약 모달창에서 confirm 버튼을 눌렀다면
                                            // 해당 장소 찜 목록에서 삭제

                                            // 전체 삭제
                                            delAllMutation.mutate({
                                                userSeq: userSeq.seq,
                                                ac: userSeq.acToken,
                                            });
                                        }
                                    })
                                }
                            >
                                전체 삭제
                            </div>
                        )}
                    </div>

                    {res[0].data?.length === 0 ? (
                        <div className="text-lg text-center mt-44">
                            알림이 없습니다.{" "}
                        </div>
                    ) : (
                        <div>
                            {res[0].data?.map((item: any) => (
                                <div
                                    key={item.id}
                                    className={
                                        item.isRead
                                            ? "py-4 px-2 text-base font-normal border-b border-gray-300 text-slate-400"
                                            : "py-4 px-2 text-base font-normal border-b border-gray-300"
                                    }
                                >
                                    {item.content}

                                    <div
                                        className="float-right"
                                        onClick={() => {
                                            // 삭제
                                            mutation.mutate({
                                                notiId: Number(item.id),
                                                userSeq: userSeq.seq,
                                                ac: userSeq.acToken,
                                            });
                                        }}
                                    >
                                        ✕
                                    </div>
                                </div>
                            ))}
                        </div>
                    )}
                </div>
            )}
        </div>
    );
}
