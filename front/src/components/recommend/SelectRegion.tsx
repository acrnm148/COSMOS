import React, { useState } from "react";
import KoreaMap from "../common/Map/KoreaMap";
import Swal from "sweetalert2";

export default function SelectRegion({ setSelects }: any) {
  const sido = [
    {
      sidoCode: 0,
      name: "서울",
    },
    {
      sidoCode: 1,
      name: "부산",
    },
    {
      sidoCode: 2,
      name: "대구",
    },
    {
      sidoCode: 3,
      name: "인천",
    },
    {
      sidoCode: 4,
      name: "광주",
    },
    {
      sidoCode: 5,
      name: "대전",
    },
    {
      sidoCode: 6,
      name: "울산",
    },
    {
      sidoCode: 7,
      name: "경기",
    },
    {
      sidoCode: 8,
      name: "강원",
    },
    {
      sidoCode: 9,
      name: "충청북도",
    },
    {
      sidoCode: 10,
      name: "충청남도",
    },
    {
      sidoCode: 11,
      name: "전라북도",
    },
    {
      sidoCode: 12,
      name: "전라남도",
    },
    {
      sidoCode: 13,
      name: "경상북도",
    },
    {
      sidoCode: 14,
      name: "경상남도",
    },
    {
      sidoCode: 15,
      name: "제주도",
    },
    {
      sidoCode: 16,
      name: "세종",
    },
  ];

  const [selectSido, setSelectSido] = useState("");
  const [selGugun, setSelGugun] = useState("");

  const handleGugun = (e: any) => {
    setSelGugun(e.target.value);
  };

  const handleSelectGugun = () => {
    if (selGugun === "") {
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
        title: "구/군 선택 완료!",
        text: "카테고리 선택으로 넘어가시겠습니까?",
        icon: "success",
        confirmButtonColor: "#3085d6", // confrim 버튼 색깔 지정
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
      {selectSido === "" ? null : (
        <div className="flex flex-row justify-end gap-5">
          <select
            className="h-[40px] border-4 border-lightMain opacity-50 rounded-lg focus:outline-none"
            onChange={handleGugun}
          >
            <option className="options" value="">
              구/군 선택
            </option>
            <option value="test">테스트구군</option>
          </select>
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
