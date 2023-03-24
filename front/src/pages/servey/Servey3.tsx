import { serveyChoice, serveyPage } from "../../recoil/states/ServeyPageState";
import { useRecoilState } from "recoil";
import { useState } from "react";

export default function Servey3() {
  const [serveyPg, setServeyPage] = useRecoilState(serveyPage);
  const [serveyCho, setServeyCho] = useRecoilState(serveyChoice);

  function submit3(key: any) {
    let update3 = {};

    if ([0, 2].indexOf(key)) {
      // Y형
      update3 = { 3: "Y" };
    } else {
      // T형
      update3 = { 3: "T" };
    }
    setServeyCho((serveyCho) => ({
      ...serveyCho,
      ...update3,
    }));
    setServeyPage(serveyPg + 1);
  }

  return (
    <div className="w-full">
      <h1 className="font-bold font-baloo text-2xl">데이트 취향설문</h1>
      <div className="mt-7 mb-7 w-full text-center">
        <span className="font-baloo text-base text-darkMain">3 / 9</span>
        <div className="border-solid border-2 h-5 border-darkMain w-full">
          <div className="h-full bg-darkMain w-3/12"></div>
        </div>
      </div>
      <h1 className="font-baloo text-base text-white mb-2">
        3. 더 가고싶은 데이트장소?
      </h1>
      <ul className="flex flex-wrap w-full grid-rows-2 gap-2 justify-center">
        {["공원피크닉", "미술전시회", "불꽃축제", "밤새영화보기"].map(
          (item, key) => {
            return (
              <div
                key={key}
                onClick={() => submit3(key)}
                className={
                  "flex justify-center w-40 h-40 p-5 content-center text-center bg-darkMain4 pt-10 pb-10 rounded-lg break-normal hover:bg-opacity-75  border-solid border-2 border-darkMain4"
                }
              >
                <div className="text-white">{item}</div>
              </div>
            );
          }
        )}
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
