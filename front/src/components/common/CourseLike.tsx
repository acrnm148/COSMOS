import React, { useState } from "react";
import COSMOS from "../../assets/login/pinkCosmos.png";
import LikeImg from "../../assets/like.png";
import NoLikeImg from "../../assets/no-like.png";
import Swal from "sweetalert2";
import { likeThisCourse } from "../../apis/api/place";
import { useNavigate } from "react-router-dom";

export default function CourseLike({ tDistance, tTime, tFare, courseId }: any) {
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
          imageUrl: COSMOS,
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
          confirmButtonColor: "#3085d6", // confrim 버튼 색깔 지정
          cancelButtonColor: "#d33", // cancel 버튼 색깔 지정
          confirmButtonText: "확인", // confirm 버튼 텍스트 지정
          cancelButtonText: "취소", // cancel 버튼 텍스트 지정
        }).then((result) => {
          // 만약 Promise리턴을 받으면,
          if (result.isConfirmed) {
            // 만약 모달창에서 confirm 버튼을 눌렀다면 마이페이지 찜한 코스로 이동
            navigate(`wish/course/${courseId}/detail`);
          }
        });

    setIsLike((cur) => !cur);
  };
  return (
    <div className="flex flex-row relative bg-lightMain3 justify-center h-14 wb-3">
      <div className="flex flex-row gap-3 h-15 m-auto">
        <span className="font-bold">{tDistance}a</span>
        <span className="font-bold">{tTime}a</span>
        <span className="font-bold">{tFare}a</span>
      </div>
      <div className="absolute right-2 mt-2">
        {isLike ? (
          <img
            onClick={handleThisCourse}
            className="w-10 h-10 m-auto cursor-pointer"
            src={LikeImg}
            alt=""
          />
        ) : (
          <img
            onClick={handleThisCourse}
            className="w-10 h-10 m-auto cursor-pointer"
            src={NoLikeImg}
            alt=""
          />
        )}
      </div>
    </div>
  );
}
