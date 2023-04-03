import { Icon } from "@iconify/react";
import { Badge } from "@mui/material";
import { useEffect, useState } from "react";
import { useQuery } from "react-query";
import { getAlarmList } from "../../apis/api/alarm";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";
import { EventSourcePolyfill } from "event-source-polyfill";

export default function Alarm() {
    const [userSeq, setUserSeq] = useRecoilState(userState);
    let subscribeUrl =
        process.env.REACT_APP_API_URL + `/noti/subscribe/${userSeq.seq}`;
    const [message, setMessage] = useState();
    const [isCheck, setIscheck] = useState(false);
    const [show, setShow] = useState(false);
    const [start, setStart] = useState(true);
    const [click, setClick] = useState(false);
    const [count, setCount] = useState(0);

    const { data } = useQuery({
        queryKey: ["getAlarmList", "userSeq"],
        queryFn: () => getAlarmList(userSeq.seq),
    });

    // console.log(data);

    const handleAlarmDetail = (item: any) => {};

    useEffect(() => {
        let eventSource = new EventSourcePolyfill(subscribeUrl, {
            headers: {
                "Content-Type": "text/event-stream",
                "Access-Control-Allow-Origin": "",
                AccessToken: `Bearer ${userSeq.acToken}`,
                "Cache-Control": "no-cache",
            },
            heartbeatTimeout: 60000,
            withCredentials: true,
        });
        eventSource.addEventListener("connect", function (e: any) {
            // setMessage(e.data);
            console.log(e.data);
        });
        eventSource.addEventListener("message", function (e: any) {
            console.log(e.data);
        });
        eventSource.addEventListener("error", function (e: any) {
            console.log("error");
        });
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
                badgeContent={3}
                sx={badgeStyle}
                overlap="circular"
                onClick={() => {
                    setShow(!show);
                }}
            >
                <Icon
                    icon="heroicons-solid:bell"
                    color="white"
                    width="35"
                    height="40"
                    onClick={() => {
                        setIscheck(false);
                        setStart(false);
                    }}
                />
            </Badge>

            {show && (
                <div className="absolute w-[340px] h-[500px] bg-white right-0 top-11 rounded-lg border-2 border-calendarDark">
                    <div
                        onClick={() => {
                            setShow(!show);
                        }}
                    >
                        닫기
                    </div>
                </div>
            )}
        </div>
    );
}
