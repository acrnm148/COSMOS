import React, { useState, useEffect } from "react";
import LightLanding from "../assets/landing/light-landing.gif";
import Login from "../pages/login/Login";
import "./LandingPage.css";

export default function LandingPage() {
  // function onScroll() {
  //   console.log(window.scrollY);
  //   if (window.scrollY > 615) window.scrollTo({ left: 0, top: 1450, behavior: "smooth" });
  // }

  // useEffect(() => {
  //   window.addEventListener("scroll", onScroll);
  //   return () => {
  //     window.removeEventListener("scroll", onScroll);
  //   };
  // }, []);

  return (
    <div className="scroll-box scrollbar-hide">
      <div className="bg-local w-full">
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

      <section id="register" className="h-screen">
        <Login />
      </section>
    </div>
  );
}
