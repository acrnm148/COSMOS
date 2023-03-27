import React, { useState } from "react";
import { getRegionList } from "./parsingRegionData";
import SouthKoreaItem from "./KoreaItem";
import { useSetRecoilState } from "recoil";
import { selectSidoState } from "../../../recoil/states/RecommendPageState";
import "../../../css/map.css";
export default function SouthKorea({ list, setSelectSido }: any) {
  const [selSido, setSelSido] = useState("");
  const setSido = useSetRecoilState(selectSidoState);

  return (
    <div className="map-wrapper">
      <div className="info"></div>
      <svg
        width="375"
        height="700"
        viewBox="30 100 400 800"
        xmlns="http://www.w3.org/2000/svg"
      >
        <g>
          {getRegionList(list).map((item: any) => {
            return (
              <SouthKoreaItem
                onClick={() => {
                  setSido({ sidoCode: item.sidoCode, name: item.name });
                  setSelSido(item.name);
                  setSelectSido({ code: item.sidoCode, name: item.name });
                }}
                key={item.sidoCode}
                className={selSido === item.name ? "land click" : "land"}
                title={item.name}
                d={item.d}
              />
            );
          })}
        </g>
      </svg>
    </div>
  );
}
