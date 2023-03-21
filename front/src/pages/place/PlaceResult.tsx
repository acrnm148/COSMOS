import React, { useState, useRef, useEffect } from "react";
import { PlaceItem } from "../../components/common/PlaceItem";
import TMapResult from "../../components/common/TMapResult";

import 파주출판단지 from "../../assets/schedule/파주출판단지.png";
import 녹두 from "../../assets/schedule/녹두.png";
import 베지앙 from "../../assets/schedule/베지앙.png";

interface Place {
  idx: number;
  name: string;
  imgUrl: string;
  category: string;
  location: string;
  date: string;
}

const testPlace: Place[] = [
  {
    idx: 0,
    name: "파주 출판단지",
    imgUrl: 파주출판단지,
    category: "관광",
    location: "경기도",
    date: "2023년 2월 28일",
  },
  {
    idx: 1,
    name: "베지앙",
    imgUrl: 베지앙,
    category: "카페",
    location: "경기도",
    date: "2023년 2월 28일",
  },
  {
    idx: 2,
    name: "녹두",
    imgUrl: 녹두,
    category: "음식",
    location: "경기도",
    date: "2023년 2월 28일",
  },
];

export default function PlaceResult() {
  const state = {
    center: {
      lat: 37.566481622437934,
      lng: 126.98502302169841,
    },
  };
  const marker = [
    {
      placeId: 0,
      lat: 37.566481622437934,
      lng: 126.98502302169841,
    },
    {
      placeId: 1,
      lat: 37.567481622437934,
      lng: 126.98602302169841,
    },
    {
      placeId: 2,
      lat: 37.567381622437934,
      lng: 126.98502302169841,
    },
  ];
  const [places, setPlaces] = useState<Place[]>([]);

  useEffect(() => {
    setPlaces([...testPlace]);
  }, []);

  // 드래그앤드랍
  const draggingIdx = useRef<null | number>(null);
  const draggingOverIdx = useRef<null | number>(null);
  // 드래그 시작 (아이템 확인)
  const onDragStart = (e: any, idx: number) => {
    draggingIdx.current = idx;
    e.target.classList.add("grabbing");
  };
  // 드래그 놓았을 때 변화한 리스트 적용
  const onDragEnter = (e: any, idx: number) => {
    draggingOverIdx.current = idx;
    const copyList = [...places];
    const dragItemContent = copyList[draggingIdx.current!];
    copyList.splice(draggingIdx.current!, 1);
    copyList.splice(draggingOverIdx.current, 0, dragItemContent);
    draggingIdx.current = draggingOverIdx.current;
    draggingOverIdx.current = null;
    setPlaces(copyList);
    e.target.classList.remove("grabbing");
  };
  // 드래그 이동 종료
  const onDragEnd = (e: any) => {
    e.target.classList.remove("grabbing");
  };
  // 드래그 하는 중 아이템이 오버랩 된 상태
  const onDragOver = (e: { preventDefault: () => void }) => {
    e.preventDefault();
  };

  return (
    <div className="text-center w-[90%] max-w-[950px] mt-[20px] mb-[100px]">
      <TMapResult state={state} marker={marker} />
      <div className="w-full justify-center">
        {places?.map((place, key) => {
          return (
            <div
              // onClick={} 페이지이동
              onDragStart={(e) => onDragStart(e, key)}
              onDragEnter={(e) => onDragEnter(e, key)}
              onDragOver={onDragOver}
              onDragEnd={onDragEnd}
              draggable
              key={key}
            >
              <PlaceItem place={place} key={key} />
            </div>
          );
        })}
      </div>
    </div>
  );
}
