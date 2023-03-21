import StarIcon from "@mui/icons-material/Star";
import { Icon } from "@iconify/react";
import Swal from "sweetalert2";
import { Navigate, useNavigate } from "react-router-dom";

export default function WishCourse() {
    let list: any = [
        {
            id: 0,
            courseName: "200일 기념 데이트",
            date: "2022-03-21",
            subCategory: "flex",
            place: [
                {
                    placeId: 0,
                    name: "해운대 우시야",
                    thumbNailUrl:
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJBHxJjvxdcCde02FU-xFtiN9IsfbChk2vrAI5CmMfkBiSIZJPym3uNJGDEeWuPDs6wOI&usqp=CAU",
                },
                {
                    placeId: 2,
                    name: "읍천리",
                    thumbNailUrl:
                        "https://img.siksinhot.com/place/1600741858600366.jpg?w=307&h=300&c=Y",
                },
                {
                    placeId: 5,
                    name: "서면 CGV",
                    thumbNailUrl:
                        "https://blog.kakaocdn.net/dn/zUGvC/btqRjgDOk3L/c8GzoRfUoTRKCWaMAgtxk0/img.jpg",
                },
            ],
        },
        {
            id: 1,
            courseName: "크리스마스~",
            date: "2022-12-25",
            subCategory: "flex",
            place: [
                {
                    placeId: 0,
                    name: "해운대 우시야",
                    thumbNailUrl:
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJBHxJjvxdcCde02FU-xFtiN9IsfbChk2vrAI5CmMfkBiSIZJPym3uNJGDEeWuPDs6wOI&usqp=CAU",
                },
                {
                    placeId: 2,
                    name: "읍천리",
                    thumbNailUrl:
                        "https://img.siksinhot.com/place/1600741858600366.jpg?w=307&h=300&c=Y",
                },
                {
                    placeId: 5,
                    name: "서면 CGV",
                    thumbNailUrl:
                        "https://blog.kakaocdn.net/dn/zUGvC/btqRjgDOk3L/c8GzoRfUoTRKCWaMAgtxk0/img.jpg",
                },
            ],
        },
        {
            id: 3,
            courseName: "여수 여행",
            date: "2022-12-25",
            subCategory: "flex",
            place: [
                {
                    placeId: 0,
                    name: "해운대 우시야",
                    thumbNailUrl:
                        "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJBHxJjvxdcCde02FU-xFtiN9IsfbChk2vrAI5CmMfkBiSIZJPym3uNJGDEeWuPDs6wOI&usqp=CAU",
                },
                {
                    placeId: 2,
                    name: "읍천리",
                    thumbNailUrl:
                        "https://img.siksinhot.com/place/1600741858600366.jpg?w=307&h=300&c=Y",
                },
                {
                    placeId: 5,
                    name: "서면 CGV",
                    thumbNailUrl:
                        "https://blog.kakaocdn.net/dn/zUGvC/btqRjgDOk3L/c8GzoRfUoTRKCWaMAgtxk0/img.jpg",
                },
            ],
        },
    ];

    return (
        <div>
            <div className="mx-5 mt-5 h-full">
                <div className="title font-medium text-xl inline-block">찜한 코스</div>
                <div className="cnt ml-2 font-bold text-red-600 text-xl inline-block">
                    {list.length}
                </div>

                <div className="mt-4 h-[540px] overflow-y-auto">
                    {list.map((a: any) => (
                        <Item item={a}></Item>
                    ))}
                </div>
            </div>
        </div>
    );
}

function Item(props: { item: any }) {
    const navigate = useNavigate();

    return (
        <div
            className="mb-5"
            onClick={() => {
                navigate(`/wish/course/${props.item.id}`);
            }}
        >
            <div className="col-md-4 p-3 h-48 bg-calendarGray rounded-t-lg">
                <div className="font-bold mb-3">{props.item.courseName}</div>

                <div className="w-full h-36 flex overflow-x-scroll scrollbar-hide">
                    {props.item.place.map((a: any) => {
                        return (
                            <div className="float-left flex-none w-32 h-28 mr-3 text-center">
                                <img
                                    className="w-32 h-24 rounded-lg"
                                    src={a.thumbNailUrl}
                                    alt="img"
                                />
                                <div className="w-32 h-8 mt-2">{a.name}</div>
                            </div>
                        );
                    })}
                </div>
            </div>
            <div className="btns w-full h-12 bg-lightMain3 rounded-b-lg text-center flex">
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
