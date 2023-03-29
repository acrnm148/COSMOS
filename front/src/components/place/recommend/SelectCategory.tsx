import React, { useRef, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useRecoilState } from "recoil";
import { selectCategory } from "../../../recoil/states/RecommendPageState";
import SelectCategoryItem from "./items/SelectCategoryItem";
import AddCircleIcon from "@mui/icons-material/AddCircle";
import Logo from "../../../assets/login/pinkCosmos.png";
import Loading from "../../../assets/loading/modal-loading.gif";
import Swal from "sweetalert2";
import "../../../css/placeSearch.css";

export default function SelecctCategory() {
  const navigate = useNavigate();
  const [category, setCategory] = useRecoilState(selectCategory);
  const categoryRef = useRef<HTMLDivElement>(null);
  const tags = [false, false, false, false, false, false];
  const [list, setList] = useState([{ index: 0, state: true }]);
  const [number, setNumber] = useState(1);

  const handleClickBtn = () => {
    setNumber((cur) => cur + 1);
    setList(list.concat({ index: number, state: true }));
  };

  const handleComplete = () => {
    const clicked = document.querySelectorAll(".content-clicked");
    let selected: any[] | ((currVal: {}[]) => {}[]) = [];
    for (let i = 0; i < clicked.length; i++) {
      switch (clicked[i].className[16]) {
        case "0":
          selected = selected.concat(["restaurant"]);
          break;
        case "1":
          selected = selected.concat(["cafe"]);
          break;
        case "2":
          selected = selected.concat(["culture"]);
          break;
        case "3":
          selected = selected.concat(["shopping"]);
          break;
        case "4":
          selected = selected.concat(["tour"]);
          break;
        case "5":
          selected = selected.concat(["leisure"]);
          break;
        default:
      }
      setCategory(selected);
    }

    Swal.fire({
      title: "카테고리 선택 완료!",
      text: "코스 추천 페이지로 이동합니다.",
      icon: "success",

      showCancelButton: true, // cancel버튼 보이기. 기본은 원래 없음
      confirmButtonColor: "#FF8E9E", // confrim 버튼 색깔 지정
      cancelButtonColor: "#A3A3A3", // cancel 버튼 색깔 지정
      confirmButtonText: "승인", // confirm 버튼 텍스트 지정
      cancelButtonText: "취소", // cancel 버튼 텍스트 지정

      reverseButtons: false, // 버튼 순서 거꾸로
    }).then((result) => {
      // 만약 Promise리턴을 받으면,
      if (result.isConfirmed) {
        // 만약 모달창에서 confirm 버튼을 눌렀다면

        Swal.fire({
          html: `<div>
                  <img class="modal-loading" src=${Logo} />
                  <img class="modal-loading" src=${Loading} />
                </div>`,
          timer: 4000,
          timerProgressBar: true,
          showConfirmButton: false,
        }).then(() => {
          // 추천 코스 페이지로 이동
          navigate("/place/result");
        });
      }
    });
  };

  return (
    <div className="mb-[90px] overflow-auto">
      <div
        className="text-right cursor-pointer"
        title="추가"
        onClick={handleClickBtn}
      >
        <AddCircleIcon sx={{ color: "#FF8E9E", fontSize: 40 }} />
      </div>
      <div ref={categoryRef}>
        {list.map((item) => {
          return <SelectCategoryItem tags={tags} key={item.index} />;
        })}
      </div>
      <button
        className="float-right mr-[10px] mt-[20px] text-white bg-lightMain p-[10px] rounded-lg hover:bg-lightMain2"
        onClick={handleComplete}
      >
        선택 완료
      </button>
    </div>
  );
}
