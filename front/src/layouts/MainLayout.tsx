import React, { useEffect } from "react";
import { createContext } from "react";
import { Outlet, useFetcher } from "react-router-dom";
import { useRecoilState } from "recoil";
import Footer from "../components/common/Footer";
import Header from "../components/common/Header";
import { userState, darkMode } from "../recoil/states/UserState";
import { MutableRefObject, useRef, useState } from "react";

export const UserDispatch = createContext<null | string>(null);
function MainLayout() {
  const [isDark, setIsDark] = useRecoilState(darkMode);
  const [scrollAmt, setScrollAmt] = useState<number>();
  const [lastScroll, setLastScroll] = useState<number>(0);
  const targetRef = useRef<HTMLInputElement>(null);
  const handleScroll = () => {
    // console.log("scrolling", window.scrollY);
    setScrollAmt(window.scrollY);

    // if (scrollAmt && (scrollAmt > 0) && targetRef.current) {
    //   targetRef.current.style.position = "fixed";
    // }
    if (scrollAmt && scrollAmt >= 100 && targetRef.current) {
      console.log(scrollAmt, lastScroll);
      if (scrollAmt > lastScroll && lastScroll > 0) {
        targetRef.current.style.top = "-100px";
        console.log(targetRef);
      } else {
        targetRef.current.style.top = "0px";
      }
      setLastScroll(scrollAmt);
    }
  };

  useEffect(() => {
    const timer = setInterval(() => {
      window.addEventListener("scroll", handleScroll);
    }, 100);
    return () => {
      clearInterval(timer);
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);
  const [user, setUser] = useRecoilState(userState);
  return (
    <div
      className={
        isDark
          ? "flex flex-col justify-center items-center max-w-[950px] m-auto dark bg-darkBackground"
          : "flex flex-col justify-center items-center max-w-[950px] m-auto"
      }
    >
      <div
        ref={targetRef}
        className="scroll position-fixed left-[50%] z-index-50 px-[10px] top-1.5 py-0 align-center transform:-translate-y-[50%] transition-all transition-duration:150ms"
      >
        <Header />
      </div>
      <UserDispatch.Provider value={user.acToken}>
        <Outlet />
      </UserDispatch.Provider>
      <Footer />
    </div>
  );
}

export default MainLayout;
