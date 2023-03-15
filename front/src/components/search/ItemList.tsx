import React, { useState } from "react";
import ListCard from "../common/ListCard";
import like from "../../assets/like.png";
import noLike from "../../assets/no-like.png";

export default function ItemList() {
  const [isLike, setIsLike] = useState(false);

  const handleLikeButton = () => {
    setIsLike((cur) => !cur);
  };
  return (
    <div className="mb-[50px]">
      <ListCard>
        <div className="flex flex-row relative">
          <div className="flex justify-center my-auto basis-3/12">
            <img
              src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRQC0rn6j8ZXwbLsoNdV5CEGem6iXs3JLMuykklndrH&s"
              alt=""
              className="w-[20vw] h-[15vw] max-w-[200px] max-h-[150px] rounded-lg"
            />
          </div>
          <div className="flex flex-col column-3 basis-8/12 text-left">
            <div className="font-bold text-lg">가게명</div>
            <div className="font-thin text-slate-400 text-sm">주소</div>
            <div className="font-bold text-base mt-[5px]">상세설명</div>
          </div>
          <div
            className="basis-1/12 absolute bottom-0 right-5"
            onClick={handleLikeButton}
          >
            {isLike ? (
              <img src={like} alt="" className="w-[10vw] max-w-[60px]" />
            ) : (
              <img src={noLike} alt="" className="w-[10vw] max-w-[60px]" />
            )}
          </div>
        </div>
      </ListCard>
    </div>
  );
}
