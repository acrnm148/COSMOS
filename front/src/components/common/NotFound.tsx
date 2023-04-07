import React from "react";
import Header from "./Header";
import Footer from "./Footer";
import { useRecoilState } from "recoil";
import { darkMode } from "../../recoil/states/UserState";
import LightLogo from "../../assets/pink-notfound.png";
import DarkLogo from "../../assets/dark-notfound.png";

export default function NotFound() {
  const isDark = useRecoilState(darkMode);
  return (
    <div
      className={
        isDark
          ? "flex flex-col justify-center items-center max-w-[950px] m-auto dark bg-darkBackground"
          : "flex flex-col justify-center items-center max-w-[950px] m-auto"
      }
    >
      <Header />
      <div className="h-screen">
        {isDark ? (
          <img className="mt-10 m-auto" src={DarkLogo} />
        ) : (
          <img src={LightLogo} />
        )}
        <div>
          <p className="text-center text-[50px] font-bold dark:text-white">
            NOT FOUND
          </p>
          <p className="text-center dark:text-white">
            요청하신 페이지를 찾을 수 없습니다
          </p>
        </div>
      </div>
      <Footer />
    </div>
  );
}
