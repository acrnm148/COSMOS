import React from "react";
import { useQuery } from "react-query";
import { getListWithSearchWord } from "../../apis/api/place";
import DefaultImg from "../../assets/login/pinkCosmos.png";
import { useRecoilState } from "recoil";
import { clickBackground } from "../../recoil/states/SearchPageState";
import { ClickAwayListener } from "@mui/base";

export default function SearchWordList({
  searchWord,
  setSearchWord,
  setItems,
}: any) {
  const [clickBg, setClickBg] = useRecoilState(clickBackground);

  const { data, isLoading } = useQuery({
    queryKey: ["getListWithSearchWord", searchWord],
    queryFn: () => getListWithSearchWord(searchWord),
  });

  if (isLoading) return null;

  return (
    <div className="text-left rounded-lg max-h-[300px] overflow-auto float absolute z-[99999] w-full">
      {data.length === 0 || clickBg
        ? null
        : data.map((item: any) => (
            <div
              className="flex flex-row py-2 cursor-pointer pl-2 leading-10 bg-lightMain4 hover:bg-lightMain3"
              key={item.placeId}
              onClick={() => {
                setClickBg(true);
                setItems({
                  placeId: item.placeId,
                  name: item.name,
                  address: item.address,
                  type: item.type,
                });
                setSearchWord(item.name);
              }}
            >
              <img
                className="w-20 h-20 mr-5"
                src={
                  item.thumbNailUrl === null ? DefaultImg : item.thumbNailUrl
                }
                alt=""
              />
              <div>
                <p className="font-bold">{item.name}</p>
                <p className="text-slate-500 truncate">{item.address}</p>
              </div>
            </div>
          ))}
    </div>
  );
}
