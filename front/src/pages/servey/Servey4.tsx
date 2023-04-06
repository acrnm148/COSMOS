import { serveyChoice, serveyPage } from "../../recoil/states/ServeyPageState";
import { useRecoilState } from "recoil";
import { useState } from "react";

export default function Servey4() {
  const [serveyPg, setServeyPage] = useRecoilState(serveyPage);
  const [serveyCho, setServeyCho] = useRecoilState(serveyChoice);

  function submit4(key: any) {
    let update4 = {};

    if (key == 0) {
      // Y형
      update4 = { 4: "Y" };
    } else {
      // T형
      update4 = { 4: "T" };
    }
    setServeyCho((serveyCho) => ({
      ...serveyCho,
      ...update4,
    }));
    setServeyPage(serveyPg + 1);
  }

  return (
    <div className="w-full">
      <h1 className="w-full font-bold font-baloo text-2xl">데이트 취향설문</h1>
      <div className="mt-7 mb-7 w-full text-center">
        <span className="font-baloo text-base text-darkMain">4 / 9</span>
        <div className="border-solid border-2 h-5 border-darkMain w-full">
          <div className="h-full bg-darkMain w-4/12"></div>
        </div>
      </div>
      <h1 className="font-baloo text-base text-white mb-2">
        4. 선호하는 데이트장소?
      </h1>
      <ul className="flex flex-wrap w-full grid-rows-2 gap-2 justify-center">
        {["늘 새로운 야외데이트", "편안한 집 데이트"].map((item, key) => {
          return (
            <div
              key={key}
              onClick={() => submit4(key)}
              
              className={(key===1?`bg-[url('https://user-images.githubusercontent.com/87971876/230253466-1344da5c-e4f6-413f-8f24-39fa47400635.png')]`
              :`bg-[url('https://user-images.githubusercontent.com/87971876/230253581-c81d6249-87b9-45c9-8988-6745c247677e.png')]`)
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
