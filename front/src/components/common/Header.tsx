import { NavLink } from "react-router-dom";
import { Icon } from "@iconify/react";
import { Badge } from "@mui/material";
import Logo from "../../assets/header/logo.png";
import LogoDark from "../../assets/header/logoDark.png";
import { useRecoilState } from "recoil";
import { darkMode } from "../../recoil/states/UserState";
import Alarm from "./Alarm";

export default function Header() {
    const isDark = useRecoilState(darkMode);

    const badgeStyle = {
        "& .MuiBadge-badge": {
            color: "white",
            backgroundColor: "#FF8E9E",
            borderRadius: "50%",
            fontSize: "17px",
            width: "24px",
            height: "24px",
        },
    };

    const badgeDarkStyle = {
        "& .MuiBadge-badge": {
            color: "white",
            backgroundColor: "#782472",
            borderRadius: "50%",
            fontSize: "17px",
            width: "24px",
            height: "24px",
        },
    };

    return (
        <div
            className={`text-3xl font-bold z-[9999999] h-20 w-screen 
          `}
        >
            {/* <header className="fixed top-0 z-100 left-0 inset-x-0 bg-lightMain4 h-20 w-full flex">
      <div className="logo text-center m-auto h-16"> */}
            <header className="w-screen max-w-[950px] m-auto fixed top-0 z-50 left-0 inset-x-0 bg-lightMain2 h-20 dark:bg-darkMain2">
                <div className="logo absolute left-3 bottom-0 ">
                    {isDark ? (
                        <img src={LogoDark} alt="logo" className="h-20" />
                    ) : (
                        <img src={Logo} alt="logo" className="h-20" />
                    )}
                </div>
                <Alarm />
            </header>
        </div>
    );
}
