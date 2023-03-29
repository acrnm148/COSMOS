import React, { useState } from "react";
import { useRecoilState, useSetRecoilState } from "recoil";
import {
  clickBackground,
  searchWord,
} from "../../../recoil/states/SearchPageState";
import SearchWordList from "./lists/SearchWordList";

export default function SearchWordItem() {
  // const setSearchWord = useSetRecoilState(searchWord); // 검색어 Recoil State 수정
  const [search, setSearch] = useRecoilState(searchWord); // 검색어
  const [clickBg, setClickBg] = useRecoilState(clickBackground); // 검색창 클릭

  // 입력값에 따른 검색어
  const handleSearchWord = (e: any) => {
    setSearch(e.target.value);
    // setSearchWord(e.target.value);
  };

  // 검색창 클릭
  const handleSearchInput = (e: any) => {
    e.stopPropagation();
    setClickBg(false);
  };

  return (
    <>
      <input
        className="w-full h-14 m-auto border-[4px] border-lightMain opacity-50 rounded-lg outline-none"
        placeholder="장소명으로 검색"
        value={search}
        onChange={handleSearchWord}
        onClick={handleSearchInput}
      />
      {/* 검색어 자동완성 */}
      <div className="relative">
        {search === "" ? null : <SearchWordList />}
      </div>
    </>
  );
}
