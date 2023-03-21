import React, { useState } from "react";
import Cinema from "../../assets/place/cinema.png";
import Cutlery from "../../assets/place/cutlery.png";
import Coffee from "../../assets/place/coffee-cup.png";
import Shopping from "../../assets/place/shopping-cart.png";
import Gym from "../../assets/place/gym.png";
import Suitcase from "../../assets/place/suitcase.png";
import LoveIcon from "../../assets/place/love-icon.png";

export default function SelectCategoryItem({ tags }: any) {
  const [tag, setTag] = useState(tags);

  return (
    <div className="flex flex-row justify-center gap-[20px]">
      <span className="icon-span">
        <img className="love-icon" src={LoveIcon} alt="" />
      </span>
      <div className="modal-content">
        <div
          className={tag[0] ? "content-clicked 0" : ""}
          onClick={() => setTag([true, false, false, false, false, false])}
        >
          <img
            src={Cutlery}
            alt=""
            style={tag[0] ? { opacity: 1 } : { opacity: 0.3 }}
          />
          <p>음식</p>
        </div>
        <div
          className={tag[1] ? "content-clicked 1" : ""}
          onClick={() => setTag([false, true, false, false, false, false])}
        >
          <img
            src={Coffee}
            alt=""
            style={tag[1] ? { opacity: 1 } : { opacity: 0.3 }}
          />
          <p>카페</p>
        </div>
        <div
          className={tag[2] ? "content-clicked 2" : ""}
          onClick={() => setTag([false, false, true, false, false, false])}
        >
          <img
            src={Cinema}
            alt=""
            style={tag[2] ? { opacity: 1 } : { opacity: 0.3 }}
          />
          <p>문화</p>
        </div>
        <div
          className={tag[3] ? "content-clicked 3" : ""}
          onClick={() => setTag([false, false, false, true, false, false])}
        >
          <img
            src={Shopping}
            alt=""
            style={tag[3] ? { opacity: 1 } : { opacity: 0.3 }}
          />
          <p>쇼핑</p>
        </div>
        <div
          className={tag[4] ? "content-clicked 4" : ""}
          onClick={() => setTag([false, false, false, false, true, false])}
        >
          <img
            src={Suitcase}
            alt=""
            style={tag[4] ? { opacity: 1 } : { opacity: 0.3 }}
          />
          <p>관광</p>
        </div>
        <div
          className={tag[5] ? "content-clicked 5" : ""}
          onClick={() => setTag([false, false, false, false, false, true])}
        >
          <img
            src={Gym}
            alt=""
            style={tag[5] ? { opacity: 1 } : { opacity: 0.3 }}
          />
          <p>운동</p>
        </div>
      </div>
    </div>
  );
}
