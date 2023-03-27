import React, { useState } from "react";
import like from "../../assets/like.png";
import noLike from "../../assets/no-like.png";
import Swal from "sweetalert2";
import { likePlace, dislikePlace } from "../../apis/api/place";
import SearchModalItem from "./SearchModalIem";

export default function PlaceLike({
  items,
  placeId,
  type,
  setState,
  modalOpen,
  closeModal,
  setModalOpen,
}: any) {
  const [likeBtn, setLikeBtn] = useState(items.like);
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
    likeBtn
      ? Toast.fire({
          title: "해제되었습니다.",
          icon: "success",
        })
      : Toast.fire({
          title: "등록되었습니다.",
          icon: "success",
        });

    likeBtn ? dislikePlace(1, items.placeId) : likePlace(1, items.placeId);
    setLikeBtn((cur: boolean) => !cur);
  };

  return (
    <>
      <div
        className="basis-1/12 absolute bottom-0 right-5"
        onClick={handleLikeButton}
      >
        {likeBtn ? (
          <img src={like} alt="" className="w-[10vw] max-w-[60px]" />
        ) : (
          <img src={noLike} alt="" className="w-[10vw] max-w-[60px]" />
        )}
      </div>
      <SearchModalItem
        modalOpen={modalOpen}
        closeModal={closeModal}
        setModalOpen={setModalOpen}
        placeId={placeId}
        type={type}
        setState={setState}
      />
    </>
  );
}
