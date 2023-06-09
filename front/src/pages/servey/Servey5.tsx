import { serveyChoice, serveyPage } from "../../recoil/states/ServeyPageState";
import { useRecoilState } from "recoil";
import { useState } from "react";

export default function Servey4() {
  const [serveyPg, setServeyPage] = useRecoilState(serveyPage);
  const [serveyCho, setServeyCho] = useRecoilState(serveyChoice);

  function submit5(key: any) {
    let update5 = {};

    if (key == 0) {
      // E형
      update5 = { 5: "E" };
    } else {
      // J형
      update5 = { 5: "J" };
    }
    setServeyCho((serveyCho) => ({
      ...serveyCho,
      ...update5,
    }));
    setServeyPage(serveyPg + 1);
  }

  return (
    <div className="w-full">
      <h1 className="font-bold font-baloo text-2xl">데이트 취향설문</h1>
      <div className="mt-7 mb-7 w-full text-center ">
        <span className="font-baloo text-base text-darkMain">5 / 9</span>
        <div className="border-solid border-2 h-5 border-darkMain w-full">
          <div className="h-full bg-darkMain w-6/12"></div>
        </div>
      </div>
      <h1 className="font-baloo text-base text-white mb-2">
        5. 주로 만나는 데이트 시간대는?
      </h1>
      <ul className="flex flex-wrap w-full grid-rows-2 gap-2 justify-center">
        {["낮", "밤"].map((item, key) => {
          return (
            <div
              key={key}
              onClick={() => submit5(key)}
              
              className={(key==0?'bg-[url("https://user-images.githubusercontent.com/87971876/230255534-310185f2-300c-4b5e-8ac3-89755bb13d0d.png")] '
              :'bg-[url("https://user-images.githubusercontent.com/87971876/230255025-6ca74260-4a03-42bc-bfed-b13af5385aed.png")] ')
                + " bg-cover bg-center bg-no-repeat bg-opacity-20 flex justify-center w-full h-44 p-5 content-center text-center pt-10 pb-10 rounded-lg break-normal hover:border-4  border-solid border-2 border-darkMain4"
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
