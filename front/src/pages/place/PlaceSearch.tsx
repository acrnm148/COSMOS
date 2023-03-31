import React, { useState, useEffect } from "react";
import SearchWait from "../../assets/place/search-wait.png";
import SearchWord from "../../components/place/search/SearchWord";
import GugunList from "../../components/place/search/lists/GugunList";
import SidoList from "../../components/place/search/lists/SidoList";
import TMap from "../../components/common/TMap";
import PlaceList from "../../components/place/search/lists/PlaceList";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
  completeWord,
  selectCategory,
} from "../../recoil/states/SearchPageState";
import SearchFilter from "../../components/place/search/SearchFilter";

export default function PlaceSearch() {
  const sidoState = useRecoilState(selectSido);
  const gugunState = useRecoilState(selectGugun);
  const wordState = useRecoilState(completeWord);
  const categoryState = useRecoilState(selectCategory);

  return (
    <div className="w-[90%] text-center">
      <div className="flex flex-row justify-center mt-10">
        <div className="basis-1/3 h-14">
          <SidoList />
        </div>
        <div className="basis-1/3 h-14">
          <GugunList />
        </div>
      </div>
      <div className="mt-5">
        <SearchWord />
      </div>
      {sidoState[0].sidoName !== "" && gugunState[0].gugunName !== "" ? (
        <SearchFilter />
      ) : null}
      <hr className="my-5 bg-slate-700" />
      <div className="mt-5">
        {(sidoState[0].sidoName !== "" && gugunState[0].gugunName !== "") ||
        wordState[0] !== "" ||
        categoryState[0] !== "" ? (
          <TMap />
        ) : (
          <div className="w-full h-[50vh]">
            <img className="h-full m-auto rounded-lg" src={SearchWait} />
          </div>
        )}
      </div>
      {/* <PlaceList /> */}
    </div>
  );
}
