import { serveyChoice, serveyPage } from "../../recoil/states/ServeyPageState";
import { useRecoilState } from "recoil";
import { useState } from "react";

export default function Servey4() {
  const [serveyPg, setServeyPage] = useRecoilState(serveyPage);
  const [serveyCho, setServeyCho] = useRecoilState(serveyChoice);

  function submit9(key: any) {
    let update9 = {};

    if ([0, 2].indexOf(key) == -1) {
      // J형
      update9 = { 9: "Y" };
    } else {
      // E형
      update9 = { 9: "E" };
    }
    setServeyCho((serveyCho) => ({
      ...serveyCho,
      ...update9,
    }));
    setServeyPage(serveyPg + 1);
  }

  return (
    <div className="w-full">
      <h1 className="font-bold font-baloo text-2xl">데이트 취향설문</h1>
      <div className="mt-7 mb-7 w-full text-center">
        <span className="font-baloo text-base text-darkMain">9 / 9</span>
        <div className="border-solid border-2 h-5 border-darkMain w-full">
          <div className="h-full bg-darkMain w-full"></div>
        </div>
      </div>
      <h1 className="font-baloo text-base text-white mb-2">
        9. 당신의 취미는 어디에 더 가깝나요?
      </h1>
      <ul className="flex flex-wrap w-full grid-rows-2 gap-2 justify-center">
        {["운동", "독서", "공연관람", "넷플릭스"].map((item, key) => {
          return (
            <div
              key={key}
              onClick={() => submit9(key)}
              className={
                (key===1?`bg-[url('https://user-images.githubusercontent.com/87971876/230256832-07aa0f43-88d2-4fd1-a8b9-6c0bfc0c3811.png')]`
                : (key===2?`bg-[url('https://user-images.githubusercontent.com/87971876/230256840-72145775-3d07-477b-9295-6fa9c89eb341.png')]`
                  : (key===3?`bg-[url('https://user-images.githubusercontent.com/87971876/230256843-d51ca33a-ff24-4978-8f41-9094fc595e58.png')]`
                    : `bg-[url('https://user-images.githubusercontent.com/87971876/230256847-ad673e13-a212-4750-80b0-6a6418fc994e.png')]`)))
                +"  hover:w-[47%] bg-cover bg-center bg-no-repeat bg-opacity-20 flex justify-center w-[46%] h-40 p-5 content-center text-centerpt-10 pb-10 rounded-lg break-normal border-solid border-2 border-darkMain4"
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
