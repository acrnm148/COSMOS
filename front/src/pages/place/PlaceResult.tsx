import React, { useState, useEffect } from "react";
import { useRecoilState } from "recoil";
import {
  selectSido,
  selectGugun,
  selectCategory,
} from "../../recoil/states/RecommendPageState";
import { useQuery } from "react-query";
import { getDateCourse } from "../../apis/api/place";
import PlaceList from "../../components/place/search/lists/PlaceList";
import TMap from "../../components/common/TMap";

export default function PlaceResult() {
  const sido = useRecoilState(selectSido);
  const gugun = useRecoilState(selectGugun);
  const category = useRecoilState(selectCategory);

  const tmp = {
    sido: "서울특별시",
    gugun: "강남구",
    categories: category[0],
    userSeq: 1,
  };

  const [item, setItem] = useState(JSON.stringify(tmp));

  useEffect(() => {
    setItem(JSON.stringify(tmp));
  }, [sido[0].sidoName, gugun[0].gugunName, category[0]]);

  const { data, isLoading } = useQuery({
    queryKey: ["getDateCourse", item],
    queryFn: () => getDateCourse(item),
  });

  if (isLoading) return null;

  return (
    <div className="w-[90%] text-center">
      <div className="mt-5">
        <TMap />
      </div>
      {data === null || data === undefined ? null : (
        <PlaceList data={data.data.places} />
      )}
    </div>
  );
}
