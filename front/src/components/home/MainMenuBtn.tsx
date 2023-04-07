import { NavLink, useNavigate } from "react-router-dom";
import { Icon } from "@iconify/react";
import MainSearch from "../../assets/home/main-search.png";
import DarkMainSearch from "../../assets/home/dark-main-search.png";
import SoloBike from "../../assets/home/solo-bike.png";
import CoupleBike from "../../assets/home/couple-bike.png";
import { useRecoilState } from "recoil";
import { darkMode } from "../../recoil/states/UserState";

export default function MainMenuBtn() {
    const isDark = useRecoilState(darkMode)[0];
    const navigate = useNavigate();

    return (
        <div className="flex flex-row btns w-full h-44 pt-3 justify-center">
            <div
                className="basis-1/2 mr-3 h-full relative float-left p-4 bg-lightMain4 rounded-2xl drop-shadow-lg dark:bg-darkMain3"
                onClick={() => navigate("/place/recommend")}
            >
                <div className="title font-bold dark:text-white">
                    맞춤 코스 추천{" "}
                    <Icon
                        icon="ic:outline-keyboard-arrow-right"
                        width="22"
                        height="25"
                        className="inline pb-1"
                    />
                </div>
                <div className="absolute bottom-3 block m-auto">
                    {isDark ? (
                        <img
                            src={SoloBike}
                            alt="recomm-btn"
                            className="h-24 w-36"
                        />
                    ) : (
                        <img
                            src={CoupleBike}
                            alt="recomm-btn"
                            className="h-24 w-36"
                        />
                    )}
                </div>
            </div>

            <div
                className="basis-1/2 ml-1 h-full float-left relative p-4 bg-lightMain4 rounded-2xl drop-shadow-lg dark:bg-darkMain3"
                onClick={() => navigate("/place/search")}
            >
                <div className="title font-bold dark:text-white">
                    장소 검색
                    <Icon
                        icon="ic:outline-keyboard-arrow-right"
                        width="22"
                        height="25"
                        className="inline pb-1"
                    />
                </div>
                <div className="absolute bottom-3 right-5 block m-auto">
                    {isDark ? (
                        <img
                            src={DarkMainSearch}
                            alt="recomm-btn"
                            className="h-20 w-28"
                        />
                    ) : (
                        <img
                            src={MainSearch}
                            alt="recomm-btn"
                            className="h-20 w-28"
                        />
                    )}
                </div>
            </div>
        </div>
    );
}
