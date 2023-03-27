import React, { useState, useEffect } from "react";
import ListCard from "../common/ListCard";
import DefaultImg from "../../assets/login/pinkCosmos.png";
import PlaceLike from "./PlaceLike";

export default function SearchPlaceItem({ data, setState, setMarkers }: any) {
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

  useEffect(() => {
    setState({
      center: {
        lat: data.midLatitude,
        lng: data.midLongitude,
      },
    });
  }, [data.midLatitude, data.midLongitude]);

  useEffect(() => {
    const markers = [{}];
    data.places.map((item: any) => {
      markers.push({
        center: {
          placeId: item.placeId,
          lat: item.latitude,
          lng: item.longitude,
        },
      });
    });
    markers.splice(0, 1);
    setMarkers(markers);
  }, [data]);
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
