import React, { useState } from "react";
import DefaultImg from "../../../../assets/login/pinkCosmos.png";
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
import Swal from "sweetalert2";

export default function PlaceList() {
  const isDark = useRecoilState(darkMode);
  const userSeq = useRecoilState(userState);
  const sidoState = useRecoilState(selectSido);
  const gugunState = useRecoilState(selectGugun);
  const wordState = useRecoilState(completeWord);
  const categoryState = useRecoilState(selectCategory);

  const [detail, setDetail] = useRecoilState(placeDetail);
  const [modalOpen, setModalOpen] = useState(false);

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

  console.log(data);
  return (
    <>
      <ListCard height={true}>
        {data.places.map((items: any) => {
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
        })}
      </ListCard>
    </>
  );
}
