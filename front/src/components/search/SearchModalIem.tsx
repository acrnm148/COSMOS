import React, { useEffect, useState } from "react";
import likeBtn from "../../assets/like.png";
import noLikeBtn from "../../assets/no-like.png";
import Modal from "../common/Modal";
import Slider from "react-slick";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import ArticleOutlinedIcon from "@mui/icons-material/ArticleOutlined";
import LocalPhoneIcon from "@mui/icons-material/LocalPhone";
import StarIcon from "@mui/icons-material/Star";
import Swal from "sweetalert2";

import { useQuery } from "react-query";
import { getPlaceDetail } from "../../apis/api/place";

export default function SearchModalItem({
  modalOpen,
  closeModal,
  placeId,
  type,
  setState,
}: any) {
  const [review, setReview] = useState(false);

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  const { data, isLoading } = useQuery({
    queryKey: ["getPlaceDetail", placeId, type],
    queryFn: () => getPlaceDetail(1, placeId, type),
  });

  if (isLoading || data === null) return null;

  return (
    <Modal open={modalOpen} close={closeModal} header="장소 상세" size="large">
      <div className="text-left overflow-auto">
        <div className="flex flex-row gap-2">
          <h1 className="text-2xl font-bold">{data.name}</h1>
          <div>
            {data.like ? (
              <img
                src={likeBtn}
                alt=""
                className="w-[35px] h-[35px] cursor-pointer"
              />
            ) : (
              <img
                src={noLikeBtn}
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
              {data.img1 === null ? null : (
                <div className="slider-img mb-5">
                  <img src={data.img1} alt="" />
                </div>
              )}
              {data.img2 === null ? null : (
                <div className="slider-img mb-5">
                  <img src={data.img2} alt="" />
                </div>
              )}
              {data.img3 === null ? null : (
                <div className="slider-img mb-5">
                  <img src={data.img3} alt="" />
                </div>
              )}
              {data.img4 === null ? null : (
                <div className="slider-img mb-5">
                  <img src={data.img4} alt="" />
                </div>
              )}
              {data.img5 === null ? null : (
                <div className="slider-img mb-5">
                  <img src={data.img5} alt="" />
                </div>
              )}
            </Slider>
          </div>
          <div className="justify-left">
            <div className="flex flex-row gap-2">
              <div className="font-bold text-lg">{data.name}</div>
              <div className="flex flex-row">
                <div>
                  <StarIcon fontSize="small" sx={{ color: "#FF8E9E" }} />
                </div>
                <div className="text-sm mt-[5px] text-lightMain font-bold">
                  {data.score}
                </div>
              </div>
            </div>
            <div className="text-slate-400 text-sm mb-3">{data.address}</div>
            <div className="flex flex-row mb-2 gap-5">
              <div title="운영시간">
                <AccessTimeIcon color="disabled" />
              </div>
              <div className="flex flex-col">
                <div>{data.dayOff}</div>
                <div>{data.openTime}</div>
              </div>
            </div>
            <div className="flex flex-row mb-2 gap-5">
              <div title="상세">
                <ArticleOutlinedIcon color="disabled" />
              </div>
              <div className="flex flex-col">
                <div>{data.detail}</div>
                <div>{data.playground}</div>
                <div>{data.parkingYn}</div>
                <div>{data.smokingYn}</div>
                <div>{data.takoutYn}</div>
                <div>{data.representativeMenu}</div>
              </div>
            </div>
            <div className="flex flex-row mb-2 gap-5">
              <div title="연락처">
                <LocalPhoneIcon color="disabled" />
              </div>
              <div className="flex flex-col">
                <div>{data.reserveInfo}</div>
                <div>{data.phoneNumber}</div>
              </div>
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
  );
}
