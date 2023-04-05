import React, { useState } from "react";
import COSMOS from "../../assets/login/pinkCosmos.png";
import DCOSMOS from "../../assets/login/darkCosmos.png";
import LikeImg from "../../assets/like.png";
import NoLikeImg from "../../assets/no-like.png";
import DarkLikeImg from "../../assets/dark-like.png";
import DarkNoLikeImg from "../../assets/dark-no-like.png";
import Swal from "sweetalert2";
import { likeThisCourse } from "../../apis/api/place";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { darkMode } from "../../recoil/states/UserState";

export default function CourseLike({ tDistance, tTime, tFare, courseId }: any) {
  const isDark = useRecoilState(darkMode)[0];
  const navigate = useNavigate();
  const [isLike, setIsLike] = useState(false);
  const handleThisCourse = () => {
    const Toast = Swal.mixin({
      toast: true,
      position: "bottom-end",
      showConfirmButton: false,
      timer: 1500,
      timerProgressBar: false,
    });
    isLike
      ? Toast.fire({
          title: "해제되었습니다.",
          icon: "success",
        })
      : Swal.fire({
          title: "코스 찜 완료!",
          text: "마이페이지에서 코스를 수정할 수 있어요. 이동하시겠습니까?",
          background: isDark[0] ? "#585858" : "#FFFFFF",
          color: isDark[0] ? "white" : "black",
          imageUrl: isDark[0] ? DCOSMOS : COSMOS,
          imageWidth: 200,
          imageHeight: 200,
          imageAlt: "Custom image",
          input: "text",
          inputPlaceholder: "코스 이름을 설정",
          inputAttributes: {
            autocapitalize: "off",
          },
          showLoaderOnConfirm: true,
          preConfirm: (title) => {
            const courseInfo = {
              courseId: courseId,
              name: title,
            };
            console.log(courseInfo);
            likeThisCourse(courseInfo);
            return <div key={title}></div>;
          },
          showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
          confirmButtonColor: isDark[0] ? "#BE6DB7" : "#FF8E9E",
          cancelButtonColor: "#A3A3A3", // cancel 버튼 색깔 지정
          confirmButtonText: "확인", // confirm 버튼 텍스트 지정
          cancelButtonText: "취소", // cancel 버튼 텍스트 지정
        }).then((result) => {
          // 만약 Promise리턴을 받으면,
          if (result.isConfirmed) {
            // 만약 모달창에서 confirm 버튼을 눌렀다면 마이페이지 찜한 코스로 이동
            navigate(`/wish/course/${courseId}/detail`);
          }
        });

    setIsLike((cur) => !cur);
  };
  return (
    <div className="flex flex-row relative bg-lightMain3 justify-center h-14 wb-3 dark:bg-darkMain">
      <div className="flex flex-row gap-3 h-15 m-auto">
        <span className="font-bold dark:text-white">{tDistance}</span>
        <span className="font-bold dark:text-white">{tTime}</span>
        <span className="font-bold dark:text-white">{tFare}</span>
      </div>
      <div className="absolute right-2 mt-2">
        {isLike ? (
          <img
            onClick={handleThisCourse}
            className="w-10 h-10 m-auto cursor-pointer"
            src={isDark ? DarkLikeImg : LikeImg}
            alt=""
          />
        ) : (
          <img
            onClick={handleThisCourse}
            className="w-10 h-10 m-auto cursor-pointer"
            src={isDark ? DarkNoLikeImg : NoLikeImg}
            alt=""
          />
        )}
      </div>
    </div>
  );
}
