import React, { useState } from "react";
import { getRegionList } from "./parsingRegionData";
import SouthKoreaItem from "./KoreaItem";
import Region from "../../../components/common/Map/Region";
import { useRecoilState } from "recoil";
import { selectSido } from "../../../recoil/states/RecommendPageState";
import "../../../css/map.css";

export default function SouthKorea() {
  const [sido, setSido] = useRecoilState(selectSido);

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
          {getRegionList(Region).map((item: any) => {
            return (
              <SouthKoreaItem
                onClick={() => {
                  setSido({ sidoCode: item.sidoCode, sidoName: item.name });
                }}
                key={item.sidoCode}
                className={sido.sidoName === item.name ? "land click" : "land"}
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
