import React, { useState } from "react";
import CloseIcon from "@mui/icons-material/Close";
import Modal from "../common/Modal";
import Cinema from "../../assets/place/cinema.png";
import Cutlery from "../../assets/place/cutlery.png";
import Coffee from "../../assets/place/coffee-cup.png";
import Shopping from "../../assets/place/shopping-cart.png";
import Gym from "../../assets/place/gym.png";
import Suitcase from "../../assets/place/suitcase.png";

export default function SearchFilter() {
  // 모달창
  const [modalOpen, setModalOpen] = useState(false);

  const openModal = () => {
    setModalOpen(true);
  };
  const closeModal = () => {
    setModalOpen(false);
  };

  // 검색 태그
  const [tag0, setTag0] = useState(false);
  const [tag1, setTag1] = useState(false);
  const [tag2, setTag2] = useState(false);
  const [tag3, setTag3] = useState(false);
  const [tag4, setTag4] = useState(false);
  const [tag5, setTag5] = useState(false);

  return (
    <div className="flex mt-[10px] justify-center">
      <div className="flex flex-row flex-start flex-wrap  gap-1 w-[70vw]">
        {tag0 ? (
          <button className="tagBtn flex flex-row w-[75px] h-[30px] border-2 border-lightMain opacity-50 rounded-lg text-lightMain">
            <span className="cursor-default ml-1"># 음식</span>
            <span className="mt-[-2px]" onClick={() => setTag0(false)}>
              <CloseIcon fontSize="small" />
            </span>
          </button>
        ) : null}
        {tag1 ? (
          <button className="tagBtn flex flex-row w-[75px] h-[30px] border-2 border-lightMain opacity-50 rounded-lg text-lightMain">
            <span className="cursor-default ml-1"># 카페</span>
            <span className="mt-[-2px]" onClick={() => setTag1(false)}>
              <CloseIcon fontSize="small" />
            </span>
          </button>
        ) : null}
        {tag2 ? (
          <button className="tagBtn flex flex-row w-[75px] h-[30px] border-2 border-lightMain opacity-50 rounded-lg text-lightMain">
            <span className="cursor-default ml-1"># 문화</span>
            <span className="mt-[-2px]" onClick={() => setTag2(false)}>
              <CloseIcon fontSize="small" />
            </span>
          </button>
        ) : null}
        {tag3 ? (
          <button className="tagBtn flex flex-row w-[75px] h-[30px] border-2 border-lightMain opacity-50 rounded-lg text-lightMain">
            <span className="cursor-default ml-1"># 쇼핑</span>
            <span className="mt-[-2px]" onClick={() => setTag3(false)}>
              <CloseIcon fontSize="small" />
            </span>
          </button>
        ) : null}
        {tag4 ? (
          <button className="tagBtn flex flex-row w-[75px] h-[30px] border-2 border-lightMain opacity-50 rounded-lg text-lightMain">
            <span className="cursor-default ml-1"># 관광</span>
            <span className="mt-[-2px]" onClick={() => setTag4(false)}>
              <CloseIcon fontSize="small" />
            </span>
          </button>
        ) : null}
        {tag5 ? (
          <button className="tagBtn flex flex-row w-[75px] h-[30px] border-2 border-lightMain opacity-50 rounded-lg text-lightMain">
            <span className="cursor-default ml-1"># 운동</span>
            <span className="mt-[-2px]" onClick={() => setTag5(false)}>
              <CloseIcon fontSize="small" />
            </span>
          </button>
        ) : null}
      </div>
      <button
        className="w-[8vw] min-w-[80px] h-[30px] border-2 border-lightMain rounded-lg bg-lightMain text-white text-[1rem]"
        onClick={openModal}
      >
        검색필터
      </button>
      <Modal
        open={modalOpen}
        close={closeModal}
        header="장소 태그 선택"
        size="small"
      >
        <div className="modal-content">
          <div
            className={tag0 ? "content-clicked" : ""}
            onClick={() => setTag0((cur) => !cur)}
          >
            <img
              src={Cutlery}
              alt=""
              style={tag0 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            음식
          </div>
          <div
            className={tag1 ? "content-clicked" : ""}
            onClick={() => setTag1((cur) => !cur)}
          >
            <img
              src={Coffee}
              alt=""
              style={tag1 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            카페
          </div>
          <div
            className={tag2 ? "content-clicked" : ""}
            onClick={() => setTag2((cur) => !cur)}
          >
            <img
              src={Cinema}
              alt=""
              style={tag2 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            문화
          </div>
          <div
            className={tag3 ? "content-clicked" : ""}
            onClick={() => setTag3((cur) => !cur)}
          >
            <img
              src={Shopping}
              alt=""
              style={tag3 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            쇼핑
          </div>
          <div
            className={tag4 ? "content-clicked" : ""}
            onClick={() => setTag4((cur) => !cur)}
          >
            <img
              src={Suitcase}
              alt=""
              style={tag4 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            관광
          </div>
          <div
            className={tag5 ? "content-clicked" : ""}
            onClick={() => setTag5((cur) => !cur)}
          >
            <img
              src={Gym}
              alt=""
              style={tag5 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            운동
          </div>
        </div>
      </Modal>
    </div>
  );
}
