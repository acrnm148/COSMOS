import React from "react";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
  selectCategory,
} from "../../recoil/states/RecommendPageState";

export default function PlaceResult() {
  const sido = useRecoilState(selectSido);
  const gugun = useRecoilState(selectGugun);
  const category = useRecoilState(selectCategory);

  let formData = new FormData();
  formData.append("sido", sido[0].sidoName);
  formData.append("gugun", gugun[0].gugunName);
  // formData.append("categories", category);
  // formData.append("userSeq", 1);
  return <div>결과</div>;
}
