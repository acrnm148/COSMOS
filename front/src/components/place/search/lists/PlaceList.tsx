import React, { useState, useEffect } from "react";
import DarkDefaultImg from "../../../../assets/login/darkCosmos.png";
import ListCard from "../../../common/ListCard";
import PlaceLike from "../items/PlaceLike";
import PlaceModal from "../items/PlaceModal";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
  completeWord,
  selectCategory,
  mapCenter,
  mapMarkers,
  placeDetail,
} from "../../../../recoil/states/SearchPageState";
import { userState, darkMode } from "../../../../recoil/states/UserState";
import { useQuery } from "react-query";
import { getPlacesWithConditions } from "../../../../apis/api/place";

export default function PlaceList({ offset, setOffset }: any) {
  const isDark = useRecoilState(darkMode)[0];
  const userSeq = useRecoilState(userState);
  const sidoState = useRecoilState(selectSido);
  const gugunState = useRecoilState(selectGugun);
  const wordState = useRecoilState(completeWord);
  const categoryState = useRecoilState(selectCategory);

  const [detail, setDetail] = useRecoilState(placeDetail);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    setOffset(0);
  }, [sidoState[0].sidoCode, gugunState[0].gugunCode]);
  const openModal = (placeId: number, type: string) => {
    setDetail({ placeId: placeId, type: type });
    setModalOpen(true);
  };

  const closeModal = (e: React.MouseEvent) => {
    e.stopPropagation();
    setModalOpen(false);
  };

  const { data, isLoading } = useQuery({
    queryKey: [
      "getPlacesWithConditions",
      sidoState[0].sidoName,
      gugunState[0].gugunName,
      wordState[0],
      categoryState[0],
    ],
    queryFn: () =>
      getPlacesWithConditions(
        userSeq[0].seq,
        sidoState[0].sidoName,
        gugunState[0].gugunName,
        wordState[0],
        categoryState[0]
      ),
  });

  if (isLoading || data === null) return null;

  return (
    <>
      <ListCard height={true}>
        {data.places.map((items: any, index: number) => {
          if (index > offset * 20 && index < offset * 20 + 20) {
            return (
              <div key={items.placeId}>
                <div
                  className="flex flex-row relative card-content dark:bg-darkBackground2 dark:hover:bg-[#676767]"
                  onClick={() => {
                    openModal(items.placeId, items.type);
                  }}
                >
                  <div className="flex justify-center my-auto basis-3/12">
                    <img
                      src={
                        items.thumbNailUrl ===
                        "https://cosmoss3.s3.ap-northeast-2.amazonaws.com/dc27450e-9c2c-4ae0-b2d2-d0b106e92288.png"
                          ? isDark
                            ? DarkDefaultImg
                            : items.thumbNailUrl
                          : items.thumbNailUrl
                      }
                      alt=""
                      className="w-[15vw] h-[15vw] max-w-[200px] max-h-[150px] rounded-lg"
                    />
                  </div>
                  <div className="flex flex-col column-3 basis-8/12 text-left">
                    <div className="font-bold text-lg dark:text-white">
                      {items.name}
                    </div>
                    <div className="font-thin text-slate-400 text-sm dark:text-white dark:opacity-30">
                      {items.address}
                    </div>
                    <p
                      className="content-detail mb-4 dark:text-white"
                      dangerouslySetInnerHTML={{ __html: items.detail }}
                    ></p>
                  </div>
                  <PlaceLike like={items.like} placeId={items.placeId} />
                </div>
                <PlaceModal modalOpen={modalOpen} closeModal={closeModal} />
              </div>
            );
          }
        })}
        <div className="flex flex-row relative pb-5">
          <span
            className={
              offset === 0
                ? "absolute left-3 font-bold text-slate-400 cursor-default"
                : "absolute left-3 font-bold text-slate-400 hover:text-darkMain cursor-pointer"
            }
            onClick={(cur) =>
              setOffset((cur: any) => (cur === 0 ? cur : cur - 1))
            }
          >
            PREV
          </span>
          <span
            className={
              data.places.length - offset * 20 <= 0
                ? "absolute right-3 font-bold text-slate-400 cursor-default"
                : "absolute right-3 font-bold text-slate-400 hover:text-darkMain cursor-pointer"
            }
            onClick={() =>
              data.places.length - offset * 20 <= 0
                ? null
                : setOffset((cur: any) => cur + 1)
            }
          >
            NEXT
          </span>
        </div>
      </ListCard>
    </>
  );
}
