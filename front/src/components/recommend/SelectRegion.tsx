import React from "react";
import KoreaMap from "../common/Map/KoreaMap";

export default function SelectRegion() {
  const sido = [
    {
      sidoCode: 0,
      name: "서울",
    },
    {
      sidoCode: 1,
      name: "부산",
    },
    {
      sidoCode: 2,
      name: "대구",
    },
    {
      sidoCode: 3,
      name: "인천",
    },
    {
      sidoCode: 4,
      name: "광주",
    },
    {
      sidoCode: 5,
      name: "대전",
    },
    {
      sidoCode: 6,
      name: "울산",
    },
    {
      sidoCode: 7,
      name: "경기",
    },
    {
      sidoCode: 8,
      name: "강원",
    },
    {
      sidoCode: 9,
      name: "충청북도",
    },
    {
      sidoCode: 10,
      name: "충청남도",
    },
    {
      sidoCode: 11,
      name: "전라북도",
    },
    {
      sidoCode: 12,
      name: "전라남도",
    },
    {
      sidoCode: 13,
      name: "경상북도",
    },
    {
      sidoCode: 14,
      name: "경상남도",
    },
    {
      sidoCode: 15,
      name: "제주도",
    },
    {
      sidoCode: 16,
      name: "세종",
    },
  ];
  return (
    <div>
      <div className="flex flex-row justify-end">
        <select className="h-[40px] border-4 border-lightMain opacity-50 rounded-lg rounded-r-none focus:outline-none">
          <option className="options" value="">
            구/군 선택
          </option>
        </select>
        <select className="h-[40px] border-4 border-lightMain opacity-50 rounded-lg rounded-l-none focus:outline-none">
          <option className="options" value="">
            동 선택
          </option>
        </select>
      </div>
      <div>
        <KoreaMap list={sido} />
      </div>
    </div>
  );
}
