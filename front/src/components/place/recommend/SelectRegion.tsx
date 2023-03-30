import React from "react";
import KoreaMap from "../../common/Map/KoreaMap";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
} from "../../../recoil/states/RecommendPageState";
import GugunList from "./lists/GugunList";
import Swal from "sweetalert2";

export default function SelectRegion({ setSelects }: any) {
  const sido = useRecoilState(selectSido);
  const gugun = useRecoilState(selectGugun);

  const handleSelectGugun = () => {
    if (gugun[0].gugunName === "") {
      const Toast = Swal.mixin({
        toast: true,
        position: "bottom-end",
        showConfirmButton: false,
        timer: 1500,
        timerProgressBar: false,
      });

      Toast.fire({
        title: "구/군을 선택해주세요!",
        icon: "warning",
      });
    } else {
      Swal.fire({
        title: `선택 완료!`,
        text: "카테고리 선택으로 이동됩니다.",
        icon: "success",
        confirmButtonColor: "#FF8E9E", // confrim 버튼 색깔 지정
        confirmButtonText: "확인", // confirm 버튼 텍스트 지정
      }).then((result) => {
        if (result.isConfirmed) {
          setSelects([false, true]);
        }
      });
    }
  };

  return (
    <div>
      <div className="flex flex-row justify-end gap-5">
        {sido[0].sidoName === "" ? (
          <button className="bg-lightMain text-white rounded-lg p-2">
            선택 완료
          </button>
        ) : (
          <div className="flex flex-row justify-end gap-5">
            <GugunList />
            <button
              className="bg-lightMain text-white rounded-lg p-2"
              onClick={() => handleSelectGugun()}
            >
              선택 완료
            </button>
          </div>
        )}
      </div>
      <KoreaMap />
    </div>
  );
}
