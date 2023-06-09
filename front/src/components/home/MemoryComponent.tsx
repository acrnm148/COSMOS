import { useRecoilState } from "recoil";
import { darkMode, userState } from "../../recoil/states/UserState";
import { useQuery } from "react-query";
import { getPhotos } from "../../apis/api/schedule";
import { useEffect, useState } from "react";
type REVIEWIMG = {
  imageId: number;
  imageUrl: string;
  reviewId: number;
  createdTime: string;
  createdMonth: string;
};
type IMAGE = {
  imageUrl: string;
  createdTime: string;
  imageId: number;
  reviewId: number;
};
export default function MemoryComponent() {
  const isDark = useRecoilState(darkMode);
  // 로그인 유저
  const [loginUser, setLoginUser] = useRecoilState(userState);
  // 갤러리 사진 저장
  const [photos, setPhotos] = useState<REVIEWIMG[]>();
  // 갤러리 사진 요청
  const { data, isLoading } = useQuery({
    queryKey: ["getPhotos"],
    queryFn: () => getPhotos(loginUser.coupleId, 3, 0),
  });
  useEffect(() => {
    if (data) {
      // console.log('사진있삼~', data)
      setPhotos(
        data.map((d: IMAGE) => ({
          imageUrl: d.imageUrl,
          createdTime: d.createdTime,
          imageId: d.imageId,
          reviewId: d.reviewId,
          createdMonth: d.createdTime.slice(0, 6),
        }))
      );
    } else {
      console.log("사진 없삼");
    }
  }, [data]);

  if (data === undefined)
    return (
      <div className="pt-10 w-full">
        <div className="title text-xl font-bold dark:text-white">
          {isDark ? "나의 추억" : "우리의 추억"}
        </div>
        <div className="list bg-lightMain4 w-full h-44 mt-2 p-4 rounded-2xl drop-shadow-lg flex overflow-x-scroll scrollbar-hide dark:bg-darkMain3"></div>
      </div>
    );

  return (
    <div className="pt-10 w-full">
      <div className="title text-xl font-bold dark:text-white">
        {isDark ? "나의 추억" : "우리의 추억"}
      </div>
      <div className="list bg-lightMain4 w-full h-44 mt-2 p-4 rounded-2xl drop-shadow-lg flex overflow-x-scroll scrollbar-hide dark:bg-darkMain3">
        {data.length <= 0
          ? null
          : data.map((item: any) => {
              return (
                <img
                  className="item w-[135px] h-[135px] bg-neutral-300 my-auto mr-5 flex-none rounded-lg text-center leading-[135px] text-lg font-semibold "
                  src={item.imageUrl}
                />
              );
            })}
      </div>
    </div>
  );
}
