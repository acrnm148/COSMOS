import React from "react";
import SearchIcon from "../../assets/place/SearchIcon.png";

export default function SelectRegion() {
  return (
    <div>
      <div className="flex flex-row justify-end">
        <select className="basis-1/4 border-4 border-lightMain opacity-50 rounded-lg rounded-r-none focus:outline-none">
          <option className="options" value="">
            구/군 선택
          </option>
        </select>
        <select className="basis-1/4 border-4 border-lightMain opacity-50 rounded-lg rounded-l-none focus:outline-none">
          <option className="options" value="">
            동 선택
          </option>
        </select>
        <button
          className="flex flex-col justify-center items-center ml-4 bg-lightMain p-2 rounded-xl hover:opacity-80"
          title="검색"
        >
          <img className="w-[32px]" src={SearchIcon} alt="검색" />
        </button>
      </div>
      <div></div>
    </div>
  );
}
