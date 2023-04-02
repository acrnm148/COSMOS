import React, { useState } from "react";
import { useQuery } from "react-query";
import { reviewTest } from "../../apis/api/place";

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

export default function ReviewAll({ placeId }: any) {
  const LIMIT = 10;
  const [offset, setOffset] = useState(0);

  //   const { data, isLoading } = useQuery({
  //     queryKey: ["getReviewAll", placeId, offset],
  //     queryFn: () => getReviewAll(placeId, LIMIT, offset),
  //   });

  const { data, isLoading } = useQuery({
    queryKey: ["reviewTest"],
    queryFn: () => reviewTest(),
  });

  if (isLoading) return null;

  console.log(data);
  return (
    <div>
      {data === "" ? <div>등록된 리뷰가 존재하지 않습니다.</div> : null}
    </div>
  );
}
