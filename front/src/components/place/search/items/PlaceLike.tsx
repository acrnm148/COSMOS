import React, { useState } from "react";
import LikeImg from "../../../../assets/like.png";
import NoLikeImg from "../../../../assets/no-like.png";
import DarkLikeImg from "../../../../assets/dark-like.png";
import DarkNoLikeImg from "../../../../assets/dark-no-like.png";
import Swal from "sweetalert2";
import { likePlace, dislikePlace } from "../../../../apis/api/place";
import { useRecoilState } from "recoil";
import { userState, darkMode } from "../../../../recoil/states/UserState";

export default function PlaceLike({ like, placeId }: any) {
  const isDark = useRecoilState(darkMode);
  const userSeq = useRecoilState(userState);
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

    isLike
      ? dislikePlace(userSeq[0].seq, placeId)
      : likePlace(userSeq[0].seq, placeId);
    setIsLike((cur: boolean) => !cur);
  };

  return (
    <div className="absolute bottom-5 right-5" onClick={handleLikeButton}>
      {isDark ? (
        <img
          className="w-[10vw] max-w-[60px]"
          src={isLike ? DarkLikeImg : DarkNoLikeImg}
          alt=""
        />
      ) : (
        <img
          className="w-[10vw] max-w-[60px]"
          src={isLike ? LikeImg : NoLikeImg}
          alt=""
        />
      )}
    </div>
  );
}
