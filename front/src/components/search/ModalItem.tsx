import React, { useState } from "react";
import Modal from "../common/Modal";
import Slider from "react-slick";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import ArticleOutlinedIcon from "@mui/icons-material/ArticleOutlined";
import LocalPhoneIcon from "@mui/icons-material/LocalPhone";
import StarIcon from "@mui/icons-material/Star";
import Swal from "sweetalert2";

export default function ModalItem() {
  const [review, setReview] = useState(false);
  const [modalOpen, setModalOpen] = useState(false);
  const [isLike, setIsLike] = useState(false);

  const openModal = () => {
    console.log("OPEN");
    setModalOpen(true);
  };
  const closeModal = () => {
    setModalOpen(false);
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
    <></>
    // <Modal open={modalOpen} close={closeModal} header="장소 상세" size="large">
    //   <div className="text-left overflow-auto">
    //     <div className="flex flex-row gap-2">
    //       <h1 className="text-2xl font-bold">{items.name}</h1>
    //       <div onClick={handleLikeButton}>
    //         {isLike ? (
    //           <img
    //             src={like}
    //             alt=""
    //             className="w-[35px] h-[35px] cursor-pointer"
    //           />
    //         ) : (
    //           <img
    //             src={noLike}
    //             alt=""
    //             className="w-[35px] h-[35px] cursor-pointer"
    //           />
    //         )}
    //       </div>
    //     </div>
    //     <hr className="divide-slate-300 m-2" />
    //     <div className="h-[300px] overflow-auto">
    //       <div className="justify-center m-auto w-[90%]">
    //         <Slider {...settings}>
    //           <div className="slider-img mb-5">
    //             <img src={like} alt="" />
    //           </div>
    //           <div className="slider-img mb-5">
    //             <img src={noLike} alt="" />
    //           </div>
    //         </Slider>
    //       </div>
    //       <div className="justify-left">
    //         <div className="flex flex-row gap-2">
    //           <div className="font-bold text-lg">장소명</div>
    //           <div className="flex flex-row">
    //             <div>
    //               <StarIcon fontSize="small" sx={{ color: "#FF8E9E" }} />
    //             </div>
    //             <div className="text-sm mt-[5px] text-lightMain font-bold">
    //               별점
    //             </div>
    //           </div>
    //         </div>
    //         <div className="text-slate-400 text-sm mb-3">주소</div>
    //         <div className="flex flex-row mb-2 gap-5">
    //           <AccessTimeIcon color="disabled" />
    //           <div>영업시간</div>
    //         </div>
    //         <div className="flex flex-row mb-2 gap-5">
    //           <ArticleOutlinedIcon color="disabled" />
    //           <div>장소상세</div>
    //         </div>
    //         <div className="flex flex-row mb-2 gap-5">
    //           <LocalPhoneIcon color="disabled" />
    //           <div>연락처</div>
    //         </div>
    //       </div>
    //       <hr className="divide-slate-300 m-5" />
    //       <div>
    //         <div className="flex flex-row gap-2">
    //           <button
    //             className={
    //               review
    //                 ? "font-bold text-sm text-slate-300"
    //                 : "font-bold text-sm"
    //             }
    //             onClick={() => setReview((cur) => !cur)}
    //           >
    //             ALL
    //           </button>
    //           <button
    //             className={
    //               !review
    //                 ? "font-bold text-sm text-slate-300"
    //                 : "font-bold text-sm"
    //             }
    //             onClick={() => setReview((cur) => !cur)}
    //           >
    //             CO-REVIEW
    //           </button>
    //         </div>
    //         {!review ? (
    //           <div className="mt-2 mb-5">리뷰 전체</div>
    //         ) : (
    //           <div className="mt-2 mb-5">우리 리뷰</div>
    //         )}
    //       </div>
    //     </div>
    //   </div>
    // </Modal>
  );
}
