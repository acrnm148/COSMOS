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
    const [user, setUser] = useRecoilState(userState);

    return (
        <div
            className={
                isDark
                    ? "flex flex-col justify-center items-center max-w-[950px] m-auto dark bg-darkBackground"
                    : "flex flex-col justify-center items-center max-w-[950px] m-auto"
            }
        >
            <Header />
            <UserDispatch.Provider value={user.acToken}>
                <Outlet />
            </UserDispatch.Provider>
            <Footer />
        </div>
    );
}

export default MainLayout;
