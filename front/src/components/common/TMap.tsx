import React, { useState, useEffect, useRef } from "react";
import LightMarker from "../../assets/place/light-marker.png";
import DarkMarker from "../../assets/place/dark/dark-marker.png";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
  completeWord,
  selectCategory,
  mapCenter,
  mapMarkers,
} from "../../recoil/states/SearchPageState";
import { userState, darkMode } from "../../recoil/states/UserState";
import { useQuery } from "react-query";
import { getPlacesWithConditions } from "../../apis/api/place";

export default function TMap() {
  const isDark = useRecoilState(darkMode);
  const userSeq = useRecoilState(userState);
  const sidoState = useRecoilState(selectSido);
  const gugunState = useRecoilState(selectGugun);
  const wordState = useRecoilState(completeWord);
  const categoryState = useRecoilState(selectCategory);
  const [mapCenterState, setMapCenterState] = useRecoilState(mapCenter);
  const [mapMarkersState, setMapMarkersState] = useRecoilState(mapMarkers);

  const [mapInstance, setMapInstance] = useState<Tmapv2.Map>();
  const [markers, setMarkers] = useState<Tmapv2.Marker[]>();

  // 지도 div
  const mapRef = useRef<HTMLDivElement>(null);

  const { data, isLoading } = useQuery({
    queryKey: [
      "getPlacesWithConditions",
      sidoState[0].sidoName,
      gugunState[0].gugunName,
      wordState[0],
      categoryState[0],
    ],
    queryFn: () =>
      getPlacesWithConditions(
        userSeq[0].seq,
        sidoState[0].sidoName,
        gugunState[0].gugunName,
        wordState[0],
        categoryState[0]
      ),
  });
  useEffect(() => {
    if (data !== null && data !== undefined) {
      setMapCenterState({ lat: data.midLatitude, lng: data.midLongitude });
      const markers = [{}];
      data.places.map((item: any) => {
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

        mapMarkersState.map((item: any) => {
          if (item.lat !== null || item.lng !== null) {
            const marker = isDark
              ? new window.Tmapv2.Marker({
                  position: new window.Tmapv2.LatLng(item.lat, item.lng),
                  icon: DarkMarker,
                  map: map,
                  title: item.name,
                })
              : new window.Tmapv2.Marker({
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
        setTimeout(() => {
          setMarkers(newMarkers);
        }, 0);
        setMapInstance(map);
      }
    } else {
      console.error("TmapV2 API is not loaded");
    }
  }, [mapRef.current]);

  useEffect(() => {
    const newMarkers: ((prevState: never[]) => never[]) | Tmapv2.Marker[] = [];
    if (window.Tmapv2) {
      if (mapInstance !== undefined) {
        mapMarkersState.map((item: any) => {
          if (item.lat !== null || item.lng !== null) {
            const marker = isDark
              ? new window.Tmapv2.Marker({
                  position: new window.Tmapv2.LatLng(item.lat, item.lng),
                  icon: DarkMarker,
                  map: mapInstance,
                  title: item.name,
                })
              : new window.Tmapv2.Marker({
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

  // 삭제 담당
  useEffect(() => {
    return () => {
      if (markers !== undefined) {
        for (let i in markers) {
          markers[i].setMap(null);
        }
      }
    };
  }, [markers]);
  if (isLoading) return null;

  return <div className="w-full h-[50vh]" id="TMAP" ref={mapRef}></div>;
}
