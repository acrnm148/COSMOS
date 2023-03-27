import React, { useState } from "react";
import ListCard from "../common/ListCard";

import DefaultImg from "../../assets/login/pinkCosmos.png";
import Swal from "sweetalert2";
import PlaceLike from "./PlaceLike";

export default function SearchPlaceItem({ data, setState }: any) {
  const [modalOpen, setModalOpen] = useState(false);
  const [placeId, setPlaceId] = useState();
  const [type, setType] = useState();

  const openModal = () => {
    setModalOpen(true);
  };

  const closeModal = (e: React.MouseEvent) => {
    e.stopPropagation();
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

    data.places.like
      ? Toast.fire({
          title: "해제되었습니다.",
          icon: "success",
        })
      : Toast.fire({
          title: "등록되었습니다.",
          icon: "success",
        });
  };

  return (
    <>
      <ListCard>
        {data === undefined
          ? null
          : data.places.map((items: any) => {
              return (
                <div key={items.placeId}>
                  <div
                    className="flex flex-row relative card-content"
                    onClick={() => {
                      setPlaceId(items.placeId);
                      setType(items.type);
                      openModal();
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
                    </div>

                    <PlaceLike
                      items={items}
                      placeId={placeId}
                      type={type}
                      setState={setState}
                      modalOpen={modalOpen}
                      closeModal={closeModal}
                      setModalOpen={setModalOpen}
                    />
                  </div>
                </div>
              );
            })}
      </ListCard>
    </>
  );
}
