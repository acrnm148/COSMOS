import React, { useState } from "react";
import LightBigData from "../../assets/place/light-bigdata.gif";
import DarkBigData from "../../assets/place/dark/dark-bigdata.gif";
import SelecctCategory from "../../components/place/recommend/SelectCategory";
import SelectRegion from "../../components/place/recommend/SelectRegion";
import { useRecoilState } from "recoil";
import { darkMode } from "../../recoil/states/UserState";
import {
  selectSido,
  selectGugun,
} from "../../recoil/states/RecommendPageState";
import Swal from "sweetalert2";

export default function PlaceRecommend() {
  const isDark = useRecoilState(darkMode);
  const sido = useRecoilState(selectSido);
  const gugun = useRecoilState(selectGugun);
  const [selects, setSelects] = useState([false, false]);

  const handleSelectCategoryFirst = () => {
    const Toast = Swal.mixin({
      toast: true,
      position: "bottom-end",
      showConfirmButton: false,
      timer: 1500,
      timerProgressBar: false,
    });

    Toast.fire({
      title: "지역 선택을 완료해주세요!",
      icon: "warning",
    });
  };

  return (
    <div className="text-center w-[90%] max-w-[950px] mt-[50px]">
      <div className="flex flex-row justify-center gap-[20vw]">
        {selects[0] ? (
          <div
            className="w-[200px] p-3 rounded-lg font-bold bg-lightMain text-white cursor-pointer dark:bg-darkMain3"
            onClick={() => setSelects([true, false])}
          >
            지역 선택
          </div>
        ) : (
          <div
            className="w-[200px] p-3 rounded-lg font-bold bg-lightMain2 text-white cursor-pointer dark:bg-darkMain"
            onClick={() => setSelects([true, false])}
          >
            지역 선택
          </div>
        )}
        {selects[1] ? (
          <div
            className="w-[200px] p-3 rounded-lg font-bold bg-lightMain text-white cursor-pointer dark:bg-darkMain3"
            onClick={() =>
              sido[0].sidoCode !== "" && gugun[0].gugunCode !== ""
                ? setSelects([false, true])
                : handleSelectCategoryFirst()
            }
          >
            카테고리 선택
          </div>
        ) : (
          <div
            className="w-[200px] p-3 rounded-lg font-bold bg-lightMain2 text-white cursor-pointer dark:bg-darkMain"
            onClick={() =>
              sido[0].sidoCode !== "" && gugun[0].gugunCode !== ""
                ? setSelects([false, true])
                : handleSelectCategoryFirst()
            }
          >
            카테고리 선택
          </div>
        )}
      </div>
      <hr className="my-[3vh]" />
      <div className="flex justify-center w-[100%]">
        {!selects[0] && !selects[1] ? (
          isDark ? (
            <img src={DarkBigData} className="w-[100%] max-w-[600px]" />
          ) : (
            <img src={LightBigData} className="w-[100%] max-w-[600px]" />
          )
        ) : null}
      </div>
      {selects[0] ? (
        <SelectRegion setSelects={setSelects} />
      ) : selects[1] ? (
        <SelecctCategory />
      ) : null}
    </div>
  );
}
