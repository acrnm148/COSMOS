import WishPlace from "../wish/WishPlace";
import WishCourse from "../wish/WishCourse";
import CourseDetail from "../wish/CourseDetail";
import WishMakeCourse from "./WishMakeCourse";
import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import CourseEdit from "./CourseEdit";
import { darkMode } from "../../recoil/states/UserState";
import { useRecoilState } from "recoil";

export default function WishList() {
    const isDark = useRecoilState(darkMode)[0];
    const [toggle, setToggle] = useState(true); // 장소: true / 코스: false
    const { courseId, editId, makeCourse } = useParams();
    const navigate = useNavigate();

    return (
        <div
            className={
                isDark ? "overflow-hidden text-white" : "overflow-hidden"
            }
        >
            <div className="menu w-full h-16 flex items-center cursor-pointer">
                <div
                    className={
                        "placeBtn w-1/2 h-full pt-[18px] float-left text-center text-xl" +
                        (toggle
                            ? isDark
                                ? " font-bold border-b-4 border-darkMain"
                                : " font-bold border-b-4 border-lightMain"
                            : "")
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
                            ? isDark
                                ? " font-bold border-b-4 border-darkMain"
                                : " font-bold border-b-4 border-lightMain"
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

            {/* wish 주 컴포넌트 변경 */}

            {/* 찜한 장소로 코스 만들기  */}
            {makeCourse === "makeCourse" && <WishMakeCourse />}

            {/* 찜한 장소 목록 */}
            {courseId === undefined &&
            editId === undefined &&
            makeCourse === undefined
                ? toggle && <WishPlace />
                : null}

            {/* 찜한 코스 목록 */}
            {courseId === undefined &&
            editId === undefined &&
            makeCourse === undefined
                ? !toggle && <WishCourse />
                : null}

            {/* 코스 상세보기 */}
            {courseId != undefined &&
            editId === undefined &&
            makeCourse === undefined ? (
                <CourseDetail courseId={courseId} />
            ) : null}

            {/* 코스 수정 */}
            {courseId === undefined &&
            editId != undefined &&
            makeCourse === undefined ? (
                <CourseEdit courseId={editId} />
            ) : null}
        </div>
    );
}
