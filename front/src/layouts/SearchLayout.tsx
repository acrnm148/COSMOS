import React, { useState } from "react";
import { Outlet } from "react-router-dom";
import Footer from "../components/common/Footer";
import Header from "../components/common/Header";
import { useRecoilState } from "recoil";
import { clickBackground } from "../recoil/states/SearchPageState";

function MainLayout() {
  const [clickBg, setClickBg] = useRecoilState(clickBackground);

  return (
    <div
      onClick={() => setClickBg(true)}
      className="flex flex-col justify-center items-center max-w-[950px] m-auto"
    >
      <Header />
      <Outlet />
      <Footer />
    </div>
  );
}

export default MainLayout;
