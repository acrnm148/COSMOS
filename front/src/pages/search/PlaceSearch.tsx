import React, { useState, useEffect } from "react";
import SearchIcon from "../../assets/search/SearchIcon.png";
import "../../css/placeSearch.css";

import TMap from "../../components/common/TMap";
import ItemList from "../../components/search/ItemList";

export default function PlaceSearch() {
  const [searchWord, setSearchWord] = useState("");

  const handleSearch = (e: any) => {
    setSearchWord(e.target.value);
  };

  const [state, setState] = useState({
    center: {
      lat: 0,
      lng: 0,
    },
  });

  useEffect(() => {
    // 사용자의 현재 위치 받아옴
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        setState((prev) => ({
          ...prev,
          center: {
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          },
          isLoading: false,
        }));
      });
    } else {
      setState((prev) => ({
        ...prev,
        errMsg: "현재 위치 정보를 받아올 수 없습니다",
        isLoading: false,
      }));
    }
  }, []);

  // 자동완성 API

  return (
    <div className="text-center w-[90%] max-w-[950px]">
      <h1>장소검색</h1>
      <div className="flex flex-row justify-center">
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
          onChange={handleSearch}
        />
        <button
          className="flex flex-col justify-center items-center ml-4 bg-lightMain p-2 rounded-xl hover:opacity-80"
          title="검색"
        >
          <img className="w-[32px]" src={SearchIcon} alt="검색" />
        </button>
      </div>
      <hr className="my-[3vh]" />
      <TMap state={state} />
      <hr className="mt-[3vh]" />
      <ItemList />
    </div>
  );
}
