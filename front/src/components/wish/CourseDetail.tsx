import { useEffect, useState } from "react";
import TMapResult from "../common/TMapResult";
import ListCard from "../common/ListCard";
import { Icon } from "@iconify/react";

interface Place {
    idx: number;
    name: string;
    thumbNailUrl: string;
    category: string;
    location: string;
    date: string;
    phoneNumber: string;
}

const testPlace: Place[] = [
    {
        idx: 1,
        name: "해운대 우시야",
        thumbNailUrl:
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJBHxJjvxdcCde02FU-xFtiN9IsfbChk2vrAI5CmMfkBiSIZJPym3uNJGDEeWuPDs6wOI&usqp=CAU",
        category: "음식",
        location: "부산",
        date: "2023년 2월 28일",
        phoneNumber: "010-1234-5678",
    },
    {
        idx: 2,
        name: "읍천리",
        thumbNailUrl:
            "https://img.siksinhot.com/place/1600741858600366.jpg?w=307&h=300&c=Y",
        category: "카페",
        location: "부산",
        date: "2023년 2월 28일",
        phoneNumber: "051-351-2345",
    },
    {
        idx: 3,
        name: "서면 CGV",
        thumbNailUrl:
            "https://blog.kakaocdn.net/dn/zUGvC/btqRjgDOk3L/c8GzoRfUoTRKCWaMAgtxk0/img.jpg",
        category: "문화",
        location: "부산",
        date: "2023년 2월 28일",
        phoneNumber: "010-1234-5678",
    },
];

export default function CourseDetail(props: { id: any }) {
    const state = {
        center: {
            lat: 37.566481622437934,
            lng: 126.98502302169841,
        },
    };
    const marker = [
        {
            placeId: 0,
            lat: 37.566481622437934,
            lng: 126.98502302169841,
        },
        {
            placeId: 1,
            lat: 37.567481622437934,
            lng: 126.98602302169841,
        },
        {
            placeId: 2,
            lat: 37.567381622437934,
            lng: 126.98502302169841,
        },
    ];
    const [places, setPlaces] = useState<Place[]>([]);

    useEffect(() => {
        setPlaces([...testPlace]);
    }, []);

    return (
        <div>
            <div className="text-center w-full max-w-[950px] overflow-y-scroll">
                <TMapResult state={state} marker={marker} className="fixed" />

                <ListCard height={false}>
                    {testPlace.map((a: Place) => (
                        <Item item={a}></Item>
                    ))}
                </ListCard>
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
                <div className="float-left w-1/3 m-auto">
                    <Icon
                        icon="material-symbols:edit-outline-rounded"
                        width="22"
                        height="22"
                        className="inline-block mr-2 mb-1"
                    />
                    편집
                </div>
                <div className="float-left w-1/3 m-auto">
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
    return (
        <div>
            <div className="idx absolute left-3 pt-1 bg-lightMain text-white w-10 h-10 rounded-full text-xl font-bold">
                {props.item.idx}
            </div>
            <div className="col-md-4 mb-4 ml-4 mr-4 p-3 bg-calendarGray rounded-lg">
                <img
                    className="float-left w-24 h-24 mr-4 rounded-md"
                    src={props.item.thumbNailUrl}
                    alt="img"
                />
                <div className="text-left mt-2 mb-2">
                    <div className="font-bold mb-2">{props.item.name}</div>
                    <div className="mb-2 text-sm text-gray-500">
                        {props.item.location}
                    </div>
                    <div className="text-sm">{props.item.phoneNumber}</div>
                </div>
            </div>
        </div>
    );
}
