import { useEffect, useState, useCallback } from "react";
import TMapResult from "../common/TMapResult";
import ListCard from "../common/ListCard";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";
import { useQuery, useMutation, useQueries } from "react-query";
import { getWishPlaceList, getCourseDetail } from "../../apis/api/wish";
import Swal from "sweetalert2";

interface Place {
    placeId: number;
    name: string;
    thumbNailUrl: string;
    address: string;
    phoneNumber: string;
    score: number;
    orders: number; // 코스 순서
    latitude: string; // 위도
    longitude: string; // 경도
    overview: string; // 개요
}

export default function CourseEdit(props: { courseId: any }) {
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

    // api
    const [userSeq, setUserSeq] = useRecoilState(userState);
    const res = useQueries([
        {
            queryKey: ["getCourseDetail", "courseId"],
            queryFn: () =>
                getCourseDetail(props.courseId, userSeq.seq, userSeq.acToken),
        },
        {
            queryKey: ["getWishPlaceList", "userSeq"],
            queryFn: () => getWishPlaceList(userSeq.seq),
        },
    ]);
    console.log(res);

    /////////////////////////////////
    // const [places, setPlaces] = useState<any>([...res[0].data.places]);
    const [places, setPlaces] = useState<any>();
    const [wishPlaces, setWishPlaces] = useState<any>();

    let copy: Place[] = [];
    const set = new Set();
    useEffect(() => {
        places?.map((p: Place) => set.add(p.placeId));

        res[0].data.places.map((p: Place) => {
            if (!set.has(p.placeId)) {
                copy.push(p);
                set.add(p.placeId);
            }
        });

        setWishPlaces([...copy]);
    }, [res[0].isLoading, res[0].data, res[1].data, res[1].isLoading]);

    return (
        <div>
            <div className="text-center w-full max-w-[950px]">
                <TMapResult state={state} marker={marker} className="fixed" />
                {places && (
                    <ListCard height={false}>
                        <div className="mb-5 font-bold">
                            계획할 장소를 순서대로 눌러주세요!
                        </div>

                        {places?.map((a: Place) => (
                            <Item key={a.placeId} item={a}></Item>
                        ))}

                        {wishPlaces?.map((a: Place) => (
                            <Item key={a.placeId} item={a}></Item>
                        ))}
                    </ListCard>
                )}
            </div>

            <div className="w-full h-16 pt-4 text-center bg-lightMain2 fixed bottom-20 z-[100001] text-white text-xl font-bold">
                코스 수정
            </div>
        </div>
    );
}

function Item(props: { item: Place }) {
    let name =
        props.item.name.length > 7
            ? props.item.name.slice(0, 7).concat("...")
            : props.item.name;
    let address =
        props.item.address.length > 8
            ? props.item.address.slice(0, 8).concat("...")
            : props.item.address;

    return (
        <div>
            <div className="col-md-4 mb-4 ml-4 mr-4 p-3 h-32 bg-calendarGray rounded-lg flex">
                <img
                    className="w-24 h-24 rounded-md mr-4 mt-1"
                    src={props.item.thumbNailUrl}
                    alt="img"
                />
                <div className="text-left mt-2">
                    <div className="font-bold mb-2">{name}</div>
                    <div className="mb-2 text-sm text-gray-500">{address}</div>
                    <div className="text-sm text-center border-2 border-calendarDark bg-white py-1 px-3 rounded">
                        유사 장소 추천
                    </div>
                </div>

                {props.item.orders > 0 && (
                    <div className="idx mt-7 ml-10 pt-1.5 bg-lightMain text-white w-12 h-12 rounded-full text-2xl">
                        {props.item.orders}
                    </div>
                )}

                {props.item.orders === 0 && (
                    <div className="idx mt-7 ml-10 pt-1.5 bg-white border-2 border-lightMain text-white w-12 h-12 rounded-full text-2xl"></div>
                )}
            </div>
        </div>
    );
}

// const coursePlace: Place[] = [
//     {
//         placeId: 1,
//         placeName: "해운대 우시야",
//         thumbNailUrl:
//             "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJBHxJjvxdcCde02FU-xFtiN9IsfbChk2vrAI5CmMfkBiSIZJPym3uNJGDEeWuPDs6wOI&usqp=CAU",
//         address: "부산 해운대구 우동1로38번길 2",
//         date: "2023년 2월 28일",
//         phoneNumber: "010-1234-5678",
//         score: 3.5,
//         orders: 1,
//         latitude: "37.566481622437934",
//         longitude: "126.98502302169841",
//         overview:
//             "서울 광화문에 위치한 사발은 새로운 스타일의 퓨전국수, 덮밥, 국밥을 정성스롭고 아름답게 대접합니다.",
//     },
//     {
//         placeId: 2,
//         placeName: "읍천리",
//         thumbNailUrl:
//             "https://img.siksinhot.com/place/1600741858600366.jpg?w=307&h=300&c=Y",
//         address: "서울 종로구 자하문로7길 11",
//         date: "2023년 2월 28일",
//         phoneNumber: "051-351-1234",
//         score: 4.5,
//         orders: 2,
//         latitude: "37.566481622437934",
//         longitude: "126.98502302169841",
//         overview: "숙성사시미 전문 캐쥬얼 스시야",
//     },
//     {
//         placeId: 3,
//         placeName: "서면 CGV",
//         thumbNailUrl:
//             "https://blog.kakaocdn.net/dn/zUGvC/btqRjgDOk3L/c8GzoRfUoTRKCWaMAgtxk0/img.jpg",
//         address: "부산 해운대구 우동1로38번길 2",
//         date: "2023년 2월 28일",
//         phoneNumber: "010-5678-4321",
//         score: 5.0,
//         orders: 3,
//         latitude: "37.566481622437934",
//         longitude: "126.98502302169841",
//         overview: "부산 소고기 오마카세 수요미식회에도 나온 맛있는 소고기",
//     },
// ];

// const wishPlace: Place[] = [
//     {
//         placeId: 1,
//         placeName: "해운대 우시야",
//         thumbNailUrl:
//             "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJBHxJjvxdcCde02FU-xFtiN9IsfbChk2vrAI5CmMfkBiSIZJPym3uNJGDEeWuPDs6wOI&usqp=CAU",
//         address: "부산 해운대구 우동1로38번길 2",
//         date: "2023년 2월 28일",
//         phoneNumber: "010-1234-5678",
//         score: 3.5,
//         orders: 0,
//         latitude: "37.566481622437934",
//         longitude: "126.98502302169841",
//         overview:
//             "서울 광화문에 위치한 사발은 새로운 스타일의 퓨전국수, 덮밥, 국밥을 정성스롭고 아름답게 대접합니다.",
//     },
//     {
//         placeId: 7,
//         placeName: "2222 읍천리",
//         thumbNailUrl:
//             "https://img.siksinhot.com/place/1600741858600366.jpg?w=307&h=300&c=Y",
//         address: "서울 종로구 자하문로7길 11",
//         date: "2023년 2월 28일",
//         phoneNumber: "051-351-1234",
//         score: 4.5,
//         orders: 0,
//         latitude: "37.567481622437934",
//         longitude: "126.98602302169841",
//         overview: "숙성사시미 전문 캐쥬얼 스시야",
//     },
//     {
//         placeId: 8,
//         placeName: "2222 서면 CGV",
//         thumbNailUrl:
//             "https://blog.kakaocdn.net/dn/zUGvC/btqRjgDOk3L/c8GzoRfUoTRKCWaMAgtxk0/img.jpg",
//         address: "부산 해운대구 우동1로38번길 2",
//         date: "2023년 2월 28일",
//         phoneNumber: "010-5678-4321",
//         score: 5.0,
//         orders: 0,
//         latitude: "37.567381622437934",
//         longitude: "126.98502302169841",
//         overview: "부산 소고기 오마카 세 수요미식회에도 나온 맛있는 소고기",
//     },
// ];
