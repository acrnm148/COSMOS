import React from "react";
import { Outlet } from "react-router-dom";
import Footer from "../components/common/Footer";
import Header from "../components/common/Header";
import { useRecoilState } from "recoil";
import { userState, darkMode } from "../recoil/states/UserState";
import { clickBackground } from "../recoil/states/SearchPageState";

function MainLayout() {
  const [clickBg, setClickBg] = useRecoilState(clickBackground);
  const [user, setUser] = useRecoilState(userState);
  const [isDark, setIsDark] = useRecoilState(darkMode);

  return (
    <div
      onClick={() => setClickBg(true)}
      className={
        isDark
          ? "flex flex-col justify-center items-center max-w-[950px] m-auto dark bg-darkBackground"
          : "flex flex-col justify-center items-center max-w-[950px] m-auto"
      }
    >
      <Header />
      <Outlet />
      <Footer />
    </div>
  );
}

export default MainLayout;
