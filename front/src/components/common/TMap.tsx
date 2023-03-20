import React, { useState, useEffect, useRef } from "react";

export default function TMap({ state }: any) {
  const [position, setPosition] = useState(state);
  const [mapInstance, setMapInstance] = useState<Tmapv2.Map>();
  // 지도 div
  const mapRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    // 좌표가 바뀔때마다 마커 재설정
    mapInstance?.setCenter(
      new window.Tmapv2.LatLng(position.center.lat, position.center.lng)
    );
  }, [position]);

  useEffect(() => {
    const lat = state.center.lat;
    const lng = state.center.lng;
    // 현재 좌표 위치로 지도 설정
    if (window.Tmapv2) {
      if (mapRef.current !== null) {
        if (lat !== 0 && lng !== 0) {
          const map = new window.Tmapv2.Map(mapRef.current, {
            center: new window.Tmapv2.LatLng(lat, lng),
            width: "100%",
            height: "50vh",
            zoom: 15,
          });

          setMapInstance(map);

          const marker = new window.Tmapv2.Marker(mapRef.current, {
            position: new window.Tmapv2.LatLng(lat, lng),
            Icon: "http://tmapapi.sktelecom.com/resources/images/common/pin_car.png",
            map: map,
          });
        }
      }
    } else {
      console.error("TmapV2 API is not loaded");
    }
  }, [state]);

  return <div className="w-full h-[50vh]" ref={mapRef}></div>;
}
