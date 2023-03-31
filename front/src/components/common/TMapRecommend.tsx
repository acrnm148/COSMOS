import React, { useState, useEffect, useRef } from "react";
import LightMarker from "../../assets/place/light-marker.png";
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

export default function TMapRecommend() {
  const sido = useRecoilState(selectSido);
  const gugun = useRecoilState(selectGugun);
  const category = useRecoilState(selectCategory);
  const [mapCenterState, setMapCenterState] = useRecoilState(mapCenter);
  const [mapMarkersState, setMapMarkersState] = useRecoilState(mapMarkers);

  const tmp = {
    sido: sido[0].sidoName,
    gugun: gugun[0].gugunName,
    categories: category[0],
    userSeq: 1,
  };

  const [item, setItem] = useState(JSON.stringify(tmp));
  const [mapInstance, setMapInstance] = useState<Tmapv2.Map>();
  const [markers, setMarkers] = useState<Tmapv2.Marker[]>();
  // 지도 div
  const mapRef = useRef<HTMLDivElement>(null);

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
      const markers = [{}];
      data.data.places.map((item: any) => {
        markers.push({
          lat: item.latitude,
          lng: item.longitude,
          placeId: item.placeId,
          name: item.name,
          type: item.type,
        });
      });
      markers.splice(0, 1);
      setMapMarkersState(markers);
    }
  }, [data]);

  useEffect(() => {
    const lat = mapCenterState.lat;
    const lng = mapCenterState.lng;
    // 현재 좌표 위치로 지도 설정
    if (window.Tmapv2) {
      if (mapRef.current !== null) {
        const map = new window.Tmapv2.Map(mapRef.current, {
          center: new window.Tmapv2.LatLng(lat, lng),
          width: "100%",
          height: "50vh",
          zoom: 12,
        });

        const newMarkers: ((prevState: never[]) => never[]) | Tmapv2.Marker[] =
          [];
        if (markers !== undefined) {
          for (let i in markers) {
            markers[i].setMap(null);
          }
        }
        mapMarkersState.map((item: any) => {
          if (item.lat !== null || item.lng !== null) {
            const marker = new window.Tmapv2.Marker({
              position: new window.Tmapv2.LatLng(item.lat, item.lng),
              icon: LightMarker,
              map: map,
              title: item.name,
            });
            newMarkers.push(marker);
          }

          //   // 웹
          //   marker.addListener("click", function () {
          //     console.log("CLICK");
          //   });
          //   // 앱
          //   marker.addListener("touchstart", function () {
          //     console.log("터치!");
          //   });
        });
        setMarkers(newMarkers);
        setMapInstance(map);
      }
    } else {
      console.error("TmapV2 API is not loaded");
    }
  }, [mapRef.current]);

  useEffect(() => {
    if (markers !== undefined) {
      for (let i in markers) {
        markers[i].setMap(null);
      }
    }

    const newMarkers: ((prevState: never[]) => never[]) | Tmapv2.Marker[] = [];
    if (window.Tmapv2) {
      if (mapInstance !== undefined) {
        mapMarkersState.map((item: any) => {
          if (item.lat !== null || item.lng !== null) {
            const marker = new window.Tmapv2.Marker({
              position: new window.Tmapv2.LatLng(item.lat, item.lng),
              icon: LightMarker,
              map: mapInstance,
              title: item.name,
            });
            newMarkers.push(marker);
          }
        });
      }
    } else {
      console.error("TmapV2 API is not loaded");
    }

    setMarkers(newMarkers);

    mapInstance?.setCenter(
      new window.Tmapv2.LatLng(mapCenterState.lat, mapCenterState.lng)
    );
  }, [mapMarkersState]);

  if (isLoading || data === undefined) return null;
  return <div className="w-full h-[50vh]" id="TMAP" ref={mapRef}></div>;
}
