import { useEffect, useState } from "react";
import TMapResult from "../common/TMapResult";
import ListCard from "../common/ListCard";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";
import { useQuery, useMutation } from "react-query";
import { getWishPlaceList, wishMakeCourse } from "../../apis/api/wish";
import Swal from "sweetalert2";

interface Place {
    placeId: number;
    name: string;
    score: number;
    address: string;
    detail: string;
    thumbNailUrl: string;
    phoneNumber: string;
    orders: number; // 코스 순서
    latitude: string; // 위도
    longitude: string; // 경도
}

export default function WishMakeCourse() {
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

    const [userSeq, setUserSeq] = useRecoilState(userState);
    const [list, setList] = useState<Place[]>();

    // api
    const { data } = useQuery({
        queryKey: ["getWishPlaceList", "userSeq"],
        queryFn: () => getWishPlaceList(userSeq.seq),
    });
    const mutation = useMutation(wishMakeCourse, {
        onSuccess: (data) => {
            Swal.fire({
                // title: "제목",
                text: "생성되었습니다. ",
                icon: "success",
                confirmButtonColor: "#FF8E9E",
            });
        },
        onError: () => {
            Swal.fire({
                // title: "제목",
                text: "생성 실패했습니다. ",
                icon: "error",
                confirmButtonColor: "#FF8E9E",
            });
        },
    });

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
        setList(data);
        setOrders(orders);
    }, [data, orders]);

    return (
        <div>
            <div className="text-center w-full max-w-[950px]">
                <TMapResult state={state} marker={marker} className="fixed" />

                <ListCard height={false}>
                    <div className="mb-5 font-bold">
                        계획할 장소를 순서대로 눌러주세요!
                    </div>

                    {list?.map((a: Place) => (
                        <Item
                            key={a.placeId}
                            item={a}
                            orders={orders}
                            handleOrders={handleOrders}
                        ></Item>
                    ))}
                </ListCard>
            </div>

            <div
                className="w-full h-16 pt-4 text-center bg-lightMain2 fixed bottom-20 z-[100001] text-white text-xl font-bold"
                onClick={() => {
                    (async () => {
                        const { value: getName } = await Swal.fire({
                            text: "코스 이름을 입력해주세요. ",
                            input: "text",
                            inputPlaceholder: "코스 이름",
                            confirmButtonText: "확인",
                            confirmButtonColor: "#FF8E9E",
                            showCancelButton: true,
                            cancelButtonText: "취소",
                            cancelButtonColor: "#B9B9B9",
                        });

                        if (getName) {
                            // 이후 처리되는 내용.
                            mutation.mutate({
                                userSeq: userSeq.seq,
                                placeIds: orders,
                                name: getName,
                            });
                        }
                    })();
                }}
            >
                코스 생성
            </div>
        </div>
    );
}

function Item(props: { item: Place; orders: number[]; handleOrders: any }) {
    let name =
        props.item.name.length > 8
            ? props.item.name.slice(0, 8).concat("...")
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
                    <div className="font-bold mb-2 text-sm">{name}</div>
                    <div className="mb-2 text-sm text-gray-500">{address}</div>
                    <div className="text-sm text-center border-2 border-calendarDark bg-white py-1 px-3 rounded">
                        유사 장소 추천
                    </div>
                </div>

                {props.orders.includes(props.item.placeId) ? (
                    <div
                        className="idx mt-7 ml-10 pt-1.5 bg-lightMain text-white w-12 h-12 rounded-full text-2xl"
                        onClick={() => {
                            props.handleOrders(props.item.placeId);
                        }}
                    >
                        {props.orders.indexOf(props.item.placeId) + 1}
                    </div>
                ) : (
                    <div
                        className="idx mt-7 ml-10 pt-1.5 bg-white border-2 border-lightMain text-white w-12 h-12 rounded-full text-2xl"
                        onClick={() => {
                            props.handleOrders(props.item.placeId);
                        }}
                    ></div>
                )}
            </div>
        </div>
    );
}