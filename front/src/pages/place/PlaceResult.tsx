import React, { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
  selectCategory,
  mapCenter,
  mapMarkers,
} from "../../recoil/states/RecommendPageState";
import { useQuery } from "react-query";
import { getDateCourse } from "../../apis/api/place";
import PlaceList from "../../components/place/search/lists/PlaceList";
import TMapRecommend from "../../components/common/TMapRecommend";

interface Place {
  longitude: string;
  latitude: string;
  placeId: number;
  type: string;
}

export default function PlaceResult() {
  const sido = useRecoilState(selectSido);
  const gugun = useRecoilState(selectGugun);
  const category = useRecoilState(selectCategory);
  const [mapCenterState, setMapCenterState] = useRecoilState(mapCenter);
  const [mapMarkersState, setMapMarkersState] = useRecoilState(mapMarkers);

  const tmp = {
    sido: "서울특별시",
    gugun: "강남구",
    categories: category[0],
    userSeq: 1,
  };

  const [item, setItem] = useState(JSON.stringify(tmp));
  const { data, isLoading } = useQuery({
    queryKey: ["getDateCourse", item],
    queryFn: () => getDateCourse(item),
  });

  useEffect(() => {
    setItem(JSON.stringify(tmp));
  }, [category[0]]);

  useEffect(() => {
    if (data !== null && data !== undefined) {
      setMapCenterState({
        lat: data.data.midLatitude,
        lng: data.data.midLongitude,
      });
      const markers = [{ lat: "", lng: "", placeId: 0, type: "" }];
      data.data.places.map((item: any) => {
        markers.push({
          lat: item.latitude,
          lng: item.longitude,
          placeId: item.placeId,
          type: item.type,
        });
      });
      markers.splice(0, 1);
      setMapMarkersState(markers);
    }
  }, [data?.data.places]);

  if (isLoading) return null;

  return (
    <div className="w-[90%] text-center">
      <div className="mt-5">
        <TMapRecommend />
      </div>
      {data === null || data === undefined ? null : (
        <PlaceList data={data.data.places} />
      )}
    </div>
  );
}
