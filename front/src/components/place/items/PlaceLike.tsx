import React, { useState } from "react";
import LikeImg from "../../../assets/like.png";
import NoLikeImg from "../../../assets/no-like.png";
import Swal from "sweetalert2";
import { likePlace, dislikePlace } from "../../../apis/api/place";

export default function PlaceLike({ like, placeId }: any) {
  const [isLike, setIsLike] = useState(like);

  const handleLikeButton = (e: React.MouseEvent) => {
    // 모달창 생성 이벤트 방지
    e.stopPropagation();
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
      : Toast.fire({
          title: "등록되었습니다.",
          icon: "success",
        });

    isLike ? dislikePlace(1, placeId) : likePlace(1, placeId);
    setIsLike((cur: boolean) => !cur);
  };

  return (
    <div className="absolute bottom-5 right-5" onClick={handleLikeButton}>
      <img
        className="w-[10vw] max-w-[60px]"
        src={isLike ? LikeImg : NoLikeImg}
        alt=""
      />
    </div>
  );
}
