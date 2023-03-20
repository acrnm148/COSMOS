import React, { useRef, useState, useEffect } from "react";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import SelectCategoryItem from "./SelectCategoryItem";

export default function SelectCategory() {
  const categoryRef = useRef<HTMLDivElement>(null);
  const [number, setNumber] = useState(1);
  const [list, setList] = useState([]);
  const tags = [false, false, false, false, false, false];
  const handleClickBtn = () => {
    setNumber((cur) => cur + 1);
  };

  useEffect(() => {}, [number]);

  return (
    <div className="mb-[90px]">
      <div
        className="text-right cursor-pointer"
        title="추가"
        onClick={handleClickBtn}
      >
        <AddCircleIcon sx={{ color: "#FF8E9E", fontSize: 40 }} />
      </div>
      <div ref={categoryRef}>
        <SelectCategoryItem number={number} tags={tags} />
      </div>
    </div>
  );
}
