import { NavLink } from "react-router-dom";
import { Icon } from "@iconify/react";
import Cosmos from "../../assets/footer/light-cosmos.png";
import Cosmos2 from "../../assets/footer/dark-cosmos.png";
import CoupleIcon from "../../assets/footer/couple-icon.png";
import CoupleIcon2 from "../../assets/footer/dark-couple-icon.png";
import { useRecoilState } from "recoil";
import { darkMode } from "../../recoil/states/UserState";

export default function Footer() {
  const isDark = useRecoilState(darkMode);
  return (
    <div className="text-xl font-bold z-[9999999]">
      <div className="dark:bg-darkBackground w-screen max-w-[950px] m-auto fixed bottom-0 z-100 left-0 inset-x-0 bg-white h-20 w-full flex">
        <div className="menuItems m-auto w-full">
          <div className="float-left w-1/5 flex justify-center">
            <NavLink to="/mypage">
              {isDark[0] ? (
                <Icon
                  icon="healthicons:ui-user-profile"
                  color="#9C4395"
                  width="45"
                  height="45"
                />
              ) : (
                <Icon
                  icon="healthicons:ui-user-profile"
                  color="#ff8e9e"
                  width="45"
                  height="45"
                />
              )}
            </NavLink>
          </div>
          <div className="float-left w-1/5 flex justify-center">
            <NavLink to="/wish">
              {isDark[0] ? (
                <Icon
                  icon="mdi:cards-heart"
                  color="#9C4395"
                  width="45"
                  height="45"
                />
              ) : (
                <Icon
                  icon="mdi:cards-heart"
                  color="#ff8e9e"
                  width="45"
                  height="45"
                />
              )}
            </NavLink>
          </div>
          <div className="float-left w-1/5 flex justify-center">
            <NavLink to="/">
              {isDark[0] ? (
                <img src={Cosmos2} alt="logo" className="h-12 w-12" />
              ) : (
                <img src={Cosmos} alt="logo" className="h-12 w-12" />
              )}
            </NavLink>
          </div>
          <div className="float-left w-1/5 flex justify-center">
            <NavLink to="/schedule/month">
              {isDark[0] ? (
                <img src={CoupleIcon2} alt="logo" className="h-10 w-10" />
              ) : (
                <img src={CoupleIcon} alt="logo" className="h-10 w-10" />
              )}
            </NavLink>
          </div>
          <div className="float-left w-1/5 flex justify-center">
            <NavLink to="/place/recommend">
              {isDark[0] ? (
                <Icon
                  icon="material-symbols:temp-preferences-custom"
                  color="#9C4395"
                  width="42"
                  height="42"
                />
              ) : (
                <Icon
                  icon="material-symbols:temp-preferences-custom"
                  color="#ff8e9e"
                  width="42"
                  height="42"
                />
              )}
            </NavLink>
          </div>
        </div>
      </div>{" "}
    </div>
  );
}
