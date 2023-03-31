import React from "react";
import PlaceList from "../../components/place/recommend/lists/PlaceList";
import TMapRecommend from "../../components/common/TMapRecommend";

export default function PlaceResult() {
  return (
    <div className="w-[90%] text-center">
      <div className="mt-5">
        <TMapRecommend />
      </div>
      <PlaceList />
    </div>
  );
}
