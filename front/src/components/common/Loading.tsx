import React from "react";
import LoadingGif from "../../assets/loading/cosmos-loading.gif";
import DarkLoadingGif from "../../assets/loading/dark-cosmos-loading.gif";
import LoadingGif2 from "../../assets/loading/modal-loading.gif";
import DarkLoadingGif2 from "../../assets/loading/dark-modal-loading.gif";
import { useRecoilState } from "recoil";
import { darkMode } from "../../recoil/states/UserState";

export default function Loading() {
  const isDark = useRecoilState(darkMode);
  return (
    <div className="h-[calc(100vh-10rem)] text-center flex bg-white justif-center dark:bg-darkBackground">
      <div className="m-auto">
        {/* 다크모드 로딩 로고 변환 */}
        {isDark ? (
          <img src={DarkLoadingGif} alt="logo" className="h-20 w-20 m-auto" />
        ) : (
          <img src={LoadingGif} alt="logo" className="h-20 w-20 m-auto" />
        )}
        <div className="mt-4 text-xl font-medium">
          {isDark ? (
            <img src={DarkLoadingGif2} alt="" />
          ) : (
            <img src={LoadingGif2} alt="" />
          )}
        </div>
      </div>
    </div>
  );
}
