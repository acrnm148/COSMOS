import WishPlace from "../wish/WishPlace";
import WishCourse from "../wish/WishCourse";
import { useState } from "react";

export default function WishList() {
    const [toggle, setToggle] = useState(true); // 장소: true / 코스: false

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
                    }}
                >
                    코스
                </div>
            </div>

            {toggle && <WishPlace />}
            {!toggle && <WishCourse />}
        </div>
    );
}
