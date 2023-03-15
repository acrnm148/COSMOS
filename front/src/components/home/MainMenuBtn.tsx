import { NavLink } from "react-router-dom";
import { Icon } from "@iconify/react";
import MainSearch from "../../assets/home/main-search.png";
import CoupleBike from "../../assets/home/couple-bike.png";

export default function MainMenuBtn() {
  return (
    <div className="btns w-full h-44 pt-3 justify-center">
      <div className="w-[170px] mr-3 h-full relative float-left p-4 bg-lightMain4 rounded-2xl drop-shadow-lg">
        <div className="title font-bold">
          맞춤 코스 추천{" "}
          <Icon
            icon="ic:outline-keyboard-arrow-right"
            width="22"
            height="25"
            className="inline pb-1"
          />
        </div>
        <NavLink to="/" className="absolute bottom-3 block m-auto">
          <img src={CoupleBike} alt="recomm-btn" className="h-24 w-36" />
        </NavLink>
      </div>

      <div className="w-[170px] ml-1 h-full float-left relative p-4 bg-lightMain4 rounded-2xl drop-shadow-lg">
        <div className="title font-bold">
          장소 검색
          <Icon
            icon="ic:outline-keyboard-arrow-right"
            width="22"
            height="25"
            className="inline pb-1"
          />
        </div>
        <NavLink to="/" className="absolute bottom-3 right-5 block m-auto">
          <img src={MainSearch} alt="recomm-btn" className="h-20 w-28" />
        </NavLink>
      </div>
    </div>
  );
}
