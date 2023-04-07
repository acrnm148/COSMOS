import React, { useState } from "react";
import { useQuery } from "react-query";
import { getReviewOurs } from "../../apis/api/place";
import { useRecoilState } from "recoil";
import { userState, darkMode } from "../../recoil/states/UserState";
import StarIcon from "@mui/icons-material/Star";

export interface REVIEW {
  reviewId: number | undefined;
  categories: [{ id: number; reviewCategoryCode: "string" }] | undefined;
  indiReviewCategories: [{ id: number; reviewCategory: "string" }] | undefined;
  score: number;
  contents: string | undefined;
  placeId: number;
  images:
    | [
        {
          id: number;
          imageUrl: string | undefined;
          coupleId: number | undefined;
          createdTime: string;
        }
      ]
    | undefined;
  contentsOpen: boolean;
  imageOpen: boolean;
  nickname: string | undefined;
  createdTime: string | undefined;
}

export default function ReviewOurs(props:{placeId:any , sol:boolean}) {
  const isDark = useRecoilState(darkMode);
  const LIMIT = 5;
  const [offset, setOffset] = useState(0);
  const userSeq = useRecoilState(userState);


  const { data, isLoading } = useQuery({
    queryKey: ["getReviewOurs", props.placeId, offset],
    queryFn: () =>
      getReviewOurs(
        userSeq[0].seq,
        props.placeId,
        userSeq[0].coupleId,
        LIMIT,
        offset
      ),
  });

  if (isLoading || data === undefined) return null;

  return (
    <div>
      {data === "" || data.length === 0 ? (
        <div className="p-3 font-bold dark:text-white">
          등록된 리뷰가 존재하지 않습니다.
        </div>
      ) : (
        data.map((item: REVIEW) => {
          return (
            <div
              className={(isDark? "bg-darkBackground2-[40%] " :"bg-[#f8f8f8]") +" p-3 py-3 dark:bg-darkBackground2 "}
              key={item.reviewId}
            >
              <div className="flex flex-row gap-5">
                <div className={(isDark?"text-darkMain ":"text-lightMain ") +"text-xl text-lightMain font-bold dark:text-darkMain"}>
                  {item.nickname}
                </div>
                <div className="flex flex-row">
                  <StarIcon
                    fontSize="small"
                    sx={
                      isDark
                        ? { color: "#BE6DB7", fontSize: "25px" }
                        : { color: "#FF8E9E", fontSize: "25px" }
                    }
                  />
                  <div className="text-lg font-semibold dark:text-white">
                    {item.score}
                  </div>
                </div>
              </div>
              <div className="text-slate-400 font-medium dark:text-white dark:opacity-30">
                {item.createdTime}
              </div>
              <div className="flex flex-row my-[10px] gap-3">
                {item.images?.map((imgs: any) => {
                  return (
                    <img
                      className="w-[100px] rounded-[20px]"
                      key={imgs.id}
                      src={imgs.imageUrl}
                      alt="리뷰 이미지"
                    />
                  );
                })}
              </div>
              <div className="flex flex-row flex-wrap gap-3">
                {item.categories?.map((review: any) => {
                  return (
                    <span
                      className={(isDark?"bg-darkMain ":"bg-lightMain ") + "font-medium py-1 px-2 rounded-lg text-white"}
                      key={review.id}
                    >
                      {review.reviewCategoryCode}
                    </span>
                  );
                })}
                {item.indiReviewCategories?.map((review: any) => {
                  return (
                    <span
                      className={(isDark?"bg-darkMain ":"bg-lightMain ") + "font-medium py-1 px-2 rounded-lg text-white"}
                      key={review.id}
                    >
                      {review.reviewCategory}
                    </span>
                  );
                })}
              </div>
              <div className="font-bold py-3 dark:text-white">
                {item.contents}
              </div>
            </div>
          );
        })
      )}
      {!props.sol &&
      <div className="flex flex-row relative pb-5">
        <span
          className={
            offset === 0
            ? "absolute left-3 font-bold text-slate-400 cursor-default"
            : "absolute left-3 font-bold text-slate-400 hover:text-black cursor-pointer"
          }
          onClick={(cur) => setOffset((cur) => (cur === 0 ? cur : cur - LIMIT))}
          >
          PREV
        </span>
        <span
          className={
            data.length < 5
            ? "absolute right-3 font-bold text-slate-400 cursor-default"
            : "absolute right-3 font-bold text-slate-400 hover:text-black cursor-pointer"
          }
          onClick={() =>
            setOffset((cur) => (data.length < 5 ? cur : cur + LIMIT))
          }
          >
          NEXT
        </span>
      </div>
      } 
    </div>
  );
}
