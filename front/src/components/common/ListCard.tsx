import React, { useState } from "react";
import "../../css/listCard.css";
import ArrowDropUpIcon from "@mui/icons-material/ArrowDropUp";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";
import { useRecoilState } from "recoil";
import { darkMode } from "../../recoil/states/UserState";

export default function ListCard({ children, height }: any | boolean) {
  const isDark = useRecoilState(darkMode);
  const [up, setUp] = useState(false);
  const handleDropBtn = () => {
    setUp((cur) => !cur);
    const list = document.querySelector("#listBox") as HTMLElement;
    if (up) {
      list.style.marginTop = "0px";
      height && (list.style.height = "70vh");
    } else {
      list.style.marginTop = "-50vh";
      height && (list.style.height = "120vh");
    }
  };

  return (
    <div
      className={
        "card mb-[80px] z-[100000] bg-white relative overflow-auto dark:bg-darkBackground" +
        (height ? " h-[70vh]" : "")
      }
      id="listBox"
    >
      {up ? (
        <ArrowDropDownIcon
          fontSize="large"
          color={isDark ? "secondary" : "disabled"}
          onClick={handleDropBtn}
          className="cursor-pointer"
        />
      ) : (
        <ArrowDropUpIcon
          fontSize="large"
          color={isDark ? "secondary" : "disabled"}
          onClick={handleDropBtn}
          className="cursor-pointer"
        />
      )}
      {children}
    </div>
  );
}
