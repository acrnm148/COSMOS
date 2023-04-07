import React, { useState, useEffect, useRef } from "react";
import LightMarker from "../../assets/place/light-marker.png";
import DarkMarker from "../../assets/place/dark/dark-marker.png";
import { useRecoilState } from "recoil";
import { mapCenter, mapMarkers } from "../../recoil/states/WishPageState";
import { useQuery } from "react-query";
import Loading from "../../components/common/Loading";
import CourseLike from "./CourseLike";
import { userState, darkMode } from "../../recoil/states/UserState";
import { getCourseDetail } from "../../apis/api/wish";
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

export default function TMapRecommend(props: { courseId: any }) {
    const isDark = useRecoilState(darkMode);
    const [mapCenterState, setMapCenterState] = useRecoilState(mapCenter);
    const [mapMarkersState, setMapMarkersState] = useRecoilState(mapMarkers);
    const [userSeq, setUserSeq] = useRecoilState(userState);

    const tmp = {
        userSeq: userSeq.seq,
    };

    const [item, setItem] = useState(JSON.stringify(tmp));
    const [tDistance, setTDistance] = useState("");
    const [tTime, setTTime] = useState("");
    const [tFare, setTFare] = useState("");
    const [mapInstance, setMapInstance] = useState<Tmapv2.Map>();
    const [markers, setMarkers] = useState<Tmapv2.Marker[]>();
    // 지도 div
    const mapRef = useRef<HTMLDivElement>(null);

    // api
    const { data, isLoading } = useQuery({
        queryKey: ["getCourseDetail", "courseId"],
        queryFn: () =>
            getCourseDetail(props.courseId, userSeq.seq, userSeq.acToken),
    });

    useEffect(() => {
        setItem(JSON.stringify(tmp));
    });

    useEffect(() => {
        if (data !== null && data !== undefined) {
            setMapCenterState({
                lat: data.midLatitude,
                lng: data.midLongitude,
            });
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

                const newMarkers:
                    | ((prevState: never[]) => never[])
                    | Tmapv2.Marker[] = [];
                const all: any[] = [];
                if (markers !== undefined) {
                    for (let i in markers) {
                        markers[i].setMap(null);
                    }
                }
                mapMarkersState.map((item: any, index: number) => {
                    if (item.lat !== null || item.lng !== null) {
                        const marker = isDark[0]
                            ? item.type === "restaurant"
                                ? new window.Tmapv2.Marker({
                                      position: new window.Tmapv2.LatLng(
                                          item.lat,
                                          item.lng
                                      ),
                                      icon: DarkCutlery,
                                      map: map,
                                      title: item.name,
                                  })
                                : item.type === "cafe"
                                ? new window.Tmapv2.Marker({
                                      position: new window.Tmapv2.LatLng(
                                          item.lat,
                                          item.lng
                                      ),
                                      icon: DarkCoffee,
                                      map: map,
                                      title: item.name,
                                  })
                                : item.type === "culture"
                                ? new window.Tmapv2.Marker({
                                      position: new window.Tmapv2.LatLng(
                                          item.lat,
                                          item.lng
                                      ),
                                      icon: DarkCinema,
                                      map: map,
                                      title: item.name,
                                  })
                                : item.type === "accommodation"
                                ? new window.Tmapv2.Marker({
                                      position: new window.Tmapv2.LatLng(
                                          item.lat,
                                          item.lng
                                      ),
                                      icon: DarkMarker,
                                      map: map,
                                      title: item.name,
                                  })
                                : item.type === "shopping"
                                ? new window.Tmapv2.Marker({
                                      position: new window.Tmapv2.LatLng(
                                          item.lat,
                                          item.lng
                                      ),
                                      icon: DarkShopping,
                                      map: map,
                                      title: item.name,
                                  })
                                : item.type === "tour"
                                ? new window.Tmapv2.Marker({
                                      position: new window.Tmapv2.LatLng(
                                          item.lat,
                                          item.lng
                                      ),
                                      icon: DarkSuitcase,
                                      map: map,
                                      title: item.name,
                                  })
                                : item.type === "leisure"
                                ? new window.Tmapv2.Marker({
                                      position: new window.Tmapv2.LatLng(
                                          item.lat,
                                          item.lng
                                      ),
                                      icon: DarkGym,
                                      map: map,
                                      title: item.name,
                                  })
                                : new window.Tmapv2.Marker({
                                      position: new window.Tmapv2.LatLng(
                                          item.lat,
                                          item.lng
                                      ),
                                      icon: DarkMarker,
                                      map: map,
                                      title: item.name,
                                  })
                            : item.type === "restaurant"
                            ? new window.Tmapv2.Marker({
                                  position: new window.Tmapv2.LatLng(
                                      item.lat,
                                      item.lng
                                  ),
                                  icon: Cutlery,
                                  map: map,
                                  title: item.name,
                              })
                            : item.type === "cafe"
                            ? new window.Tmapv2.Marker({
                                  position: new window.Tmapv2.LatLng(
                                      item.lat,
                                      item.lng
                                  ),
                                  icon: Coffee,
                                  map: map,
                                  title: item.name,
                              })
                            : item.type === "culture"
                            ? new window.Tmapv2.Marker({
                                  position: new window.Tmapv2.LatLng(
                                      item.lat,
                                      item.lng
                                  ),
                                  icon: Cinema,
                                  map: map,
                                  title: item.name,
                              })
                            : item.type === "accommodation"
                            ? new window.Tmapv2.Marker({
                                  position: new window.Tmapv2.LatLng(
                                      item.lat,
                                      item.lng
                                  ),
                                  icon: LightMarker,
                                  map: map,
                                  title: item.name,
                              })
                            : item.type === "shopping"
                            ? new window.Tmapv2.Marker({
                                  position: new window.Tmapv2.LatLng(
                                      item.lat,
                                      item.lng
                                  ),
                                  icon: Shopping,
                                  map: map,
                                  title: item.name,
                              })
                            : item.type === "tour"
                            ? new window.Tmapv2.Marker({
                                  position: new window.Tmapv2.LatLng(
                                      item.lat,
                                      item.lng
                                  ),
                                  icon: Suitcase,
                                  map: map,
                                  title: item.name,
                              })
                            : item.type === "leisure"
                            ? new window.Tmapv2.Marker({
                                  position: new window.Tmapv2.LatLng(
                                      item.lat,
                                      item.lng
                                  ),
                                  icon: Gym,
                                  map: map,
                                  title: item.name,
                              })
                            : new window.Tmapv2.Marker({
                                  position: new window.Tmapv2.LatLng(
                                      item.lat,
                                      item.lng
                                  ),
                                  icon: LightMarker,
                                  map: map,
                                  title: item.name,
                              });
                        all.push(item);
                        newMarkers.push(marker);
                    }
                });

                var rest: any[] = [];

                for (let i = 1; i < all.length - 1; i++) {
                    const data = {
                        viaPointId: `${all[i].placeId}`,
                        viaPointName: `${all[i].name}`,
                        viaX: `${all[i].lng}`,
                        viaY: `${all[i].lat}`,
                    };
                    rest.push(data);
                }

                // 경로 탐색 API 사용 요청
                var routeLayer;
                var selectOption = "0";
                var headers = {
                    appKey: "aqfIFlCTMCk4ui6GBQGW6wTurELvMKx8baquZQG8",
                    "Content-Type": "application/json",
                };

                var param = JSON.stringify({
                    startName: "출발지",
                    startX: all[0].lng,
                    startY: all[0].lat,
                    startTime: "201708081103",
                    endName: "도착지",
                    endX: all[all.length - 1].lng,
                    endY: all[all.length - 1].lat,
                    viaPoints: rest,
                    reqCoordType: "WGS84GEO",
                    resCoordType: "EPSG3857",
                    searchOption: selectOption,
                });

                var resultInfoArr: Tmapv2.Polyline[];
                var drawInfoArr = [];

                $.ajax({
                    method: "POST",
                    url: "https://apis.openapi.sk.com/tmap/routes/routeSequential30?version=1&format=json", //
                    headers: headers,
                    async: false,
                    data: param,
                    success: function (response) {
                        console.log(response);
                        var resultData = response.properties;
                        var resultFeatures = response.features;

                        // 결과 출력
                        setTDistance(
                            "총 거리 : " +
                                (resultData.totalDistance / 1000).toFixed(1) +
                                "km"
                        );
                        setTTime(
                            "총 시간 : " +
                                (resultData.totalTime / 60).toFixed(0) +
                                "분"
                        );
                        setTFare("총 요금 : " + resultData.totalFare + "원");

                        //기존  라인 초기화

                        if (resultInfoArr !== undefined) {
                            if (resultInfoArr.length > 0) {
                                for (var i in resultInfoArr) {
                                    resultInfoArr[i].setMap(null);
                                }
                                resultInfoArr = [];
                            }
                        }

                        for (var i in resultFeatures) {
                            var geometry = resultFeatures[i].geometry;
                            var properties = resultFeatures[i].properties;
                            var polyline_;

                            drawInfoArr = [];

                            if (geometry.type === "LineString") {
                                for (var j in geometry.coordinates) {
                                    // 경로들의 결과값(구간)들을 포인트 객체로 변환
                                    var latlng = new Tmapv2.Point(
                                        geometry.coordinates[j][0],
                                        geometry.coordinates[j][1]
                                    );
                                    // 포인트 객체를 받아 좌표값으로 변환
                                    var convertPoint =
                                        new Tmapv2.Projection.convertEPSG3857ToWGS84GEO(
                                            latlng
                                        );
                                    // 포인트객체의 정보로 좌표값 변환 객체로 저장
                                    var convertChange = new Tmapv2.LatLng(
                                        convertPoint._lat,
                                        convertPoint._lng
                                    );

                                    if (drawInfoArr !== undefined) {
                                        drawInfoArr.push(convertChange);
                                    }
                                }

                                polyline_ = new Tmapv2.Polyline({
                                    path: drawInfoArr,
                                    strokeColor: isDark ? "#9C4395" : "#FF8E9E",
                                    strokeWeight: 6,
                                    map: map,
                                });

                                if (resultInfoArr !== undefined) {
                                    resultInfoArr.push(polyline_);
                                }
                            }
                        }
                    },
                    error: function (request, status, error) {
                        // 429 에러 => 일별 무료제공 쿼터 소진
                        console.log(
                            "code:" +
                                request.status +
                                "\n" +
                                "message:" +
                                request.responseText +
                                "\n" +
                                "error:" +
                                error
                        );
                    },
                });

                setMarkers(newMarkers);
                setMapInstance(map);
            }
        } else {
            console.error("TmapV2 API is not loaded");
        }
    }, [mapRef.current]);

    if (isLoading || data === undefined) return <Loading />;

    return (
        <>
            <div className="flex flex-row relative bg-lightMain3 justify-center h-14 wb-3 dark:bg-darkMain">
                <div className="flex flex-row gap-3 h-15 m-auto">
                    <span className="font-bold dark:text-white">
                        {tDistance}
                    </span>
                    <span className="font-bold dark:text-white">{tTime}</span>
                    <span className="font-bold dark:text-white">{tFare}</span>
                </div>
            </div>

            <div className="w-full h-[50vh]" id="TMAP" ref={mapRef}></div>
        </>
    );
}
