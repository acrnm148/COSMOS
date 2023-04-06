import { Icon } from "@iconify/react";
import { Badge } from "@mui/material";
import React, { useEffect, useState, useRef, useReducer } from "react";
import { useQuery, useQueries, useQueryClient, useMutation } from "react-query";
import {
    getAlarmList,
    getUnReadAlarm,
    deleteAlarm,
} from "../../apis/api/alarm";
import { useRecoilState } from "recoil";
import { darkMode, userState } from "../../recoil/states/UserState";
import { EventSourcePolyfill } from "event-source-polyfill";

export default function Alarm() {
    const isDark = useRecoilState(darkMode)[0];
    const [userSeq, setUserSeq] = useRecoilState(userState);
    let subscribeUrl =
        process.env.REACT_APP_API_URL + `/noti/subscribe/${userSeq.seq}`;
    const [message, setMessage] = useState();
    const [click, setClick] = useState(false);
    const [isShow, setShow] = useState(false);
    const [any, forceUpdate] = useReducer((num) => num + 1, 0);
    function handleChange() {
        forceUpdate();
    }

    // 모달 닫기
    const el = React.useRef() as React.MutableRefObject<HTMLDivElement>;
    const handleCloseModal = (e: any) => {
        if (isShow && (!el.current || !el.current.contains(e.target)))
            setShow(false);
    };
    useEffect(() => {
        window.addEventListener("click", handleCloseModal);
        return () => {
            window.removeEventListener("click", handleCloseModal);
        };
    }, []);

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

    useEffect(() => {
        // console.log(click);
    }, [click]);

    // api
    const queryClient = useQueryClient();
    const res = useQueries([
        {
            // list
            queryKey: ["getAlarmList", "userSeq", "click"],
            queryFn: () =>
                getAlarmList({ userSeq: userSeq.seq, clicked: click ? 1 : 0 }),
        },
        {
            // 안 읽은 알림 개수
            queryKey: ["getUnReadAlarm", "userSeq"],
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
            ref={el}
            className="alertIcon absolute right-8 top-1/2 -translate-y-1/2 cursor-pointer"
        >
            <Badge
                badgeContent={res[1].data}
                sx={badgeStyle}
                overlap="circular"
                onClick={() => {
                    !isShow && setClick((click) => false);
                    isShow && setClick((click) => true);
                    isShow && handleChange();
                    setShow(!isShow);
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
                <div className="p-5 absolute w-[340px] h-[550px] bg-white right-0 top-11 rounded-lg border border-calendarDark overflow-y-auto">
                    <div className="text-xl border-b border-gray-500 pb-3">
                        알림
                        <div className="float-right text-base font-">
                            전체 삭제
                        </div>
                    </div>

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
    );
}
