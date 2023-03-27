import React, { useState, useEffect } from "react";
import SearchIcon from "../../assets/place/SearchIcon.png";
import "../../css/placeSearch.css";
import SearchWait from "../../assets/place/search-wait.png";
import TMap from "../../components/common/TMap";
import ListItems from "../../components/search/ListItems";
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
  // 필터
  const [filter, setFilter] = useState("");
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

  // 자동완성 API
  useEffect(() => {
    setClickBg(false);
  }, [searchWord]);

  return (
    <div className="text-center w-[90%] mt-[50px] h-screen mb-10">
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
          {searchWord === "" ? null : (
            <SearchWordList
              searchWord={searchWord}
              setSearchWord={setSearchWord}
              setItems={setItems}
            />
          )}
        </div>
      </div>

      {sido !== undefined && gugun !== undefined ? (
        <SearchFilter filter={filter} setFilter={setFilter} />
      ) : null}
      <hr className="my-[3vh]" />
      {state.center.lat !== 0 && state.center.lng !== 0 ? (
        <TMap state={state} />
      ) : (
        <div className="w-full h-[50vh]">
          <img className="h-full m-auto rounded-lg" src={SearchWait} />
        </div>
      )}
      <hr className="mt-[3vh]" />
      <ListItems
        items={items}
        setState={setState}
        sido={sido}
        gugun={gugun}
        text={searchWord}
        filter={filter}
      />
    </div>
  );
}
