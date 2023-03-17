import React, { useState, useEffect, useRef } from "react";
import LightLanding from "../assets/landing/light-landing.gif";
import Login from "../pages/login/Login";
import "./LandingPage.css";
import useScrollSnap from "react-use-scroll-snap";

export default function LandingPage() {
    const outerDivRef = React.useRef() as React.MutableRefObject<HTMLDivElement>;
    const firstComponentHeight = React.useRef() as React.MutableRefObject<HTMLDivElement>; // 랜딩 사진 높이
    let lastScrollY: number = 0;

    useEffect(() => {
        const wheelHandler = () => {
            // e.preventDefault();
            const currentY = window.scrollY; // 스크롤 위쪽 끝부분 위치
            const deltaY: number = currentY > lastScrollY ? 1 : -1; // 스크롤 방향 감지 (위/아래)
            lastScrollY = currentY;
            const pageHeight = window.innerHeight; // 페이지 높이 (vh)
            const firstHeight = firstComponentHeight.current.offsetHeight; // 랜딩페이지 높이

            if (deltaY > 0) {
                // 스크롤 down
                if (currentY >= 0 && currentY < firstHeight) {
                    // 1페이지
                    console.log("현재: 1페이지 | down");

                    window.scrollTo({
                        top: firstHeight,
                        left: 0,
                        behavior: "smooth",
                    });
                } else if (currentY >= firstHeight && currentY < pageHeight + firstHeight) {
                    // 2페이지
                    console.log("현재: 2페이지 | down");

                    window.scrollTo({
                        top: pageHeight + firstHeight,
                        left: 0,
                        behavior: "smooth",
                    });
                }
            } else {
                // 스크롤 up
                if (currentY >= 0 && currentY < firstHeight) {
                    // 1페이지
                    console.log("현재: 1페이지 | up");

                    window.scrollTo({
                        top: 0,
                        left: 0,
                        behavior: "smooth",
                    });
                } else if (currentY >= firstHeight && currentY < pageHeight + firstHeight) {
                    // 2페이지
                    console.log("현재: 2페이지 | up");

                    window.scrollTo({
                        top: 0,
                        left: 0,
                        behavior: "smooth",
                    });
                }
            }
        };

        const outerDivRefCurrent = outerDivRef.current;
        outerDivRefCurrent && outerDivRefCurrent.addEventListener("wheel", wheelHandler);
        return () => {
            outerDivRefCurrent && outerDivRefCurrent.removeEventListener("wheel", wheelHandler);
        };
    });

    // function onScroll() {
    //     console.log(window.scrollY);
    //     if (800 < window.scrollY && window.scrollY < 1300) {
    //     }
    // }

    // useEffect(() => {
    //     window.addEventListener("scroll", onScroll);
    //     return () => {
    //         window.removeEventListener("scroll", onScroll);
    //     };
    // }, []);

    // const scrollRef = useRef(null);
    // useScrollSnap({ ref: scrollRef, duration: 100, delay: 0 }); // 여기 delay 0을 줬는데도 뭔가 부자연스럽게 내려가는 느낌입니다...

    return (
        <div className="scroll-box scrollbar-hide ScrollSnapWrap outer" ref={outerDivRef}>
            <div className="bg-local w-full SnapDiv inner" ref={firstComponentHeight}>
                <img src={LightLanding} alt="landing" width="100%" />
                <div className="lottie w-full bottom-10 fixed">
                    <a href="#register">
                        <iframe
                            src="https://embed.lottiefiles.com/animation/111636"
                            className="w-full"
                        ></iframe>
                    </a>
                </div>
            </div>

            <section id="register" className="h-screen SnapDiv inner inner2">
                <Login />
            </section>
        </div>
    );
}
