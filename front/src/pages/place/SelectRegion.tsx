import React from "react";
import KoreaMap from "../../components/common/Map/KoreaMap";

export default function SelectRegion({ setSelects }: any) {
  return (
    <div>
      <button>선택 완료</button>
      <KoreaMap />
    </div>
  );
}
