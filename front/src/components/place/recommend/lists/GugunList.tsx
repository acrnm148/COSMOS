import React from "react";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
} from "../../../../recoil/states/RecommendPageState";
import { useQuery } from "react-query";
import { getGugunList } from "../../../../apis/api/place";

export default function GugunList() {
  const sido = useRecoilState(selectSido);
  const [gugun, setGugun] = useRecoilState(selectGugun);

  // 구/군 선택
  const handleGugunList = (e: any) => {
    const idx = e.target.options.selectedIndex;
    const code = e.target.options[idx].getAttribute("data-key"); // 구군코드
    const name = e.target.value; // 구군이름
    const selected = { gugunCode: code, gugunName: name };
    setGugun(selected);
  };
  // 구/군 리스트 얻어옴
  const { data, isLoading } = useQuery({
    queryKey: ["getGugunList", sido],
    queryFn: () => getGugunList(sido[0].sidoCode),
  });

  if (isLoading || data === undefined) return null;

  return (
    <select
      className="w-40 h-10 m-auto border-[4px] border-lightMain rounded-lg outline-none focus:bg-lightMain3 dark:bg-darkBackground dark:text-white dark:border-darkMain3"
      name="selectSido"
      id="selectSido"
      onChange={handleGugunList}
      value={gugun.gugunName === "" ? "구/군 선택" : gugun.gugunName}
    >
      <option value="">구/군 선택</option>
      {data === undefined || data === null
        ? null
        : data.map((item: any) => {
            return (
              <option
                value={item.gugunName}
                key={item.gugunCode}
                data-key={item.gugunCode}
              >
                {item.gugunName}
              </option>
            );
          })}
    </select>
  );
}
