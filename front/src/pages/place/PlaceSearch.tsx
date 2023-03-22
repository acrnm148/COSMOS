import React, { useState, useEffect } from "react";
import SearchIcon from "../../assets/place/SearchIcon.png";
import "../../css/placeSearch.css";

import TMap from "../../components/common/TMap";
import ItemList from "../../components/search/ItemList";
import SearchFilter from "../../components/search/SearchFilter";
import SidoList from "../../components/search/SidoList";
import GugunList from "../../components/search/GugunList";

export default function PlaceSearch() {
  // 시/도
  const [sido, setSido] = useState();
  // 구/군
  const [gugun, setGugun] = useState();
  // 검색어
  const [searchWord, setSearchWord] = useState("");

  console.log(sido);

  const handleSearch = (e: any) => {
    setSearchWord(e.target.value);
  };

  // TMap
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
    <div className="text-center w-[90%] max-w-[950px] mt-[50px]">
      <div className="flex flex-row justify-center">
        <SidoList setSido={setSido} />
        {sido === undefined ? (
          <select className="basis-1/6 border-4 border-lightMain opacity-50 rounded-lg rounded-r-none rounded-l-none focus:outline-none">
            <option value="">구/군 선택</option>
          </select>
        ) : (
          <GugunList selectedSidoCode={sido} setGugun={setGugun} />
        )}
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
      <SearchFilter />
      <hr className="my-[3vh]" />
      <TMap state={state} />
      <hr className="mt-[3vh]" />
      <ItemList />
    </div>
  );
}
