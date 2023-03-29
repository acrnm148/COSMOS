import { NavLink } from "react-router-dom";
import { Icon } from "@iconify/react";
import Cosmos from "../../assets/footer/light-cosmos.png";
import CoupleIcon from "../../assets/footer/couple-icon.png";

export default function Footer() {
    return (
        <div className="text-xl font-bold z-[9999999]">
            <div className="w-screen max-w-[1200px] m-auto fixed bottom-0 z-100 left-0 inset-x-0 bg-white h-20 w-full flex">
                <div className="menuItems m-auto w-full">
                    <div className="float-left w-1/5 flex justify-center">
                        <NavLink to="/mypage">
                            <Icon
                                icon="healthicons:ui-user-profile"
                                color="#ff8e9e"
                                width="45"
                                height="45"
                            />
                        </NavLink>
                    </div>
                    <div className="float-left w-1/5 flex justify-center">
                        <NavLink to="/wish">
                            <Icon
                                icon="mdi:cards-heart"
                                color="#ff8e9e"
                                width="45"
                                height="45"
                            />
                        </NavLink>
                    </div>
                    <div className="float-left w-1/5 flex justify-center">
                        <NavLink to="/">
                            <img
                                src={Cosmos}
                                alt="logo"
                                className="h-12 w-12"
                            />
                        </NavLink>
                    </div>
                    <div className="float-left w-1/5 flex justify-center">
                        <NavLink to="/schedule/month">
                            <img
                                src={CoupleIcon}
                                alt="logo"
                                className="h-10 w-10"
                            />
                        </NavLink>
                    </div>
                    <div className="float-left w-1/5 flex justify-center">
                        <NavLink to="/place/recommend">
                            <Icon
                                icon="material-symbols:temp-preferences-custom"
                                color="#ff8e9e"
                                width="42"
                                height="42"
                            />
                        </NavLink>
                    </div>
                </div>
            </div>{" "}
        </div>
    );
}
