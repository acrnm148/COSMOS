import React, { useState } from "react";
import { useQuery } from "react-query";
import { getSidoList } from "../../../../apis/api/place";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
} from "../../../../recoil/states/SearchPageState";
export default function SidoList() {
  // 시/도 Recoil 수정
  const [sido, setSido] = useRecoilState(selectSido);
  const [gugun, setGugun] = useRecoilState(selectGugun);
  // 시/도 선택
  const handleSidoList = (e: any) => {
    const idx = e.target.options.selectedIndex;
    const code = e.target.options[idx].getAttribute("data-key"); // 시도코드
    const name = e.target.value; // 시도이름

    const selected = { sidoCode: code, sidoName: name };
    setSido(selected);
    setGugun({ gugunCode: "", gugunName: "" });
  };

  // 시/도 리스트 얻어옴
  const { data, isLoading } = useQuery({
    queryKey: ["getSidoList"],
    queryFn: () => getSidoList(),
  });

  if (isLoading || data === undefined) return null;

  return (
    <select
      className="w-full h-14 m-auto border-[4px] border-lightMain dark:border-darkMain dark:bg-black dark:text-white rounded-l-lg outline-none hover:bg-lightMain3"
      name="selectSido"
      id="selectSido"
      onChange={handleSidoList}
      value={sido.sidoName === "" ? "시/도 선택" : sido.sidoName}
      data-key=""
    >
      <option value="" data-key="">
        시/도 선택
      </option>
      {data.map((item: any) => {
        return (
          <option
            value={item.sidoName}
            key={item.sidoCode}
            data-key={item.sidoCode}
          >
            {item.sidoName}
          </option>
        );
      })}
    </select>
  );
}
