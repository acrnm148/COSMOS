import React, { useState } from "react";
import "../../css/listItem.css";
import { useQuery } from "react-query";
import { getPlaceList } from "../../apis/api/place";
import SearchPlaceItem from "./SearchPlaceItem";

export default function ListItems({
  items,
  setState,
  setMarkers,
  sido,
  gugun,
  text,
  filter,
}: any) {
  const { data, isLoading } = useQuery({
    queryKey: ["getPlaceList", sido, gugun, text, filter],
    queryFn: () => getPlaceList(1, sido, gugun, text, filter, 8, 0),
  });

  if (isLoading) return null;

  return (
    <>
      {data === undefined || data === null ? null : (
        <SearchPlaceItem
          data={data}
          setState={setState}
          setMarkers={setMarkers}
        />
      )}
    </>
  );
}
