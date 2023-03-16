import React, { useState } from "react";
import ListCard from "../common/ListCard";
import like from "../../assets/like.png";
import noLike from "../../assets/no-like.png";
import Swal from "sweetalert2";
import ArrowDropUpIcon from "@mui/icons-material/ArrowDropUp";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import "../../css/listItem.css";
import Modal2 from "../common/ModalLarge";

export default function ItemList() {
  const [isLike, setIsLike] = useState(false);
  const [up, setUp] = useState(false);

  const [modalOpen, setModalOpen] = useState(false);

  const openModal = () => {
    console.log("OPEN");
    setModalOpen(true);
  };
  const closeModal = () => {
    setModalOpen(false);
  };

  const handleDropBtn = () => {
    setUp((cur) => !cur);
    const list = document.querySelector("#listBox") as HTMLElement;
    if (up) {
      list.style.marginTop = "0px";
      list.style.height = "70vh";
    } else {
      list.style.marginTop = "-400px";
      list.style.height = "120vh";
    }
  };

  const handleLikeButton = () => {
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
    <div
      className="mb-[50px] z-[100000] bg-white h-[70vh] relative"
      id="listBox"
    >
      {up ? (
        <ArrowDropDownIcon
          fontSize="large"
          color="disabled"
          onClick={handleDropBtn}
        />
      ) : (
        <ArrowDropUpIcon
          fontSize="large"
          color="disabled"
          onClick={handleDropBtn}
        />
      )}

      <ListCard>
        <div className="flex flex-row relative" onClick={openModal}>
          <div className="flex justify-center my-auto basis-3/12">
            <img
              src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQC0rn6j8ZXwbLsoNdV5CEGem6iXs3JLMuykklndrH&s"
              alt=""
              className="w-[20vw] h-[15vw] max-w-[200px] max-h-[150px] rounded-lg"
            />
          </div>
          <div className="flex flex-col column-3 basis-8/12 text-left">
            <div className="font-bold text-lg">가게명</div>
            <div className="font-thin text-slate-400 text-sm">주소</div>
            <div className="font-bold text-base mt-[5px]">상세설명</div>
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
      </ListCard>
      <Modal2 open={modalOpen} close={closeModal} header="가게명"></Modal2>
    </div>
  );
}
