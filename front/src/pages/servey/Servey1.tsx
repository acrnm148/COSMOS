import { serveyChoice, serveyPage } from "../../recoil/states/ServeyPageState";
import { useRecoilState } from "recoil";
import { useState } from "react";

export default function Servey1() {
  const [serveyCho, setServeyCho] = useRecoilState(serveyChoice);
  const [serveyPg, setServeyPage] = useRecoilState(serveyPage);

  function submit1(key: any) {
    let update1 = {};
    if ([0, 1, 3, 6].indexOf(key) == -1) {
      // J형
      update1 = { 1: "J" };
    } else {
      // E형
      update1 = { 1: "E" };
    }
    setServeyCho((serveyCho) => ({
      ...serveyCho,
      ...update1,
    }));
    setServeyPage(serveyPg + 1);
  }
  return (
    <div className="w-full">
      <h1 className="font-bold font-baloo text-2xl">데이트 취향설문</h1>
      <div className="mt-7 mb-7 w-full text-center">
        <span className="font-baloo text-base text-darkMain">1 / 9</span>
        <div className="border-solid border-2 h-5 border-darkMain w-full">
          <div className="h-full bg-darkMain w-10"></div>
        </div>
      </div>
      <h1 className="font-baloo text-base text-white mb-2">
        1. 가장 중요한 식당 포인트는?
      </h1>
      <ul className="flex flex-wrap w-[full] justify-center">
        {[
          "인스타 핫플",
          "컨셉이 있는 식당",
          "높은 평점",
          "무한리필 뷔페",
          "집밥 느낌의 한식",
          ,
          "평소 못 가본 오마카세",
          "이색 맛집",
          "맛있게 먹었던 식당",
        ].map((item, key) => {
          return (
            <div
              key={key}
              onClick={() => submit1(key)}
              className={
                "text-sm flex justify-center w-[32%] m-[2px] p-4 content-center text-center bg-darkMain4 pt-10 pb-10 rounded-lg hover:bg-opacity-75 border-solid border-2 border-darkMain4"
              }
            >
              <p className="text-white break-normal">{item}</p>
            </div>
          );
        })}
      </ul>
    </div>
  );
}
