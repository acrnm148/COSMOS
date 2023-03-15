import React, { useState, useEffect } from "react";
import ReactDOM from "react-dom";

export default function TMap({ state }: any) {
  const [position, setPosition] = useState(state);

  useEffect(() => {
    setPosition(state);
  }, [state]);

  useEffect(() => {
    // 현재 좌표 위치
    const lat = position.center.lat;
    const lng = position.center.lng;

    // 추가적으로 생성되는 TMAP 에러 해결
    const clearMap = document.querySelector("#TMapApp");
    clearMap?.replaceChildren(); // 현재 맵 하위 자식 컨텐츠 전체 삭제

    // 이후 다시 좌푯값을 얻어와서 맵 생성
    // Tmapv2 사용을 위해 script Element 생성
    const script = document.createElement("script");
    script.setAttribute("id", "TMAP");

    // 이후 여기에다가 Tmap 관련 코드 작성 d
    script.innerHTML = `
        function initTmap() {
            var map = new Tmapv2.Map("TMapApp", {
                center: new Tmapv2.LatLng(${lat}, ${lng}),
                width: "100%",
                height: "50vh",
                zoom:15
            });
        }

        initTmap();
        `;
    if (lat !== 0 && lng !== 0) {
      // 자바스크립트 형식으로 설정 후 document에 추가
      script.type = "text/javascript";
      script.async = true;
      document.head.appendChild(script);
    }
  }, [position]);
  return <div className="w-full h-[50vh] TMapApp" id="TMapApp"></div>;
}
