import React from "react";
import { getRegionList } from "./parsingRegionData";
import SouthKoreaItem from "./KoreaItem";
import { useSetRecoilState } from "recoil";
import { selectSidoState } from "../../../recoil/states/RecommendPageState";
import "../../../css/map.css";
export default function SouthKorea({ list }: any) {
  const setSido = useSetRecoilState(selectSidoState);

  return (
    <div className="map-wrapper">
      <div className="info"></div>
      <svg
        width="400"
        height="800"
        viewBox="30 100 400 800"
        xmlns="http://www.w3.org/2000/svg"
      >
        <g>
          {getRegionList(list).map((item: any) => {
            console.log(item);
            return (
              <SouthKoreaItem
                onClick={() => {
                  setSido({ sidoCode: item.sidoCode, name: item.sidoName });
                }}
                key={item.sidoCode}
                className={`land`}
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
