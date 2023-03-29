import React, { useState } from "react";
import DefaultImg from "../../../assets/login/pinkCosmos.png";
import ListCard from "../../common/ListCard";
import PlaceLike from "../items/PlaceLike";
import PlaceModal from "../items/PlaceModal";
import { useRecoilState } from "recoil";
import { placeDetail } from "../../../recoil/states/SearchPageState";

export default function PlaceList({ data }: any) {
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

  return (
    <>
      <ListCard height={true}>
        {data.map((items: any) => {
          return (
            <div key={items.placeId}>
              <div
                className="flex flex-row relative card-content"
                onClick={() => {
                  openModal(items.placeId, items.type);
                }}
              >
                <div className="flex justify-center my-auto basis-3/12">
                  <img
                    src={
                      items.thumbNailUrl === null
                        ? DefaultImg
                        : items.thumbNailUrl
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
                  <div>{items.detail}</div>
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
