import React, { useState } from "react";
import "../../css/listCard.css";
import ArrowDropUpIcon from "@mui/icons-material/ArrowDropUp";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";

export default function ListCard({ children }: any) {
  const [up, setUp] = useState(false);
  const handleDropBtn = () => {
    setUp((cur) => !cur);
    const list = document.querySelector("#listBox") as HTMLElement;
    if (up) {
      list.style.marginTop = "0px";
      list.style.height = "70vh";
    } else {
      list.style.marginTop = "-50vh";
      list.style.height = "120vh";
    }
  };
  return (
    <div className="card">
      <div
        className="mb-[50px] z-[100000] bg-white h-[70vh] relative"
        id="listBox"
      >
        {up ? (
          <ArrowDropDownIcon
            fontSize="large"
            color="disabled"
            onClick={handleDropBtn}
            className="cursor-pointer"
          />
        ) : (
          <ArrowDropUpIcon
            fontSize="large"
            color="disabled"
            onClick={handleDropBtn}
            className="cursor-pointer"
          />
        )}
        {children}
      </div>
    </div>
  );
}
