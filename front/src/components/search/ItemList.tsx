import React, { useState } from "react";
import ListCard from "../common/ListCard";
import like from "../../assets/like.png";
import noLike from "../../assets/no-like.png";
import Swal from "sweetalert2";
import "../../css/listItem.css";
import DefaultImg from "../../assets/login/pinkCosmos.png";
import SearchModalItem from "./SearchModalIem";

export default function ItemList({ items, setState }: any) {
  const [isLike, setIsLike] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);

  const openModal = () => {
    console.log("OPEN");
    setModalOpen(true);
  };

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
    setIsLike((cur) => !cur);
  };

  return (
    <>
      <ListCard>
        {items === undefined ? null : (
          <div
            className="flex flex-row relative card-content"
            onClick={openModal}
          >
            <div className="flex justify-center my-auto basis-3/12">
              <img
                src={
                  items.thumbNailUrl === null ? DefaultImg : items.thumbNailUrl
                }
                alt=""
                className="w-[20vw] h-[15vw] max-w-[200px] max-h-[150px] rounded-lg"
              />
            </div>
            <div className="flex flex-col column-3 basis-8/12 text-left">
              <div className="font-bold text-lg">{items.name}</div>
              <div className="font-thin text-slate-400 text-sm">
                {items.address}
              </div>
            </div>
            <div
              className="basis-1/12 absolute bottom-0 right-5"
              onClick={handleLikeButton}
            >
              {isLike ? (
                <img src={like} alt="" className="w-[10vw] max-w-[60px]" />
              ) : (
                <img src={noLike} alt="" className="w-[10vw] max-w-[60px]" />
              )}
            </div>
          </div>
        )}
      </ListCard>
      {items === undefined ? null : (
        <SearchModalItem
          modalOpen={modalOpen}
          setModalOpen={setModalOpen}
          isLike={isLike}
          setIsLike={setIsLike}
          handleLikeButton={handleLikeButton}
          items={items}
          setState={setState}
        />
      )}
    </>
  );
}
