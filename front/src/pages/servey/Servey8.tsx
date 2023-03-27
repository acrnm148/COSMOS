import { serveyChoice, serveyPage } from "../../recoil/states/ServeyPageState";
import { useRecoilState } from "recoil";
import { useState } from "react";

export default function Servey4() {
  const [serveyPg, setServeyPage] = useRecoilState(serveyPage);
  const [serveyCho, setServeyCho] = useRecoilState(serveyChoice);

  function submit8(key: any) {
    let update8 = {};

    if (key == 1) {
      // Y형
      update8 = { 8: "Y" };
    } else {
      // T형
      update8 = { 8: "T" };
    }
    setServeyCho((serveyCho) => ({
      ...serveyCho,
      ...update8,
    }));
    setServeyPage(serveyPg + 1);
  }

  return (
    <div className="">
      <h1 className="font-bold font-baloo text-2xl">데이트 취향설문</h1>
      <div className="mt-7 mb-7 w-full text-center">
        <span className="font-baloo text-base text-darkMain">8 / 9</span>
        <div className="border-solid border-2 h-5 border-darkMain w-full">
          <div className="h-full bg-darkMain w-10/12"></div>
        </div>
      </div>
      <h1 className="font-baloo text-base text-white mb-2">
        8. 주로 찾는 데이트장소의 분위기?
      </h1>
      <ul className="flex flex-wrap w-full grid-rows-2 gap-2 justify-center">
        {[
          "사람들로 북적이는 활기있는 장소",
          "둘이서 얘기하기 좋은 조용한 장소",
        ].map((item, key) => {
          return (
            <div
              key={key}
              onClick={() => submit8(key)}
              className={
                "flex justify-center w-full h-40 p-5 content-center text-center bg-darkMain4 pt-10 pb-10 rounded-lg break-normal hover:bg-opacity-75  border-solid border-2 border-darkMain4"
              }
            >
              <div className="text-white">{item}</div>
            </div>
          );
        })}
      </ul>
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
