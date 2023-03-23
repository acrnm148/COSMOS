import React from "react";
import { useQuery } from "react-query";
import { getListWithSearchWord } from "../../apis/api/place";

export default function SearchWordList({ searchWord }: any) {
  const { data, isLoading } = useQuery({
    queryKey: ["getListWithSearchWord", searchWord],
    queryFn: () => getListWithSearchWord(searchWord),
  });

  if (isLoading) return null;
  console.log(data);
  return (
    <>
      {data.length === 0
        ? null
        : data.map((item: any) => {
            <div key={item.placeId}>{item.name}</div>;
          })}
    </>
  );
}
