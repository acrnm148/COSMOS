import { serveyChoice, serveyPage } from "../../recoil/states/ServeyPageState";
import { useRecoilState } from "recoil";
import { useState } from "react";

export default function Servey4() {
  const [serveyPg, setServeyPage] = useRecoilState(serveyPage);
  const [serveyCho, setServeyCho] = useRecoilState(serveyChoice);

  function submit6(key: any) {
    let update6 = {};

    if ([0, 2].indexOf(key) == -1) {
      // A형
      update6 = { 6: "A" };
    } else {
      // O형
      update6 = { 6: "O" };
    }
    setServeyCho((serveyCho) => ({
      ...serveyCho,
      ...update6,
    }));
    setServeyPage(serveyPg + 1);
  }

  return (
    <div className="w-full">
      <h1 className="font-bold font-baloo text-2xl">데이트 취향설문</h1>
      <div className="mt-7 mb-7 w-full text-center">
        <span className="font-baloo text-base text-darkMain">6 / 9</span>
        <div className="border-solid border-2 h-5 border-darkMain w-full">
          <div className="h-full bg-darkMain w-7/12"></div>
        </div>
      </div>
      <h1 className="font-baloo text-base text-white mb-2">
        6. 야경을 보러간다면?
      </h1>
      <div className="flex flex-wrap w-full grid-rows-2 gap-2 justify-center">
        {[
          ["호텔 라운지에서 보는", "도시 야경"],
          ["공원에서 자전거타며", "강변야경"],
          ["캠핑장에서 올려다보는", "밤하늘"],
          ["집에서 유튜브로 보는", "VR 야경"],
        ].map((item, key) => {
          return (
            <div
              key={key}
              onClick={() => submit6(key)}
              className={
                "flex w-40 justify-center p-5 content-center text-center bg-darkMain4 pt-10 pb-10 rounded-lg break-normal hover:bg-opacity-75  border-solid border-2 border-darkMain4"
              }
            >
              <div className="">
                <p className="text-sm text-white">{item[0]}</p>
                <p className="text-white">{item[1]}</p>
              </div>
            </div>
          );
        })}
      </div>
      <div className="flex grid-rows-2 gap-2">
        <button
          onClick={() => setServeyPage(serveyPg - 1)}
          className="flex border-solid border-2 border-darkMain justify-center mt-5 text-darkMain p-3 text-center rounded-lg w-full mx-auto 
                    hover:bg-darkMain hover:text-white"
        >
          이전
        </button>
      </div>
    </div>
  );
}
