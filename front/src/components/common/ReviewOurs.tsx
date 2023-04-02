import React, { useState } from "react";
import { useQuery } from "react-query";
import { getReviewOurs } from "../../apis/api/place";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";

export interface REVIEW {
  reviewId: number | undefined;
  categories: [{ id: number; reviewCategoryCode: "string" }] | undefined;
  indiReviewCategories:
    | [{ id: number; reviewCategoryCode: "string" }]
    | undefined;
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
}
export default function ReviewOurs({ placeId }: any) {
  const LIMIT = 10;
  const [offset, setOffset] = useState(0);
  const user = useRecoilState(userState);

  const { data, isLoading } = useQuery({
    queryKey: ["getReviewOurs", placeId, offset],
    queryFn: () => getReviewOurs(1, placeId, user[0].coupleId, LIMIT, offset),
  });

  if (isLoading) return null;

  return (
    <div>
      {data === "" ? <div>등록된 리뷰가 존재하지 않습니다.</div> : null}
    </div>
  );
}
