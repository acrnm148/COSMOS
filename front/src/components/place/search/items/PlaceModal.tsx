import React, { useState, useEffect } from "react";
import Modal from "../../../common/Modal";
import Slider from "react-slick";
import AccessTimeIcon from "@mui/icons-material/AccessTime";
import ArticleOutlinedIcon from "@mui/icons-material/ArticleOutlined";
import LocalPhoneIcon from "@mui/icons-material/LocalPhone";
import DirectionsCarIcon from "@mui/icons-material/DirectionsCar";
import StarIcon from "@mui/icons-material/Star";
import PetsIcon from "@mui/icons-material/Pets";
import TourIcon from "@mui/icons-material/Tour";
import CalendarMonthIcon from "@mui/icons-material/CalendarMonth";
import RestaurantMenuIcon from "@mui/icons-material/RestaurantMenu";
import PaidIcon from "@mui/icons-material/Paid";
import LikeImg from "../../../../assets/like.png";
import NoLikeImg from "../../../../assets/no-like.png";
import { useRecoilState } from "recoil";
import { placeDetail } from "../../../../recoil/states/SearchPageState";
import { useQuery } from "react-query";
import { getPlaceDetail } from "../../../../apis/api/place";

export default function PlaceModal({ modalOpen, closeModal }: any) {
  const detail = useRecoilState(placeDetail);
  const [items, setItems] = useState(detail[0]);
  const [review, setReview] = useState(false);

  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
  };

  useEffect(() => {
    setItems(detail[0]);
  }, [detail[0].placeId]);

  const { data, isLoading } = useQuery({
    queryKey: ["getPlaceDetail", items.placeId],
    queryFn: () => getPlaceDetail(1, items.placeId, items.type),
  });

  if (isLoading) return null;
  return (
    <Modal open={modalOpen} close={closeModal} header="장소 상세" size="large">
      <div className="text-left overflow-auto">
        <div className="flex flex-row gap-2">
          <h1 className="text-2xl font-bold">{data.name}</h1>
          <div>
            {data.like ? (
              <img
                src={LikeImg}
                alt=""
                className="w-[35px] h-[35px] cursor-pointer"
              />
            ) : (
              <img
                src={NoLikeImg}
                alt=""
                className="w-[35px] h-[35px] cursor-pointer"
              />
            )}
          </div>
        </div>
        <hr className="divide-slate-300 m-2" />
        <div className="h-[300px] overflow-auto">
          <div className="m-auto w-[90%]">
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
            {/* 공통 */}
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
              <div title="상세">
                <ArticleOutlinedIcon color="disabled" />
              </div>
              <div className="flex flex-col">
                <div>{data.detail}</div>
              </div>
            </div>
            <div className="flex flex-row mb-2 gap-5">
              <div title="연락처">
                <LocalPhoneIcon color="disabled" />
              </div>
              <div className="flex flex-col">
                <div>{data.phoneNumber}</div>
              </div>
            </div>
            <div className="flex flex-row mb-2 gap-5">
              <div title="주차 가능 여부">
                <DirectionsCarIcon color="disabled" />
              </div>
              <div className="flex flex-col">
                <div>{data.parkingYn}</div>
              </div>
            </div>
            {items.type === "tour" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="반려동물 출입 가능 여부">
                    <PetsIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.petYn}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="프로그램 정보">
                    <TourIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.program}</div>
                  </div>
                </div>
              </>
            ) : items.type === "festival" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="시작/종료일">
                    <CalendarMonthIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.startDate}</div>
                    <div>{data.endDate}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="이용요금">
                    <PaidIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.price}</div>
                  </div>
                </div>
              </>
            ) : items.type === "accommodation" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="체크인/체크아웃 시간">
                    <AccessTimeIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.checkIn}</div>
                    <div>{data.checkOut}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="환불 규정">
                    <PaidIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.refund}</div>
                  </div>
                </div>
              </>
            ) : items.type === "restaurant" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="영업시간">
                    <AccessTimeIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.dayOff}</div>
                    <div>{data.openTime}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="대표메뉴">
                    <RestaurantMenuIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.representativeMenu}</div>
                  </div>
                </div>
              </>
            ) : items.type === "cafe" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="영업시간">
                    <AccessTimeIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.dayOff}</div>
                    <div>{data.openTime}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="대표메뉴">
                    <RestaurantMenuIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.representativeMenu}</div>
                  </div>
                </div>
              </>
            ) : items.type === "shopping" ? (
              <div className="flex flex-row mb-2 gap-5">
                <div title="영업시간">
                  <AccessTimeIcon color="disabled" />
                </div>
                <div className="flex flex-col">
                  <div>{data.dayOff}</div>
                  <div>{data.openTime}</div>
                </div>
              </div>
            ) : items.type === "leisure" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="영업시간">
                    <AccessTimeIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.dayOff}</div>
                    <div>{data.openTime}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="반려동물 출입 가능 여부">
                    <PetsIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.petYn}</div>
                  </div>
                </div>
              </>
            ) : items.type === "culture" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="휴무일">
                    <AccessTimeIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.dayOff}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="반려동물 출입 가능 여부">
                    <PetsIcon color="disabled" />
                  </div>
                  <div className="flex flex-col">
                    <div>{data.petYn}</div>
                  </div>
                </div>
              </>
            ) : null}
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
