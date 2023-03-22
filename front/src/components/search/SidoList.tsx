import React from "react";

import { useQuery } from "react-query";
import { getSidoList } from "../../apis/api/place";

export default function SidoList({ setSido }: any) {
  const handleSidoSelect = (e: any) => {
    const selectedIndex = e.target.options.selectedIndex;
    const selectedSidoCode =
      e.target.options[selectedIndex].getAttribute("data-key");
    const selectedSidoName = e.target.value;

    setSido({ code: selectedSidoCode, name: selectedSidoName });
  };
  // 시/도 리스트
  const { data, isLoading } = useQuery({
    queryKey: ["getSidoList"],
    queryFn: () => getSidoList(),
  });

  if (isLoading) return null;

  return (
    <select
      className="basis-1/6 border-4 border-lightMain opacity-50 rounded-lg rounded-r-none focus:outline-none"
      onChange={handleSidoSelect}
    >
      <option value="">시/도 선택</option>
      {data.map((item: any) => {
        return (
          <option
            key={item.sidoCode}
            value={item.sidoName}
            data-key={item.sidoCode}
          >
            {item.sidoName}
          </option>
        );
      })}
    </select>
  );
}
