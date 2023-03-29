import React, { useState, useEffect } from "react";
import SearchWait from "../../assets/place/search-wait.png";
import SearchWord from "../../components/place/SearchWord";
import GugunList from "../../components/place/lists/GugunList";
import SidoList from "../../components/place/lists/SidoList";
import TMap from "../../components/common/TMap";
import PlaceList from "../../components/place/lists/PlaceList";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
  completeWord,
  selectCategory,
  mapCenter,
  mapMarkers,
} from "../../recoil/states/SearchPageState";
import { useQuery } from "react-query";
import { getPlacesWithConditions } from "../../apis/api/place";
import SearchFilter from "../../components/place/SearchFilter";

export default function PlaceSearch() {
  const sidoState = useRecoilState(selectSido);
  const gugunState = useRecoilState(selectGugun);
  const wordState = useRecoilState(completeWord);
  const categoryState = useRecoilState(selectCategory);
  const [mapCenterState, setMapCenterState] = useRecoilState(mapCenter);
  const [mapMarkersState, setMapMarkersState] = useRecoilState(mapMarkers);
  const LIMIT = 10;
  const [offset, setOffset] = useState(0);

  const { data, isLoading } = useQuery({
    queryKey: [
      "getPlacesWithConditions",
      sidoState[0].sidoName,
      gugunState[0].gugunName,
      wordState[0],
      categoryState[0],
      LIMIT,
      offset,
    ],
    queryFn: () =>
      getPlacesWithConditions(
        1,
        sidoState[0].sidoName,
        gugunState[0].gugunName,
        wordState[0],
        categoryState[0],
        LIMIT,
        offset
      ),
  });

  useEffect(() => {
    if (data !== null && data !== undefined) {
      setMapCenterState({ lat: data.midLatitude, lng: data.midLongitude });
      const markers = [{}];
      data.places.map((item: any) => {
        markers.push({ lat: item.latitude, lng: item.longitude });
      });
      markers.splice(0, 1);
      setMapMarkersState(markers);
    }
  }, [data]);

  if (isLoading) return null;

  return (
    <div className="w-[90%] text-center">
      <div className="flex flex-row justify-center mt-10">
        <div className="basis-1/3 h-14">
          <SidoList />
        </div>
        <div className="basis-1/3 h-14">
          <GugunList />
        </div>
      </div>
      <div className="mt-5">
        <SearchWord />
      </div>
      {sidoState[0].sidoName !== "" && gugunState[0].gugunName !== "" ? (
        <SearchFilter />
      ) : null}
      <hr className="my-5 bg-slate-700" />
      <div className="mt-5">
        {data === null || data === undefined ? (
          <div className="w-full h-[50vh]">
            <img className="h-full m-auto rounded-lg" src={SearchWait} />
          </div>
        ) : (
          <TMap />
        )}
      </div>
      {data === null || data === undefined ? null : (
        <PlaceList data={data.places} />
      )}
    </div>
  );
}
