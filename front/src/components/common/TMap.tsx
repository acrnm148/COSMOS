import React, { useState, useEffect } from "react";
import ReactDOM from "react-dom";

export default function TMap({ state }: any) {
  const [position, setPosition] = useState(state);

  useEffect(() => {
    setPosition(state);
  }, [state]);

  useEffect(() => {
    console.log(document.head);
    // 현재 좌표 위치
    const lat = position.center.lat;
    const lng = position.center.lng;

    // Tmapv2 사용을 위해 script Element 생성
    const scriptList = document.querySelectorAll("script[id='TMAP']");

    if (scriptList.length > 0) {
      const test = document.getElementById("TMAP");
      console.log(test?.parentNode);
      for (var i = 0; i < scriptList.length; i++) {
        scriptList[i].parentNode?.removeChild(scriptList[i]);
      }
      test?.parentNode?.removeChild(test);
    } else {
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
        console.log(script);
        document.head.appendChild(script);
      }
    }
  }, [position]);
  return (
    <>
      <div className="w-full h-[50vh]" id="TMapApp"></div>
      <button
        onClick={() =>
          setPosition({
            center: {
              lat: 37.5652045,
              lng: 126.98702028,
            },
          })
        }
      >
        클릭
      </button>
    </>
  );
}
