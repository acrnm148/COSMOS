import { useEffect, useState } from "react";
import TMapResult from "../common/TMapResult";
import ListCard from "../common/ListCard";

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

export default function CourseEdit(props: { id: any }) {
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
                    <div className="mb-5 font-bold">
                        계획할 장소를 순서대로 눌러주세요!
                    </div>

                    {testPlace.map((a: Place) => (
                        <Item item={a}></Item>
                    ))}
                </ListCard>
            </div>

            <div className="w-full h-16 pt-4 text-center bg-lightMain2 fixed bottom-20 z-[100001] text-white text-xl font-bold">
                코스 수정
            </div>
        </div>
    );
}

function Item(props: { item: Place }) {
    return (
        <div>
            <div className="col-md-4 mb-4 ml-4 mr-4 p-3 h-32 bg-calendarGray rounded-lg flex">
                <img
                    className="w-24 h-24 rounded-md mr-4 mt-1"
                    src={props.item.thumbNailUrl}
                    alt="img"
                />

                <div className="text-left mt-2">
                    <div className="font-bold mb-2">{props.item.name}</div>
                    <div className="mb-2 text-sm text-gray-500">
                        {props.item.location}
                    </div>
                    <div className="text-sm border-2 border-lightMain2 bg-white py-1 px-3 rounded">
                        유사 장소 추천
                    </div>
                </div>

                <div className="idx mt-7 ml-10 pt-1.5 bg-lightMain text-white w-12 h-12 rounded-full text-2xl">
                    {props.item.idx}
                </div>
            </div>
        </div>
    );
}
