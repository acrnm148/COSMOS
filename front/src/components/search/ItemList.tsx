import React, { useState } from "react";
import ListCard from "../common/ListCard";
import like from "../../assets/like.png";
import noLike from "../../assets/no-like.png";
import Swal from "sweetalert2";
import ArrowDropUpIcon from "@mui/icons-material/ArrowDropUp";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import "../../css/listItem.css";
import Modal from "../common/Modal";
import Slider from "react-slick";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import ArticleOutlinedIcon from "@mui/icons-material/ArticleOutlined";
import LocalPhoneIcon from "@mui/icons-material/LocalPhone";
import StarIcon from "@mui/icons-material/Star";

export default function ItemList() {
  const [isLike, setIsLike] = useState(false);
  const [up, setUp] = useState(false);
  const [review, setReview] = useState(false);

  const [modalOpen, setModalOpen] = useState(false);

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

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
            <div className="text-base mt-[5px]">상세설명</div>
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
      <Modal
        open={modalOpen}
        close={closeModal}
        header="장소 상세"
        size="large"
      >
        <div className="text-left overflow-auto">
          <div className="flex flex-row gap-2">
            <h1 className="text-2xl font-bold">장소명</h1>
            <div onClick={handleLikeButton}>
              {isLike ? (
                <img
                  src={like}
                  alt=""
                  className="w-[35px] h-[35px] cursor-pointer"
                />
              ) : (
                <img
                  src={noLike}
                  alt=""
                  className="w-[35px] h-[35px] cursor-pointer"
                />
              )}
            </div>
          </div>
          <hr className="divide-slate-300 m-2" />
          <div className="h-[300px] overflow-auto">
            <div className="justify-center m-auto w-[90%]">
              <Slider {...settings}>
                <div className="slider-img mb-5">
                  <img src={like} alt="" />
                </div>
                <div className="slider-img mb-5">
                  <img src={noLike} alt="" />
                </div>
              </Slider>
            </div>
            <div className="justify-left">
              <div className="flex flex-row gap-2">
                <div className="font-bold text-lg">장소명</div>
                <div className="flex flex-row">
                  <div>
                    <StarIcon fontSize="small" sx={{ color: "#FF8E9E" }} />
                  </div>
                  <div className="text-sm mt-[5px] text-lightMain font-bold">
                    별점
                  </div>
                </div>
              </div>
              <div className="text-slate-400 text-sm mb-3">주소</div>
              <div className="flex flex-row mb-2 gap-5">
                <AccessTimeIcon color="disabled" />
                <div>영업시간</div>
              </div>
              <div className="flex flex-row mb-2 gap-5">
                <ArticleOutlinedIcon color="disabled" />
                <div>장소상세</div>
              </div>
              <div className="flex flex-row mb-2 gap-5">
                <LocalPhoneIcon color="disabled" />
                <div>연락처</div>
              </div>
            </div>
            <hr className="divide-slate-300 m-5" />
            <div>
              <div className="flex flex-row gap-2">
                <button
                  className={
                    review
                      ? "font-bold text-sm text-slate-300"
                      : "font-bold text-sm"
                  }
                  onClick={() => setReview((cur) => !cur)}
                >
                  ALL
                </button>
                <button
                  className={
                    !review
                      ? "font-bold text-sm text-slate-300"
                      : "font-bold text-sm"
                  }
                  onClick={() => setReview((cur) => !cur)}
                >
                  CO-REVIEW
                </button>
              </div>
              {!review ? (
                <div className="mt-2 mb-5">리뷰 전체</div>
              ) : (
                <div className="mt-2 mb-5">우리 리뷰</div>
              )}
            </div>
          </div>
        </div>
      </Modal>
    </div>
  );
}
