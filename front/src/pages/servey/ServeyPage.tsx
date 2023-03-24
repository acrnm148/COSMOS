import { serveyChoice, serveyPage } from "../../recoil/states/ServeyPageState";
import { useRecoilState, useRecoilValue } from "recoil";

import Servey1 from "./Servey1";
import Servey2 from "./Servey2";
import Servey3 from "./Servey3";
import Servey4 from "./Servey4";
import Servey5 from "./Servey5";
import Servey6 from "./Servey6";
import Servey7 from "./Servey7";
import Servey8 from "./Servey8";
import Servey9 from "./Servey9";
import { useEffect, useState } from "react";
import { Navigate, useNavigate } from "react-router-dom";

export default function ServeyPage() {
  const [serveyPg, setServeyPage] = useRecoilState(serveyPage);
  const serveyChoices = useRecoilState(serveyChoice);

  const navigate = useNavigate();
  const [cate, setCate] = useState("");
  const [cateNumCode, setCateNumCode] = useState("");

  // 설문조사 결과
  useEffect(() => {
    if (serveyPg === 10) {
      console.log("여기는 10페이지");
      console.log("serveyChoices", serveyChoices); // {1: 'J', 2: 'O', 3: 'Y', 4: 'T', 5: 'E', 6: 'O', 7: 'O', 8: 'Y', 9: 'E'}
      console.log("typeof(serveyChoices)", typeof serveyChoices);
      // serveyChoices에 담긴 알파벳 값 카운트 해서 result 생성
      const sc = serveyChoices[0];
      let result = { E: 0, J: 0, A: 0, O: 0, Y: 0, T: 0 };
      for (const [key, value] of Object.entries(sc)) {
        if (value === "E") {
          result["E"] += 1;
        } else if (value === "J") {
          result["J"] += 1;
        } else if (value === "A") {
          result["A"] += 1;
        } else if (value === "O") {
          result["O"] += 1;
        } else if (value === "Y") {
          result["Y"] += 1;
        } else if (value === "T") {
          result["T"] += 1;
        }
      }
      console.log("result", result);
      setCate(() => {
        let firstLetter = result["E"] > result["J"] ? "E" : "J";
        let firstCode = result["E"] > result["J"] ? result["E"] : result["J"];

        let secondLetter = result["O"] > result["A"] ? "O" : "A";
        let secondCode = result["O"] > result["A"] ? result["O"] : result["A"];

        let thirdLetter = result["Y"] > result["T"] ? "Y" : "T";
        let thirdCode = result["Y"] > result["T"] ? result["Y"] : result["T"];

        setCateNumCode(
          String(firstCode) + String(secondCode) + String(thirdCode)
        );
        return firstLetter + secondLetter + thirdLetter;
      });
    }
  }, [serveyPg]);
  useEffect(() => {
    if (serveyPg === 10 && cate && cateNumCode) {
      setTimeout(() => {
        console.log("cate", cate, "cateNumCode", cateNumCode);
        navigate(`/result/${cate}/${cateNumCode}`);
      }, 2000);
    }
  }, [cate, cateNumCode]);

  function submit() {
    setServeyPage(serveyPg + 1);
  }
  return (
    <>
      <div className="h-screen w-full max-w-[500px] m-auto p-6 bg-darkBackground place-content-center text-darkMain m-auto">
        <p>현재페이지 : {serveyPg}</p> {/* 삭제예정 */}
        <div className="flex content-center w-full items-start h-full">
          {serveyPg === 0 && (
            <div className="flex h-full flex-col">
              <span className="font-baloo text-base">
                설문을 기반으로 데이트코스를 맞춤 추천해드려요.
              </span>
              <button
                onClick={submit}
                className="w-full flex h-12 border-solid border-2 border-darkMain justify-center p-3 text-center rounded-lg w-full 
                                hover:bg-darkMain hover:text-white
                                "
              >
                설문 시작하기
              </button>
            </div>
          )}
          {serveyPg === 1 && <Servey1 />}
          {serveyPg === 2 && <Servey2 />}
          {serveyPg === 3 && <Servey3 />}
          {serveyPg === 4 && <Servey4 />}
          {serveyPg === 5 && <Servey5 />}
          {serveyPg === 6 && <Servey6 />}
          {serveyPg === 7 && <Servey7 />}
          {serveyPg === 8 && <Servey8 />}
          {serveyPg === 9 && <Servey9 />}
          {serveyPg === 10 && (
            <div className="flex w-screen h-screen justify-center items-center flex-col">
              <iframe src="https://embed.lottiefiles.com/animation/101474"></iframe>
              <h1 className="font-bold font-baloo text-2xl mb-3">
                데이트 취향설문 결과 분석중
              </h1>
            </div>
          )}
        </div>
      </div>
    </>
  );
}

///////////////// tailwind 반응형 참고용 /////////////////

{
  /* <div className="bg-white overflow-hidden rounded-3xl shadow-xl">
    <div className="bg-blue-500 p-6 pb-14">
    <span className="text-white text-2xl">프로필</span>
    </div>
    <div className="rounded-3xl p-6 bg-white relative -top-5">
    <div className="flex relative -top-16 items-end justify-between">
        <div className="flex flex-col items-center">
        <span className="text-xs text-gray-500">Orders</span>
        <span className="font-medium">340</span>
        </div>
        <div className="h-24 w-24 bg-zinc-300 rounded-full" />
        <div className="flex flex-col items-center">
        <span className="text-xs text-gray-500">Spent</span>
        <span className="font-medium">$340</span>
        </div>
    </div>
    <div className="relative  flex flex-col items-center -mt-14 -mb-5">
        <span className="text-lg font-medium">KKANA KANA</span>
        <span className="text-sm text-gray-500">KANA</span>
    </div>
    </div>
</div>
<div className="bg-white p-6 rounded-3xl shadow-xl">
    <div className="flex mb-5 justify-between items-center">
    <span>⬅️</span>
    <div className="space-x-3">
        <span>😀4.9</span>
        <span className="shadow-xl p-2 rounded-md">💖</span>
    </div>
    </div>
    <div className="bg-zinc-400 h-72 mb-5" />
    <div className="flex flex-col">
    <span className="font-medium text-xl">맛있는 음식</span>
    <span className="text-xs text-gray-500">Chair</span>
    <div className="mt-3 mb-5 flex justify-between items-center">
        <div className="space-x-2">
        <button className="w-5 h-5 rounded-full bg-red-500 focus:ring-2 ring-offset-2 ring-red-500 transition" />
        <button className="w-5 h-5 rounded-full bg-indigo-500 focus:ring-2 ring-offset-2 ring-indigo-500 transition" />
        <button className="w-5 h-5 rounded-full bg-violet-600 focus:ring-2 ring-offset-2 ring-violet-500 transition" />
        </div>
        <div className="flex items-center space-x-5">
        <button className="rounded-lg bg-blue-200 flex justify-center items-center aspect-square w-8 text-xl text-gray-500">
            -
        </button>
        <span>1</span>
        <button className="rounded-lg bg-blue-200 flex justify-center items-center aspect-square w-8 text-xl text-gray-500">
            +
        </button>
        </div>
    </div>
    </div>
    <div className="flex justify-around items-center">
    <span className="font-medium text-2xl">$450</span>
    <button className="bg-blue-500 p-2 px-5 text-center text-xs text-white rounded-lg">
        Add to cart
    </button>
    </div>
</div> */
}
