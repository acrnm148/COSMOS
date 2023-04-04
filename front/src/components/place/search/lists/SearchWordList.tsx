import React from "react";
import { useRecoilState } from "recoil";
import {
  clickBackground,
  searchWord,
  completeWord,
} from "../../../../recoil/states/SearchPageState";
import { useQuery } from "react-query";
import { getAutoSearchList } from "../../../../apis/api/place";
import DarkDefaultImg from "../../../../assets/login/darkCosmos.png";
import { darkMode } from "../../../../recoil/states/UserState";

export default function SearchWordList() {
  const isDark = useRecoilState(darkMode)[0];
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
              className={
                "flex flex-row py-2 cursor-pointer pl-2 leading-10 bg-lightMain3 hover:bg-lightMain2 dark:bg-darkMain3 dark:hover:bg-darkMain4"
              }
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
                  item.thumbNailUrl ===
                  "https://cosmoss3.s3.ap-northeast-2.amazonaws.com/dc27450e-9c2c-4ae0-b2d2-d0b106e92288.png"
                    ? isDark
                      ? DarkDefaultImg
                      : item.thumbNailUrl
                    : item.thumbNailUrl
                }
                alt=""
              />
              <div>
                <p className={isDark ? "font-bold text-white" : "font-bold"}>
                  {item.name}
                </p>
                <p
                  className={
                    isDark
                      ? "text-white truncate opacity-30"
                      : "text-slate-500 truncate"
                  }
                >
                  {item.address}
                </p>
              </div>
            </div>
          ))}
    </div>
  );
}
