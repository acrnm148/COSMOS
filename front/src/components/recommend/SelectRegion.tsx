import React, { useState, useEffect } from "react";
import KoreaMap from "../common/Map/KoreaMap";
import Swal from "sweetalert2";
import GugunList from "../search/GugunList";

export default function SelectRegion({ setSelects }: any) {
  const sido = [
    {
      sidoCode: 11,
      name: "서울특별시",
    },
    {
      sidoCode: 26,
      name: "부산광역시",
    },
    {
      sidoCode: 27,
      name: "대구광역시",
    },
    {
      sidoCode: 28,
      name: "인천광역시",
    },
    {
      sidoCode: 29,
      name: "광주광역시",
    },
    {
      sidoCode: 30,
      name: "대전광역시",
    },
    {
      sidoCode: 31,
      name: "울산광역시",
    },
    {
      sidoCode: 41,
      name: "경기도",
    },
    {
      sidoCode: 42,
      name: "강원도",
    },
    {
      sidoCode: 43,
      name: "충청북도",
    },
    {
      sidoCode: 44,
      name: "충청남도",
    },
    {
      sidoCode: 45,
      name: "전라북도",
    },
    {
      sidoCode: 46,
      name: "전라남도",
    },
    {
      sidoCode: 47,
      name: "경상북도",
    },
    {
      sidoCode: 48,
      name: "경상남도",
    },
    {
      sidoCode: 50,
      name: "제주특별자치도",
    },
    {
      sidoCode: 36,
      name: "세종특별자치시",
    },
  ];

  const [selectSido, setSelectSido] = useState();
  const [selGugun, setSelGugun] = useState();

  const handleGugun = (e: any) => {
    setSelGugun(e.target.value);
  };

  const handleSelectGugun = () => {
    if (selGugun === undefined) {
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
      const Toast = Swal.mixin({
        toast: true,
        position: "bottom-end",
        showConfirmButton: false,
        timer: 1500,
        timerProgressBar: false,
      });

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
      {selectSido === undefined ? null : (
        <div className="flex flex-row justify-end gap-5">
          <GugunList selectedSidoCode={selectSido} setGugun={setSelGugun} />
          <button
            className="bg-lightMain text-white rounded-lg p-2"
            onClick={() => handleSelectGugun()}
          >
            선택 완료
          </button>
        </div>
      )}
      <KoreaMap list={sido} setSelectSido={setSelectSido} />
    </div>
  );
}
