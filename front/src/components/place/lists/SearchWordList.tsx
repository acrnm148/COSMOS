import React from "react";
import { useRecoilState } from "recoil";
import {
  clickBackground,
  searchWord,
  completeWord,
} from "../../../recoil/states/SearchPageState";
import { useQuery } from "react-query";
import { getAutoSearchList } from "../../../apis/api/place";
import defaultImg from "../../../assets/place/default-img.png";

export default function SearchWordList() {
  const [clickBg, setClickBg] = useRecoilState(clickBackground);
  const [word, setWord] = useRecoilState(searchWord);
  const [comWord, setComWord] = useRecoilState(completeWord);

  const { data, isLoading } = useQuery({
    queryKey: ["getAutoSearchList", word],
    queryFn: () => getAutoSearchList(word),
  });

  if (isLoading || data === null) return null;

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
                setWord(item.name);
                setComWord(item.name);
              }}
            >
              <img
                className="w-20 h-20 mr-5"
                src={
                  item.thumbNailUrl === null ? defaultImg : item.thumbNailUrl
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
