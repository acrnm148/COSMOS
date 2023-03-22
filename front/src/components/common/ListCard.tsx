import React, { useState } from "react";
import "../../css/listCard.css";
import ArrowDropUpIcon from "@mui/icons-material/ArrowDropUp";
import ArrowDropDownIcon from "@mui/icons-material/ArrowDropDown";

export default function ListCard({ children, height }: string | any) {
    const [up, setUp] = useState(false);
    const handleDropBtn = () => {
        setUp((cur) => !cur);
        const list = document.querySelector("#listBox") as HTMLElement;
        if (up) {
            list.style.marginTop = "0px";
            // list.style.height = "70vh";
        } else {
            list.style.marginTop = "-50vh";
            // list.style.height = "120vh";
            if (typeof height === "string") list.style.height = `${height}vh`;
        }
    };

    const cardHeight = Number(height) - 50;
    console.log(height);

    return (
        <div className="card">
            <div
                className={
                    "mb-[50px] z-[100000] bg-white relative h-[" +
                    cardHeight +
                    "vh]"
                }
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
