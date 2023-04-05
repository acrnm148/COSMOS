import WishPlace from "../wish/WishPlace";
import WishCourse from "../wish/WishCourse";
import CourseDetail from "../wish/CourseDetail";
import WishMakeCourse from "./WishMakeCourse";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import CourseEdit from "./CourseEdit";

export default function WishList() {
    const [toggle, setToggle] = useState(true); // 장소: true / 코스: false
    const { courseId, editId, makeCourse } = useParams();
    const navigate = useNavigate();

    return (
        <div className="overflow-hidden">
            <div className="menu w-full h-16 bg-white flex items-center">
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
                        (!toggle
                            ? " font-bold border-b-4 border-lightMain"
                            : "")
                    }
                    onClick={() => {
                        setToggle(false);
                        navigate("/wish");
                    }}
                >
                    코스
                </div>
            </div>

            {makeCourse === "makeCourse" && <WishMakeCourse />}
            {courseId === undefined &&
            editId === undefined &&
            makeCourse === undefined
                ? toggle && <WishPlace />
                : null}
            {courseId === undefined &&
            editId === undefined &&
            makeCourse === undefined
                ? !toggle && <WishCourse />
                : null}
            {courseId != undefined &&
            editId === undefined &&
            makeCourse === undefined ? (
                <CourseDetail courseId={courseId} />
            ) : null}
            {courseId === undefined &&
            editId != undefined &&
            makeCourse === undefined ? (
                <CourseEdit courseId={editId} />
            ) : null}
        </div>
    );
}
