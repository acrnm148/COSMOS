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

import { useRecoilState } from "recoil";
import { clickBackground } from "../../recoil/states/SearchPageState";

export default function PlaceSearch() {
  // 시/도
  const [sido, setSido] = useState();
  // 구/군
  const [gugun, setGugun] = useState();
  // 검색어
  const [searchWord, setSearchWord] = useState("");
  // 아이템리스트
  const [items, setItems] = useState();
  // 검색어 자동완성 박스
  const [clickBg, setClickBg] = useRecoilState(clickBackground);

  const handleSearch = (e: any) => {
    setSearchWord(e.target.value);
  };

  // 검색창 클릭하면 나오게, 그 외 다른거 클릭하면 안나오게
  const handleSearchInput = (e: any) => {
    e.stopPropagation();
    setClickBg(false);
  };

  // TMap
  const [state, setState] = useState({
    center: {
      lat: 0,
      lng: 0,
    },
  });
  // useEffect(() => {
  //   // 사용자의 현재 위치 받아옴
  //   if (navigator.geolocation) {
  //     navigator.geolocation.getCurrentPosition((position) => {
  //       setState((prev) => ({
  //         ...prev,
  //         center: {
  //           lat: position.coords.latitude,
  //           lng: position.coords.longitude,
  //         },
  //         isLoading: false,
  //       }));
  //     });
  //   } else {
  //     setState((prev) => ({
  //       ...prev,
  //       errMsg: "현재 위치 정보를 받아올 수 없습니다",
  //       isLoading: false,
  //     }));
  //   }
  // }, []);

  // 자동완성 API
  useEffect(() => {
    setClickBg(false);
  }, [searchWord]);
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
      <div className="flex flex-col justify-center">
        <input
          className="w-[80%] m-auto h-12 border-4 border-lightMain opacity-50 rounded-lg focus:outline-none"
          type="text"
          placeholder="장소명으로 검색"
          value={searchWord}
          onChange={handleSearch}
          onClick={handleSearchInput}
        />
        <div className="w-[80%] m-auto relative">
          {searchWord === null ? null : (
            <SearchWordList
              searchWord={searchWord}
              setSearchWord={setSearchWord}
              setItems={setItems}
            />
          )}
        </div>
      </div>

      {sido !== undefined && gugun !== undefined ? <SearchFilter /> : null}
      <hr className="my-[3vh]" />
      {state.center.lat !== 0 && state.center.lng !== 0 ? (
        <TMap state={state} />
      ) : (
        <div className="w-full h-[50vh]">
          <img className="h-full m-auto rounded-lg" src={SearchWait} />
        </div>
      )}
      <hr className="mt-[3vh]" />
      <ItemList items={items} setState={setState} />
    </div>
  );
}
