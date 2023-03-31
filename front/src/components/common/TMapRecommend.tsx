import React, { useState, useEffect, useRef } from "react";
import LightMarker from "../../assets/place/light-marker.png";
import { useRecoilState } from "recoil";
import { mapCenter, mapMarkers } from "../../recoil/states/RecommendPageState";
export default function TMapRecommend() {
  const [mapCenterState, setMapCenterState] = useRecoilState(mapCenter);
  const [mapMarkersState, setMapMarkersState] = useRecoilState(mapMarkers);
  console.log(mapMarkersState);
  // const [position, setPosition] = useState(mapCenterState);
  const [mapInstance, setMapInstance] = useState<Tmapv2.Map>();
  // 지도 div
  const mapRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    // 좌표가 바뀔때마다 마커 재설정
    mapInstance?.setCenter(
      new window.Tmapv2.LatLng(mapCenterState.lat, mapCenterState.lng)
    );
  }, [mapCenterState]);

  useEffect(() => {
    const lat = mapCenterState.lat;
    const lng = mapCenterState.lng;
    // 현재 좌표 위치로 지도 설정
    if (window.Tmapv2) {
      if (mapRef.current !== null) {
        if (lat !== 0 && lng !== 0) {
          const map = new window.Tmapv2.Map(mapRef.current, {
            center: new window.Tmapv2.LatLng(lat, lng),
            width: "100%",
            height: "50vh",
            zoom: 10,
          });

          const resultMarkerArr = [];
          mapMarkersState.map((item: any, index: number) => {
            if (index === 0) {
              const marker_s = new window.Tmapv2.Marker({
                position: new window.Tmapv2.LatLng(item.lat, item.lng),
                icon: LightMarker,
                map: map,
              });
            }
            if (item.lat !== null || item.lng !== null) {
              const marker = new window.Tmapv2.Marker({
                position: new window.Tmapv2.LatLng(item.lat, item.lng),
                icon: LightMarker,
                map: map,
              });
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

          setMapInstance(map);
        }
      }
    } else {
      console.error("TmapV2 API is not loaded");
    }
  }, [mapCenterState]);

  return <div className="w-full h-[50vh]" id="TMAP" ref={mapRef}></div>;
}
