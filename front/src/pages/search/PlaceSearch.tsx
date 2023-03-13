import React from "react";
import SearchIcon from "../../assets/search/SearchIcon.png";
import "../../css/placeSearch.css";

export default function PlaceSearch() {
  return (
    <div className="text-center w-[80%] max-w-[950px]">
      <h1>장소검색</h1>
      <div className="flex flex-row">
        <select className="basis-1/6 border-4 border-lightMain opacity-50 rounded-lg rounded-r-none focus:outline-none">
          <option className="options" value="">
            시/도
          </option>
          <option className="options" value="">
            구/군
          </option>
          <option className="options" value="">
            동
          </option>
        </select>
        <input
          className="basis-4/6 border-4 border-lightMain opacity-50 rounded-lg rounded-l-none focus:outline-none"
          type="text"
          placeholder="장소명으로 검색"
        />
        <button className="flex flex-col justify-center items-center ml-4 bg-lightMain p-2 rounded-xl">
          <img className="w-[32px]" src={SearchIcon} alt="검색" />
        </button>
      </div>
    </div>
  );
}
