import { serveyChoice, serveyPage } from "../../recoil/states/ServeyPageState";
import { useRecoilState } from "recoil";
import { useState } from "react";

export default function Servey4() {
  const [serveyPg, setServeyPage] = useRecoilState(serveyPage);
  const [serveyCho, setServeyCho] = useRecoilState(serveyChoice);

  function submit7(key: any) {
    let update7 = {};

    if (key == 1) {
      // A형
      update7 = { 7: "A" };
    } else {
      // O형
      update7 = { 7: "O" };
    }
    setServeyPage(serveyPg + 1);
    setServeyCho((serveyCho) => ({
      ...serveyCho,
      ...update7,
    }));
  }

  return (
    <div className="w-full">
      <h1 className="font-bold font-baloo text-2xl">데이트 취향설문</h1>
      <div className="mt-7 mb-7 w-full text-center">
        <span className="font-baloo text-base text-darkMain">7 / 9</span>
        <div className="border-solid border-2 h-5 border-darkMain w-full">
          <div className="h-full bg-darkMain w-9/12"></div>
        </div>
      </div>
      <h1 className="font-baloo text-base text-white mb-2">
        7. 커플 여행 시 숙소 선택의 기준은?
      </h1>
      <ul className="flex flex-wrap w-full grid-rows-2 gap-2 justify-center">
        {["뷰 & 인테리어가 예쁜 곳", "관광지 중심의 접근성이 좋은 곳"].map(
          (item, key) => {
            return (
              <div
                key={key}
                onClick={() => submit7(key)}
                className={(key===1?`bg-[url('https://user-images.githubusercontent.com/87971876/230256539-363f6c1d-817c-4279-9b2f-034e7666a6a1.png')]`
                :`bg-[url('https://user-images.githubusercontent.com/87971876/230256544-cc059b2e-5208-4099-bb29-270cf76b6d03.png')]`)
                + " bg-opacity-50 hover:bg-opacity-75 bg-cover bg-center bg-no-repeat bg-opacity-20 flex justify-center w-full h-44 p-5 content-center text-center pt-10 pb-10 rounded-lg break-normal hover:border-4  border-solid border-2 border-darkMain4"
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
