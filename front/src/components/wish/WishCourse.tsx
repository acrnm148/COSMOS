import { Icon } from "@iconify/react";
import { useNavigate } from "react-router-dom";
import { getWishCourseList, deleteWishCourse } from "../../apis/api/wish";
import { useMutation, useQuery, useQueryClient } from "react-query";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";
import { useEffect, useState } from "react";
import Swal from "sweetalert2";
import { darkMode } from "../../recoil/states/UserState";

interface Course {
  courseId: number;
  date: string;
  name: string;
  orders: number;
  places: [Place];
}

interface Place {
  placeId: number;
  name: string;
  thumbNailUrl: string;
  address: string;
  phoneNumber: string;
  score: number;
  orders: number; // 코스 순서
  latitude: string; // 위도
  longitude: string; // 경도
  overview: string; // 개요
}

export default function WishCourse() {
  const [userSeq, setUserSeq] = useRecoilState(userState);
  const [course, setCourse] = useState<Course[]>();
  const { data } = useQuery({
    queryKey: ["getWishCourseList"],
    queryFn: () => getWishCourseList(userSeq.seq, userSeq.acToken),
  });

  useEffect(() => {
    setCourse(data);
  }, [data]);

  return (
    <div>
      <div className="mx-5 mt-5">
        <div className="title font-medium text-xl inline-block">찜한 코스</div>
        <div className="cnt ml-2 font-bold text-red-600 text-xl inline-block">
          {course?.length}
        </div>

        <div className="mt-4 h-[540px] overflow-y-auto">
          {course?.map((c: Course) => (
            <Item key={c.courseId} item={c}></Item>
          ))}
        </div>
      </div>
    </div>
  );
}

function Item(props: { item: Course }) {
  const isDark = useRecoilState(darkMode)[0];
  const navigate = useNavigate();
  const Toast = Swal.mixin({
    toast: true, // 토스트 형식
    position: "bottom-end", // 알림 위치
    showConfirmButton: false, // 확인버튼 생성 유무
    timer: 1000, // 지속 시간
    timerProgressBar: true, // 지속시간바 생성 여부
  });

  const queryClient = useQueryClient();
  const mutation = useMutation(deleteWishCourse, {
    onSuccess: (data) => {
      Toast.fire({
        icon: "success",
        title: "삭제되었습니다. ",
      });

      queryClient.invalidateQueries();
    },
    onError: () => {
      Toast.fire({
        icon: "error",
        title: "삭제 실패했습니다. ",
      });
    },
  });

  return (
    <div className="mb-5">
      <div
        className={
          isDark
            ? "col-md-4 p-3 h-48 bg-darkBackground2 rounded-t-lg cursor-pointer"
            : "col-md-4 p-3 h-48 bg-calendarGray rounded-t-lg cursor-pointer"
        }
        onClick={() => {
          navigate(`/wish/course/${props.item.courseId}/detail`);
        }}
      >
        <div className="font-bold mb-3">{props.item.name}</div>

        <div className="w-full h-36 flex overflow-x-scroll scrollbar-hide">
          {props.item.places.map((p: any) => {
            let name =
              p.name.length > 7 ? p.name.slice(0, 7).concat("...") : p.name;

            return (
              <div
                key={p.placeId}
                className="float-left flex-none w-32 h-28 mr-3 text-center"
              >
                <img
                  className="w-32 h-24 rounded-lg"
                  src={p.thumbNailUrl}
                  alt="img"
                />
                <div className="w-32 h-8 mt-2 text-sm">{name}</div>
              </div>
            );
          })}
        </div>
      </div>
      <div
        className={
          isDark
            ? "cursor-pointer btns w-full h-12 bg-darkMain3 rounded-b-lg text-center flex"
            : "cursor-pointer btns w-full h-12 bg-lightMain3 rounded-b-lg text-center flex"
        }
      >
        <div className="float-left w-1/3 m-auto">
          <Icon
            icon="material-symbols:share-outline"
            width="20"
            height="20"
            className="inline-block mr-2 mb-1 "
          />
          공유
        </div>
        <div
          className="float-left w-1/3 m-auto"
          onClick={() => {
            navigate(`/wish/course/${props.item.courseId}/edit`);
          }}
        >
          <Icon
            icon="material-symbols:edit-outline-rounded"
            width="22"
            height="22"
            className="inline-block mr-2 mb-1"
          />
          편집
        </div>
        <div
          className="float-left w-1/3 m-auto"
          onClick={() => {
            Swal.fire({
              // title: `${props.item.title}을 찜 해제하시겠습니까?`,
              text: `${props.item.name} 코스를 삭제하시겠습니까?`,
              icon: "question",

              showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
              confirmButtonColor: isDark ? "#BE6DB7" : "#FF8E9E", // confrim 버튼 색깔 지정
              cancelButtonColor: "#B9B9B9", // cancel 버튼 색깔 지정
              confirmButtonText: "확인", // confirm 버튼 텍스트 지정
              cancelButtonText: "취소", // cancel 버튼 텍스트 지정
            }).then((result) => {
              // 만약 Promise리턴을 받으면,
              if (result.isConfirmed) {
                // 만약 모달창에서 confirm 버튼을 눌렀다면
                // 해당 장소 찜 목록에서 삭제

                mutation.mutate(Number(props.item.courseId));
              }
            });
          }}
        >
          <Icon
            icon="material-symbols:delete-forever-outline"
            width="22"
            height="22"
            className="inline-block mr-2 mb-1"
          />
          삭제
        </div>
      </div>
    </div>
  );
}
