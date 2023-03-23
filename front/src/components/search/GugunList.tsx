import React, { useEffect } from "react";

import { useQuery } from "react-query";
import { getGugunList } from "../../apis/api/place";

export default function GugunList({ selectedSidoCode, setGugun }: any) {
  const sidoCode = selectedSidoCode.code;
  const handleGugunSelect = (e: any) => {
    const selectedIndex = e.target.options.selectedIndex;
    const selectedGugunCode =
      e.target.options[selectedIndex].getAttribute("data-key");
    const selectedGugunName = e.target.value;

    setGugun({ code: selectedGugunCode, name: selectedGugunName });
  };

  // 구/군 리스트
  const { data, isLoading } = useQuery({
    queryKey: ["getGugunList", sidoCode],
    queryFn: () => getGugunList(sidoCode),
  });

  if (isLoading) return null;

  return (
    <select
      className="h-12 w-[30%] border-4 border-lightMain opacity-50 rounded-lg focus:outline-none"
      onChange={handleGugunSelect}
    >
      <option value="">구/군 선택</option>
      {data.map((item: any) => {
        return (
          <option
            key={item.gugunCode}
            value={item.gugunName}
            data-key={item.gugunCode}
          >
            {item.gugunName}
          </option>
        );
      })}
    </select>
  );
}
