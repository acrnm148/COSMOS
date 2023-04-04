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
import DarkLikeImg from "../../../../assets/dark-like.png";
import DarkNoLikeImg from "../../../../assets/dark-no-like.png";
import DefaultImg from "../../../../assets/login/pinkCosmos.png";
import { useRecoilState } from "recoil";
import { placeDetail } from "../../../../recoil/states/RecommendPageState";
import { userState, darkMode } from "../../../../recoil/states/UserState";
import { useQuery } from "react-query";
import { getPlaceDetail } from "../../../../apis/api/place";
import "../../../../css/listItem.css";
import ReviewAll from "../../../common/ReviewAll";
import ReviewOurs from "../../../common/ReviewOurs";

export default function PlaceModal({ modalOpen, closeModal }: any) {
  const isDark = useRecoilState(darkMode);
  const userSeq = useRecoilState(userState);
  const detail = useRecoilState(placeDetail);
  const [items, setItems] = useState(detail[0]);
  const [review, setReview] = useState(false);

  const settings = {
    dots: true,
    arrows: false,
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
      <div className="text-left">
        <div className="flex flex-row gap-2">
          <h1 className="text-2xl font-bold dark:text-white">{data.name}</h1>
          <div>
            {data.like ? (
              isDark ? (
                <img
                  src={DarkLikeImg}
                  alt=""
                  className="w-[35px] h-[35px] cursor-pointer"
                />
              ) : (
                <img
                  src={LikeImg}
                  alt=""
                  className="w-[35px] h-[35px] cursor-pointer"
                />
              )
            ) : isDark ? (
              <img
                src={DarkNoLikeImg}
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
        <div className="h-full overflow-auto">
          <div className="m-auto w-[80%]">
            <Slider {...settings}>
              {data.thumbNailUrl === "" ? (
                <div className="slider-img mb-5">
                  <img src={DefaultImg} alt="" />
                </div>
              ) : (
                <div className="slider-img mb-5">
                  <img src={data.thumbNailUrl} alt="" />
                </div>
              )}
              {data.img1 === "" ? null : (
                <div className="slider-img mb-5">
                  <img src={data.img1} alt="" />
                </div>
              )}
              {data.img2 === "" ? null : (
                <div className="slider-img mb-5">
                  <img src={data.img2} alt="" />
                </div>
              )}
              {data.img3 === "" ? null : (
                <div className="slider-img mb-5">
                  <img src={data.img3} alt="" />
                </div>
              )}
              {data.img4 === "" ? null : (
                <div className="slider-img mb-5">
                  <img src={data.img4} alt="" />
                </div>
              )}
              {data.img5 === "" ? null : (
                <div className="slider-img mb-5">
                  <img src={data.img5} alt="" />
                </div>
              )}
            </Slider>
          </div>
          <div className="justify-left">
            {/* 공통 */}
            <div className="flex flex-row gap-2">
              <div className="font-bold text-lg dark:text-white">
                {data.name}
              </div>
              <div className="flex flex-row">
                <div>
                  <StarIcon
                    fontSize="small"
                    sx={isDark ? { color: "#9C4395" } : { color: "#FF8E9E" }}
                  />
                </div>
                <div className="text-sm mt-[5px] text-lightMain font-bold dark:text-darkMain2">
                  {data.score}
                </div>
              </div>
            </div>
            <div className="text-slate-400 text-sm mb-3 dark:text-white dark:opacity-30">
              {data.address}
            </div>
            <div className="flex flex-row mb-2 gap-5">
              <div title="상세">
                <ArticleOutlinedIcon
                  sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                />
              </div>
              <div className="flex flex-col dark:text-white">
                <div dangerouslySetInnerHTML={{ __html: data.detail }}></div>
              </div>
            </div>
            <div className="flex flex-row mb-2 gap-5">
              <div title="연락처">
                <LocalPhoneIcon
                  sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                />
              </div>
              <div className="flex flex-col">
                <div>{data.phoneNumber}</div>
              </div>
            </div>
            <div className="flex flex-row mb-2 gap-5">
              <div title="주차 가능 여부">
                <DirectionsCarIcon
                  sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                />
              </div>
              <div className="flex flex-col dark:text-white">
                <div>{data.parkingYn}</div>
              </div>
            </div>
            {items.type === "tour" ? (
              <>
                <div className="flex flex-row mb-2 gap-5 dark:text-white">
                  <div title="반려동물 출입 가능 여부">
                    <PetsIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col dark:text-white">
                    <div>{data.petYn}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5 dark:text-white">
                  <div title="프로그램 정보">
                    <TourIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col dark:text-white">
                    <div>{data.program}</div>
                  </div>
                </div>
              </>
            ) : items.type === "festival" ? (
              <>
                <div className="flex flex-row mb-2 gap-5 dark:text-white">
                  <div title="시작/종료일">
                    <CalendarMonthIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">{data.startDate}</div>
                    <div className="dark:text-white">{data.endDate}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5 dark:text-white">
                  <div title="이용요금">
                    <PaidIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col dark:text-white">
                    <div>{data.price}</div>
                  </div>
                </div>
              </>
            ) : items.type === "accommodation" ? (
              <>
                <div className="flex flex-row mb-2 gap-5 dark:text-white">
                  <div title="체크인/체크아웃 시간">
                    <AccessTimeIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">{data.checkIn}</div>
                    <div className="dark:text-white">{data.checkOut}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="환불 규정">
                    <PaidIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">{data.refund}</div>
                  </div>
                </div>
              </>
            ) : items.type === "restaurant" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="영업시간">
                    <AccessTimeIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">{data.dayOff}</div>
                    <div className="dark:text-white">{data.openTime}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="대표메뉴">
                    <RestaurantMenuIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">
                      {data.representativeMenu}
                    </div>
                  </div>
                </div>
              </>
            ) : items.type === "cafe" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="영업시간">
                    <AccessTimeIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">{data.dayOff}</div>
                    <div className="dark:text-white">{data.openTime}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="대표메뉴">
                    <RestaurantMenuIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">
                      {data.representativeMenu}
                    </div>
                  </div>
                </div>
              </>
            ) : items.type === "shopping" ? (
              <div className="flex flex-row mb-2 gap-5">
                <div title="영업시간">
                  <AccessTimeIcon
                    sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                  />
                </div>
                <div className="flex flex-col">
                  <div className="dark:text-white">{data.dayOff}</div>
                  <div className="dark:text-white">{data.openTime}</div>
                </div>
              </div>
            ) : items.type === "leisure" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="영업시간">
                    <AccessTimeIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">{data.dayOff}</div>
                    <div className="dark:text-white">{data.openTime}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="반려동물 출입 가능 여부">
                    <PetsIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">{data.petYn}</div>
                  </div>
                </div>
              </>
            ) : items.type === "culture" ? (
              <>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="휴무일">
                    <AccessTimeIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">{data.dayOff}</div>
                  </div>
                </div>
                <div className="flex flex-row mb-2 gap-5">
                  <div title="반려동물 출입 가능 여부">
                    <PetsIcon
                      sx={isDark ? { color: "#9C4395" } : { color: "disabled" }}
                    />
                  </div>
                  <div className="flex flex-col">
                    <div className="dark:text-white">{data.petYn}</div>
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
                    : "font-bold text-sm dark:text-darkMain"
                }
                onClick={() => setReview((cur) => !cur)}
              >
                ALL
              </button>
              <button
                className={
                  !review
                    ? "font-bold text-sm text-slate-300"
                    : "font-bold text-sm dark:text-darkMain"
                }
                onClick={() => setReview((cur) => !cur)}
              >
                CO-REVIEW
              </button>
            </div>
            {!review ? (
              <div className="mt-2 mb-5">
                <ReviewAll placeId={items.placeId} />
              </div>
            ) : (
              <div className="mt-2 mb-5">
                <ReviewOurs placeId={data.placeId} />
              </div>
            )}
          </div>
        </div>
      </div>
    </Modal>
  );
}
