import WishPlace from "../wish/WishPlace";
import WishCourse from "../wish/WishCourse";
import CourseDetail from "../wish/CourseDetail";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function WishList() {
    const [toggle, setToggle] = useState(true); // 장소: true / 코스: false
    const { wishId } = useParams();
    const navigate = useNavigate();

    return (
        <div>
            <div className="menu w-full h-16 flex items-center">
                <div
                    className={
                        "placeBtn w-1/2 h-full pt-[18px] float-left text-center text-xl" +
                        (toggle ? " font-bold border-b-4 border-lightMain" : "")
                    }
                    onClick={() => {
                        setToggle(true);
                        navigate("/wish");
                    }}
                >
                    장소
                </div>
                <div
                    className={
                        "courseBtn w-1/2 h-full pt-[18px] float-left text-center text-xl" +
                        (!toggle ? " font-bold border-b-4 border-lightMain" : "")
                    }
                    onClick={() => {
                        setToggle(false);
                        navigate("/wish");
                    }}
                >
                    코스
                </div>
            </div>

            {wishId === undefined ? toggle && <WishPlace /> : null}
            {wishId === undefined ? !toggle && <WishCourse /> : null}

            {wishId != undefined && <CourseDetail id={wishId} />}
        </div>
    );
}
