import React, { useState, useEffect } from "react";
import SearchIcon from "../../assets/place/SearchIcon.png";
import "../../css/placeSearch.css";
import SearchWait from "../../assets/place/search-wait.png";
import TMap from "../../components/common/TMap";
import ItemList from "../../components/search/ItemList";
import SearchFilter from "../../components/search/SearchFilter";
import SidoList from "../../components/search/SidoList";
import GugunList from "../../components/search/GugunList";
import SearchWordList from "../../components/search/SearchWordList";

export default function PlaceSearch() {
  // 시/도
  const [sido, setSido] = useState();
  // 구/군
  const [gugun, setGugun] = useState();
  // 검색어
  const [searchWord, setSearchWord] = useState("");

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
  useEffect(() => {}, [searchWord]);
  return (
    <div className="text-center w-[90%] mt-[50px]">
      <div className="flex flex-row gap-2 justify-center mb-5">
        <SidoList setSido={setSido} />
        {sido === undefined ? (
          <select className="h-12 w-[30%] border-4 border-lightMain opacity-50 rounded-lg focus:outline-none">
            <option value="">구/군 선택</option>
          </select>
        ) : (
          <GugunList selectedSidoCode={sido} setGugun={setGugun} />
        )}
      </div>
      <div className="flex flex-row justify-center">
        <input
          className="w-[80%] h-12 border-4 border-lightMain opacity-50 rounded-lg focus:outline-none"
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
      {sido !== undefined && gugun !== undefined ? <SearchFilter /> : null}
      <div className="w-[80%] max-h-[20vh] m-auto">
        {searchWord === null ? null : (
          <SearchWordList searchWord={searchWord} />
        )}
      </div>
      <hr className="my-[3vh]" />
      {sido !== undefined && gugun !== undefined ? (
        <TMap state={state} />
      ) : (
        <div className="w-full h-[50vh]">
          <img className="h-full m-auto rounded-lg" src={SearchWait} />
        </div>
      )}
      <hr className="mt-[3vh]" />
      <ItemList />
    </div>
  );
}
