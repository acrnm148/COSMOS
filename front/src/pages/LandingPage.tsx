import React, { useState, useEffect, useRef } from "react";
import LightLanding from "../assets/landing/light-landing.gif";
import Login from "../pages/login/Login";
import "./LandingPage.css";
import useScrollSnap from "react-use-scroll-snap";

export default function LandingPage() {
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

    const scrollRef = useRef(null);
    useScrollSnap({ ref: scrollRef, duration: 100, delay: 0 }); // 여기 delay 0을 줬는데도 뭔가 부자연스럽게 내려가는 느낌입니다...

    return (
        <div className="scroll-box scrollbar-hide ScrollSnapWrap" ref={scrollRef}>
            <div className="bg-local w-full SnapDiv">
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

            <section id="register" className="h-screen SnapDiv  ">
                <Login />
            </section>
        </div>
    );
}
