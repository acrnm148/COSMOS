import React from "react";
import SearchIcon from "../../assets/search/SearchIcon.png";

export default function PlaceSearch() {
  return (
    <div className="text-center">
      <h1>장소검색</h1>
      <div>
        <select name="" id="">
          <option value="">시/도</option>
          <option value="">구/군</option>
          <option value="">동</option>
        </select>
        <div className="bg-black">
          <img src={SearchIcon} alt="" />
        </div>
      </div>
    </div>
  );
}
