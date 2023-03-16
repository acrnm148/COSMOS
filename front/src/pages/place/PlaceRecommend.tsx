import React, { useState } from "react";
import LightBigData from "../../assets/place/light-bigdata.gif";

export default function PlaceRecommend() {
  const [selects, setSelects] = useState([false, false]);

  return (
    <div className="text-center w-[90%] max-w-[950px] mt-[100px]">
      <h1>코스추천</h1>
      <div className="flex flex-row justify-center gap-[20vw]">
        {selects[0] ? (
          <div
            className="w-[200px] p-5 rounded-lg font-bold bg-lightMain text-white cursor-pointer"
            onClick={() => setSelects([true, false])}
          >
            지역 선택
          </div>
        ) : (
          <div
            className="w-[200px] p-5 rounded-lg font-bold bg-lightMain3 cursor-pointer"
            onClick={() => setSelects([true, false])}
          >
            지역 선택
          </div>
        )}
        {selects[1] ? (
          <div
            className="w-[200px] p-5 rounded-lg font-bold bg-lightMain text-white cursor-pointer"
            onClick={() => setSelects([false, true])}
          >
            카테고리 선택
          </div>
        ) : (
          <div
            className="w-[200px] p-5 rounded-lg font-bold bg-lightMain3 cursor-pointer"
            onClick={() => setSelects([false, true])}
          >
            카테고리 선택
          </div>
        )}
      </div>
      <hr className="my-[3vh]" />
      <div>
        <div className="flex justify-center w-[100%] mb-20">
          {!selects[0] && !selects[1] ? (
            <img src={LightBigData} className="w-[100%] max-w-[600px]" />
          ) : null}
        </div>
        {selects[0] ? (
          <div className="map-wrapper">지도</div>
        ) : selects[1] ? (
          <div>카테고리</div>
        ) : null}
      </div>
    </div>
  );
}
