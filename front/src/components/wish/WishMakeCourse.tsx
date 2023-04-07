import { useEffect, useState } from "react";
import TMapResult from "../common/TMapResult";
import ListCard from "../common/ListCard";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";
import { useQuery, useMutation } from "react-query";
import { getWishPlaceList, wishMakeCourse } from "../../apis/api/wish";
import Swal from "sweetalert2";
import { darkMode } from "../../recoil/states/UserState";
import { useNavigate } from "react-router";

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
    const isDark = useRecoilState(darkMode)[0];
    const [userSeq, setUserSeq] = useRecoilState(userState);
    const [list, setList] = useState<Place[]>();
    const navigate = useNavigate();

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
            navigate("/wish");
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
                <div className="my-5 font-bold">
                    계획할 장소를 순서대로 눌러주세요!
                </div>

                <div className="mt-3 h-[51.5vh] overflow-y-auto">
                    {list?.map((a: Place) => (
                        <Item
                            key={a.placeId}
                            item={a}
                            orders={orders}
                            handleOrders={handleOrders}
                        ></Item>
                    ))}
                </div>

                <div
                    className={
                        isDark
                            ? "cursor-pointer w-full h-16 pt-4 bg-darkMain z-[100001] text-white text-xl font-bold"
                            : "cursor-pointer w-full h-16 pt-4 bg-lightMain2 z-[100001] text-white text-xl font-bold"
                    }
                    onClick={() => {
                        (async () => {
                            const { value: getName } = await Swal.fire({
                                text: "코스 이름을 입력해주세요. ",
                                input: "text",
                                inputPlaceholder: "코스 이름",
                                confirmButtonText: "확인",
                                confirmButtonColor: isDark
                                    ? "#BE6DB7"
                                    : "#FF8E9E",
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
        </div>
    );
}

function Item(props: { item: Place; orders: number[]; handleOrders: any }) {
    const isDark = useRecoilState(darkMode)[0];

    return (
        <div>
            <div
                className={
                    isDark
                        ? "col-md-4 mb-4 ml-4 mr-4 p-3 h-32 bg-darkBackground2 rounded-lg flex"
                        : "col-md-4 mb-4 ml-4 mr-4 p-3 h-32 bg-calendarGray rounded-lg flex"
                }
            >
                <div className="w-10/12">
                    <img
                        className="float-left w-24 h-24 rounded-md mr-4 mt-1"
                        src={props.item.thumbNailUrl}
                        alt="img"
                    />
                    <div className="text-left mt-2">
                        <div className="font-bold mb-2 text-sm text-ellipsis">
                            {props.item.name}
                        </div>
                        <div
                            className={
                                isDark
                                    ? "mb-2 text-sm text-slate-300 text-ellipsis"
                                    : "mb-2 text-sm text-gray-500 text-ellipsis"
                            }
                        >
                            {props.item.address}
                        </div>
                        {/* <div className="cursor-pointer text-sm text-center border-2 border-calendarDark bg-white py-1 px-3 rounded">
                        유사 장소 추천
                    </div> */}
                    </div>
                </div>

                {props.orders.includes(props.item.placeId) ? (
                    <div
                        className={
                            isDark
                                ? "float-right cursor-pointer idx mt-7 pt-1.5 bg-darkMain text-white w-12 h-12 rounded-full text-2xl"
                                : "float-right cursor-pointer idx mt-7 pt-1.5 bg-lightMain text-white w-12 h-12 rounded-full text-2xl"
                        }
                        onClick={() => {
                            props.handleOrders(props.item.placeId);
                        }}
                    >
                        {props.orders.indexOf(props.item.placeId) + 1}
                    </div>
                ) : (
                    <div
                        className={
                            isDark
                                ? "float-right cursor-pointer idx mt-7 pt-1.5 bg-white border-2 border-darkMain text-white w-12 h-12 rounded-full text-2xl"
                                : "float-right cursor-pointer idx mt-7 pt-1.5 bg-white border-2 border-lightMain text-white w-12 h-12 rounded-full text-2xl"
                        }
                        onClick={() => {
                            props.handleOrders(props.item.placeId);
                        }}
                    ></div>
                )}
            </div>
        </div>
    );
}
