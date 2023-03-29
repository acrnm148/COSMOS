import React, { useState } from "react";
import LightBigData from "../../assets/place/light-bigdata.gif";
import SelecctCategory from "../../components/place/recommend/SelectCategory";
import SelectRegion from "../../components/place/recommend/SelectRegion";
export default function PlaceRecommend() {
  const [selects, setSelects] = useState([false, false]);

  return (
    <div className="text-center w-[90%] max-w-[950px] mt-[50px]">
      <div className="flex flex-row justify-center gap-[20vw]">
        {selects[0] ? (
          <div
            className="w-[200px] p-3 rounded-lg font-bold bg-lightMain text-white cursor-pointer"
            onClick={() => setSelects([true, false])}
          >
            지역 선택
          </div>
        ) : (
          <div
            className="w-[200px] p-3 rounded-lg font-bold bg-lightMain2 text-white cursor-pointer"
            onClick={() => setSelects([true, false])}
          >
            지역 선택
          </div>
        )}
        {selects[1] ? (
          <div
            className="w-[200px] p-3 rounded-lg font-bold bg-lightMain text-white cursor-pointer"
            onClick={() => setSelects([false, true])}
          >
            카테고리 선택
          </div>
        ) : (
          <div
            className="w-[200px] p-3 rounded-lg font-bold bg-lightMain2 text-white cursor-pointer"
            onClick={() => setSelects([false, true])}
          >
            카테고리 선택
          </div>
        )}
      </div>
      <hr className="my-[3vh]" />
      <div className="flex justify-center w-[100%]">
        {!selects[0] && !selects[1] ? (
          <img src={LightBigData} className="w-[100%] max-w-[600px]" />
        ) : null}
      </div>
      {selects[0] ? (
        <SelectRegion setSelects={setSelects} />
      ) : (
        <SelecctCategory />
      )}
    </div>
  );
}
