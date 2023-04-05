import { useEffect, useState, useCallback } from "react";
import TMapResult from "../common/TMapResult";
import ListCard from "../common/ListCard";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";
import { useMutation, useQueries } from "react-query";
import {
    getWishPlaceList,
    getCourseDetail,
    wishCourseEdit,
} from "../../apis/api/wish";
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
    const mutation = useMutation(wishCourseEdit, {
        onSuccess: (data) => {
            Swal.fire({
                text: "수정되었습니다. ",
                icon: "success",
                confirmButtonColor: "#FF8E9E",
            });
        },
        onError: () => {
            Swal.fire({
                text: "수정 실패했습니다. ",
                icon: "error",
                confirmButtonColor: "#FF8E9E",
            });
        },
    });

    console.log(res[0]);
    const [places, setPlaces] = useState<any>();
    const [wishPlaces, setWishPlaces] = useState<any>();

    let copy: Place[] = [];
    let copyPlaces: Place[] = [];
    const set = new Set();
    useEffect(() => {
        if (!res[0].isLoading && res[0].data) {
            copyPlaces = [...res[0].data.places];

            if (!res[1].isLoading && res[1].data) {
                copyPlaces?.map((p: Place) => {
                    set.add(p.placeId);
                    orders.push(p.placeId);
                });

                res[1].data.map((p: Place) => {
                    if (!set.has(p.placeId)) {
                        copy.push(p);
                        set.add(p.placeId);
                    }
                });

                setWishPlaces([...copy]);
                setPlaces([...copyPlaces]);
            }
        }
    }, [res[0].data, res[1].data]);

    // 장소 순서 저장하는 로직
    const [orders, setOrders] = useState<number[]>([]);
    function handleOrders(placeId: number) {
        const copy = [...orders];

        copy.includes(placeId)
            ? copy.includes(placeId) && copy?.splice(copy.indexOf(placeId), 1)
            : !copy.includes(placeId) && copy?.push(placeId);

        setOrders(copy);
    }
    useEffect(() => {
        setOrders(orders);
    }, [orders]);

    return (
        <div>
            <div className="text-center w-full max-w-[950px]">
                {/* <TMapResult state={state} marker={marker} className="fixed" /> */}

                {places && (
                    <div className="mb-16">
                        <div className="mb-1 text-lg font-bold text-left pb-3 mx-5">
                            {res[0].data?.name}
                        </div>

                        <div className="mb-5 font-bold mx-5">
                            계획할 장소를 순서대로 눌러주세요!
                        </div>

                        {places?.map((a: Place) => (
                            <Item
                                key={a.placeId}
                                item={a}
                                orders={orders}
                                handleOrders={handleOrders}
                            ></Item>
                        ))}

                        {wishPlaces?.map((a: Place) => (
                            <Item
                                key={a.placeId}
                                item={a}
                                orders={orders}
                                handleOrders={handleOrders}
                            ></Item>
                        ))}
                    </div>
                )}
            </div>

            <div
                className="cursor-pointer w-full h-16 pt-4 text-center bg-lightMain2 fixed bottom-20 z-[100001] text-white text-xl font-bold"
                onClick={() => {
                    (async () => {
                        const { value: getName } = await Swal.fire({
                            text: "코스 이름을 입력해주세요. ",
                            input: "text",
                            inputValue: res[0].data?.name,
                            confirmButtonText: "수정",
                            confirmButtonColor: "#FF8E9E",
                            showCancelButton: true,
                            cancelButtonText: "취소",
                            cancelButtonColor: "#B9B9B9",
                        });

                        if (getName) {
                            // 이후 처리되는 내용.
                            mutation.mutate({
                                courseId: res[0].data.courseId,
                                placeIds: orders,
                                name: getName,
                            });
                        }
                    })();
                }}
            >
                코스 수정
            </div>
        </div>
    );
}

function Item(props: { item: Place; orders: number[]; handleOrders: any }) {
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
                    <div className="cursor-pointer text-sm text-center border-2 border-calendarDark bg-white py-1 px-3 rounded">
                        유사 장소 추천
                    </div>
                </div>

                {props.orders.includes(props.item.placeId) ? (
                    <div
                        className="cursor-pointer idx mt-7 ml-10 pt-1.5 bg-lightMain text-white w-12 h-12 rounded-full text-2xl"
                        onClick={() => {
                            props.handleOrders(props.item.placeId);
                        }}
                    >
                        {props.orders.indexOf(props.item.placeId) + 1}
                    </div>
                ) : (
                    <div
                        className="cursor-pointer idx mt-7 ml-10 pt-1.5 bg-white border-2 border-lightMain text-white w-12 h-12 rounded-full text-2xl"
                        onClick={() => {
                            props.handleOrders(props.item.placeId);
                        }}
                    ></div>
                )}
            </div>
        </div>
    );
}
