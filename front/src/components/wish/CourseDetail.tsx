import { useEffect, useState } from "react";
import TMapResult from "../common/TMapResult";
import { useRef } from "react";
import { BottomSheet, BottomSheetRef } from "react-spring-bottom-sheet";

interface Place {
    idx: number;
    name: string;
    thumbNailUrl: string;
    category: string;
    location: string;
    date: string;
}

const testPlace: Place[] = [
    {
        idx: 0,
        name: "해운대 우시야 ",
        thumbNailUrl:
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJBHxJjvxdcCde02FU-xFtiN9IsfbChk2vrAI5CmMfkBiSIZJPym3uNJGDEeWuPDs6wOI&usqp=CAU",
        category: "음식",
        location: "부산",
        date: "2023년 2월 28일",
    },
    {
        idx: 1,
        name: "읍천리",
        thumbNailUrl:
            "https://img.siksinhot.com/place/1600741858600366.jpg?w=307&h=300&c=Y",
        category: "카페",
        location: "부산",
        date: "2023년 2월 28일",
    },
    {
        idx: 2,
        name: "서면 CGV",
        thumbNailUrl:
            "https://blog.kakaocdn.net/dn/zUGvC/btqRjgDOk3L/c8GzoRfUoTRKCWaMAgtxk0/img.jpg",
        category: "문화",
        location: "부산",
        date: "2023년 2월 28일",
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

    const sheetRef = useRef<BottomSheetRef>(null);

    return (
        <div>
            <TMapResult state={state} marker={marker} />

            <BottomSheet open ref={sheetRef}>
                <button
                    onClick={() => {
                        // Full typing for the arguments available in snapTo, yay!!
                        {
                            sheetRef.current &&
                                sheetRef.current.snapTo(
                                    ({ maxHeight }) => maxHeight
                                );
                        }
                    }}
                >
                    Expand to full height
                </button>
            </BottomSheet>
        </div>
    );
}
