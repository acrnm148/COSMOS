import React, { useState } from "react";
import Cinema from "../../../../assets/place/cinema.png";
import Cutlery from "../../../../assets/place/cutlery.png";
import Coffee from "../../../../assets/place/coffee-cup.png";
import Shopping from "../../../../assets/place/shopping-cart.png";
import Gym from "../../../../assets/place/gym.png";
import Suitcase from "../../../../assets/place/suitcase.png";
import DarkCinema from "../../../../assets/place/dark/cinema.png";
import DarkCutlery from "../../../../assets/place/dark/cutlery.png";
import DarkCoffee from "../../../../assets/place/dark/coffee-cup.png";
import DarkShopping from "../../../../assets/place/dark/shopping-cart.png";
import DarkGym from "../../../../assets/place/dark/gym.png";
import DarkSuitcase from "../../../../assets/place/dark/suitcase.png";
import LoveIcon from "../../../../assets/place/love-icon.png";
import DarkLoveIcon from "../../../../assets/place/dark/love-icon.png";
import { useRecoilState } from "recoil";
import { darkMode } from "../../../../recoil/states/UserState";

export default function SelectCategoryItem({ tags, index }: any) {
  const isDark = useRecoilState(darkMode);
  const [tag, setTag] = useState(tags);

  return (
    <div className="flex flex-row justify-center gap-[20px]" key={index}>
      <span className="icon-span">
        {isDark ? (
          <img className="love-icon" src={DarkLoveIcon} alt="" />
        ) : (
          <img className="love-icon" src={LoveIcon} alt="" />
        )}
      </span>
      <div className="modal-content">
        <div
          className={
            tag[0]
              ? isDark
                ? "content-clicked 0 darkMode"
                : "content-clicked 0"
              : ""
          }
          onClick={() => setTag([true, false, false, false, false, false])}
        >
          {isDark ? (
            <img
              src={DarkCutlery}
              alt=""
              style={tag[0] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          ) : (
            <img
              src={Cutlery}
              alt=""
              style={tag[0] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          )}
          <p>음식</p>
        </div>
        <div
          className={
            tag[1]
              ? isDark
                ? "content-clicked 1 darkMode"
                : "content-clicked 1"
              : ""
          }
          onClick={() => setTag([false, true, false, false, false, false])}
        >
          {isDark ? (
            <img
              src={DarkCoffee}
              alt=""
              style={tag[1] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          ) : (
            <img
              src={Coffee}
              alt=""
              style={tag[1] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          )}
          <p>카페</p>
        </div>
        <div
          className={
            tag[2]
              ? isDark
                ? "content-clicked 2 darkMode"
                : "content-clicked 2"
              : ""
          }
          onClick={() => setTag([false, false, true, false, false, false])}
        >
          {isDark ? (
            <img
              src={DarkCinema}
              alt=""
              style={tag[2] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          ) : (
            <img
              src={Cinema}
              alt=""
              style={tag[2] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          )}
          <p>문화</p>
        </div>
        <div
          className={
            tag[3]
              ? isDark
                ? "content-clicked 3 darkMode"
                : "content-clicked 3"
              : ""
          }
          onClick={() => setTag([false, false, false, true, false, false])}
        >
          {isDark ? (
            <img
              src={DarkShopping}
              alt=""
              style={tag[3] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          ) : (
            <img
              src={Shopping}
              alt=""
              style={tag[3] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          )}
          <p>쇼핑</p>
        </div>
        <div
          className={
            tag[4]
              ? isDark
                ? "content-clicked 4 darkMode"
                : "content-clicked 4"
              : ""
          }
          onClick={() => setTag([false, false, false, false, true, false])}
        >
          {isDark ? (
            <img
              src={DarkSuitcase}
              alt=""
              style={tag[4] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          ) : (
            <img
              src={Suitcase}
              alt=""
              style={tag[4] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          )}
          <p>관광</p>
        </div>
        <div
          className={
            tag[5]
              ? isDark
                ? "content-clicked 5 darkMode"
                : "content-clicked 5"
              : ""
          }
          onClick={() => setTag([false, false, false, false, false, true])}
        >
          {isDark ? (
            <img
              src={DarkGym}
              alt=""
              style={tag[5] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          ) : (
            <img
              src={Gym}
              alt=""
              style={tag[5] ? { opacity: 1 } : { opacity: 0.3 }}
            />
          )}
          <p>운동</p>
        </div>
      </div>
    </div>
  );
}
