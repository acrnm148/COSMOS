import React, { useState, useEffect } from "react";
import ReactDOM from "react-dom";

export default function TMap({ state }: any) {
  const lat = state.center.lat;
  const lng = state.center.lng;

  useEffect(() => {
    // Tmapv2 사용을 위해 script Element 생성
    const script = document.createElement("script");
    script.setAttribute("id", "TMAP");

    // sds
    const scriptList = document.querySelectorAll("script[id='TMAP']");
    console.log(scriptList.length);
    if (scriptList.length >= 0) {
      console.log(script.parentNode);
      script.parentNode?.removeChild(script);
    }
    // 이후 여기에다가 Tmap 관련 코드 작성
    script.innerHTML = `
        function initTmap() {
            var map = new Tmapv2.Map("TMapApp", {
                center: new Tmapv2.LatLng("${lat}", "${lng}"),
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
  }, [state]);
  return <div className="w-full h-[50vh]" id="TMapApp"></div>;
}
