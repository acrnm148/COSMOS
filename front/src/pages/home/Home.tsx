import React, { useState } from "react";
import RecommSlider from "../../components/home/RecommSlider";
import MainMenuBtn from "../../components/home/MainMenuBtn";
import MemoryComponent from "../../components/home/MemoryComponent";
import { useRecoilState } from "recoil";
import { darkMode } from "../../recoil/states/UserState";

export default function Home() {
    const isDark = useRecoilState(darkMode);
    return (
        <div className="pb-24 px-4 mt-8 w-full h-screen">
            <RecommSlider isDark={isDark} />
            <MainMenuBtn />
            <MemoryComponent />
        </div>
    );
}
