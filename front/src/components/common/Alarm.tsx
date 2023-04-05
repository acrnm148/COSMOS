import { Icon } from "@iconify/react";
import { Badge } from "@mui/material";
import { useEffect, useState } from "react";
import { useQuery, useQueries, useQueryClient, useMutation } from "react-query";
import {
    getAlarmList,
    getUnReadAlarm,
    deleteAlarm,
} from "../../apis/api/alarm";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";

export default function Alarm() {
    const [userSeq, setUserSeq] = useRecoilState(userState);
    let subscribeUrl =
        process.env.REACT_APP_API_URL + `/noti/subscribe/${userSeq.seq}`;
    const [message, setMessage] = useState();
    const [click, setClick] = useState(false);
    const [show, setShow] = useState(false);

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
    const res = useQueries([
        {
            // list
            queryKey: ["getAlarmList", "userSeq", "click"],
            queryFn: () => getAlarmList({ userSeq: userSeq.seq, click: click }),
        },
        {
            // 안 읽은 알림 개수
            queryKey: ["getUnReadAlarm", "userSeq"],
            queryFn: () => getUnReadAlarm(userSeq.seq),
        },
    ]);

    const queryClient = useQueryClient();
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
            color: "white",
            backgroundColor: "#FF8E9E",
            borderRadius: "50%",
            fontSize: "17px",
            width: "24px",
            height: "24px",
        },
    };
    return (
        <div className="alertIcon absolute right-8 top-1/2 -translate-y-1/2">
            <Badge
                badgeContent={res[1].data}
                sx={badgeStyle}
                overlap="circular"
                onClick={() => {
                    setShow(!show);
                    setClick(!click);
                }}
            >
                <Icon
                    icon="heroicons-solid:bell"
                    color="white"
                    width="35"
                    height="40"
                />
            </Badge>

            {show && (
                <div className="p-5 absolute w-[340px] h-[550px] bg-white right-0 top-11 rounded-lg border border-calendarDark">
                    <div className="text-xl border-b border-gray-500 pb-3">
                        알림
                    </div>
                    {res[0].data?.map((item: any) => (
                        <div
                            key={item.id}
                            className="py-4 px-2 text-base font-normal border-b border-gray-300"
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
