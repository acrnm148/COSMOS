import React, { useState, useEffect, useRef } from "react";
import LightMarker from "../../assets/place/light-marker.png";
import DarkMarker from "../../assets/place/dark/dark-marker.png";
import Cinema from "../../assets/place/cinema-marker.png";
import Cutlery from "../../assets/place/cutlery-marker.png";
import Coffee from "../../assets/place/coffee-cup-marker.png";
import Shopping from "../../assets/place/shopping-cart-marker.png";
import Gym from "../../assets/place/gym-marker.png";
import Suitcase from "../../assets/place/suitcase-marker.png";
import DarkCinema from "../../assets/place/dark/cinema-marker.png";
import DarkCutlery from "../../assets/place/dark/cutlery-marker.png";
import DarkCoffee from "../../assets/place/dark/coffee-cup-marker.png";
import DarkShopping from "../../assets/place/dark/shopping-cart-marker.png";
import DarkGym from "../../assets/place/dark/gym-marker.png";
import DarkSuitcase from "../../assets/place/dark/suitcase-marker.png";
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
import Loading from "./Loading";

export default function TMap({ offset }: any) {
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
      offset,
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

        mapMarkersState.map((item: any, index: number) => {
          if (item.lat !== null || item.lng !== null) {
            if (index > offset * 20 && index < offset * 20 + 20) {
              const marker = isDark[0]
                ? item.type === "restaurant"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkCutlery,
                      map: map,
                      title: item.name,
                    })
                  : item.type === "cafe"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkCoffee,
                      map: map,
                      title: item.name,
                    })
                  : item.type === "culture"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkCinema,
                      map: map,
                      title: item.name,
                    })
                  : item.type === "accommodation"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkMarker,
                      map: map,
                      title: item.name,
                    })
                  : item.type === "shopping"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkShopping,
                      map: map,
                      title: item.name,
                    })
                  : item.type === "tour"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkSuitcase,
                      map: map,
                      title: item.name,
                    })
                  : item.type === "leisure"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkGym,
                      map: map,
                      title: item.name,
                    })
                  : new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkMarker,
                      map: map,
                      title: item.name,
                    })
                : item.type === "restaurant"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Cutlery,
                    map: map,
                    title: item.name,
                  })
                : item.type === "cafe"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Coffee,
                    map: map,
                    title: item.name,
                  })
                : item.type === "culture"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Cinema,
                    map: map,
                    title: item.name,
                  })
                : item.type === "accommodation"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: LightMarker,
                    map: map,
                    title: item.name,
                  })
                : item.type === "shopping"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Shopping,
                    map: map,
                    title: item.name,
                  })
                : item.type === "tour"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Suitcase,
                    map: map,
                    title: item.name,
                  })
                : item.type === "leisure"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Gym,
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
        mapMarkersState.map((item: any, index: number) => {
          if (item.lat !== null || item.lng !== null) {
            if (index > offset * 20 && index < offset * 20 + 20) {
              const marker = isDark[0]
                ? item.type === "restaurant"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkCutlery,
                      map: mapInstance,
                      title: item.name,
                    })
                  : item.type === "cafe"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkCoffee,
                      map: mapInstance,
                      title: item.name,
                    })
                  : item.type === "culture"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkCinema,
                      map: mapInstance,
                      title: item.name,
                    })
                  : item.type === "accommodation"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkMarker,
                      map: mapInstance,
                      title: item.name,
                    })
                  : item.type === "shopping"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkShopping,
                      map: mapInstance,
                      title: item.name,
                    })
                  : item.type === "tour"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkSuitcase,
                      map: mapInstance,
                      title: item.name,
                    })
                  : item.type === "leisure"
                  ? new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkGym,
                      map: mapInstance,
                      title: item.name,
                    })
                  : new window.Tmapv2.Marker({
                      position: new window.Tmapv2.LatLng(item.lat, item.lng),
                      icon: DarkMarker,
                      map: mapInstance,
                      title: item.name,
                    })
                : item.type === "restaurant"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Cutlery,
                    map: mapInstance,
                    title: item.name,
                  })
                : item.type === "cafe"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Coffee,
                    map: mapInstance,
                    title: item.name,
                  })
                : item.type === "culture"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Cinema,
                    map: mapInstance,
                    title: item.name,
                  })
                : item.type === "accommodation"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: LightMarker,
                    map: mapInstance,
                    title: item.name,
                  })
                : item.type === "shopping"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Shopping,
                    map: mapInstance,
                    title: item.name,
                  })
                : item.type === "tour"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Suitcase,
                    map: mapInstance,
                    title: item.name,
                  })
                : item.type === "leisure"
                ? new window.Tmapv2.Marker({
                    position: new window.Tmapv2.LatLng(item.lat, item.lng),
                    icon: Gym,
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
  if (isLoading) return <Loading />;

  return <div className="w-full h-[50vh]" id="TMAP" ref={mapRef}></div>;
}
