import React, { useState, useEffect } from "react";
import DefaultImg from "../../../../assets/login/pinkCosmos.png";
import ListCard from "../../../common/ListCard";
import PlaceLike from "../items/PlaceLike";
import PlaceModal from "../items/PlaceModal";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
  selectCategory,
  placeDetail,
} from "../../../../recoil/states/RecommendPageState";
import { useQuery } from "react-query";
import { getDateCourse } from "../../../../apis/api/place";

export default function PlaceList() {
  const sido = useRecoilState(selectSido);
  const gugun = useRecoilState(selectGugun);
  const category = useRecoilState(selectCategory);

  const tmp = {
    sido: sido[0].sidoName,
    gugun: gugun[0].gugunName,
    categories: category[0],
    userSeq: 1,
  };

  const [item, setItem] = useState(JSON.stringify(tmp));
  const [detail, setDetail] = useRecoilState(placeDetail);
  const [modalOpen, setModalOpen] = useState(false);

  useEffect(() => {
    setItem(JSON.stringify(tmp));
  }, [category[0]]);

  const openModal = (placeId: number, type: string) => {
    setDetail({ placeId: placeId, type: type });
    setModalOpen(true);
  };

  const closeModal = (e: React.MouseEvent) => {
    e.stopPropagation();
    setModalOpen(false);
  };

  const { data, isLoading } = useQuery({
    queryKey: ["getDateCourse", item],
    queryFn: () => getDateCourse(item),
  });

  if (isLoading || data === undefined) return null;

  return (
    <>
      <ListCard height={true}>
        {data.data.places.map((items: any) => {
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
                  <p
                    className="content-detail mb-4"
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
