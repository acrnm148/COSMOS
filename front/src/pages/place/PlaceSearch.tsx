import React, { useState, useEffect } from "react";
import SearchIcon from "../../assets/search/SearchIcon.png";
import "../../css/placeSearch.css";

import TMap from "../../components/common/TMap";
import ItemList from "../../components/search/ItemList";
import CloseIcon from "@mui/icons-material/Close";
import Modal from "../../components/common/ModalSmall";
import Cinema from "../../assets/search/cinema.png";
import Cutlery from "../../assets/search/cutlery.png";
import Coffee from "../../assets/search/coffee-cup.png";
import Shopping from "../../assets/search/shopping-cart.png";
import Gym from "../../assets/search/gym.png";
import Suitcase from "../../assets/search/suitcase.png";

export default function PlaceSearch() {
  // 검색어
  const [searchWord, setSearchWord] = useState("");

  const handleSearch = (e: any) => {
    setSearchWord(e.target.value);
  };

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

  // TMap
  const [state, setState] = useState({
    center: {
      lat: 0,
      lng: 0,
    },
  });

  useEffect(() => {
    // 사용자의 현재 위치 받아옴
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        setState((prev) => ({
          ...prev,
          center: {
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          },
          isLoading: false,
        }));
      });
    } else {
      setState((prev) => ({
        ...prev,
        errMsg: "현재 위치 정보를 받아올 수 없습니다",
        isLoading: false,
      }));
    }
  }, []);

  // 자동완성 API

  return (
    <div className="text-center w-[90%] max-w-[950px] mt-[100px]">
      <h1>장소검색</h1>
      <div className="flex flex-row justify-center">
        <select className="basis-1/6 border-4 border-lightMain opacity-50 rounded-lg rounded-r-none focus:outline-none">
          <option className="options" value="">
            시/도
          </option>
          <option className="options" value="">
            구/군
          </option>
          <option className="options" value="">
            장소명
          </option>
        </select>
        <input
          className="basis-4/6 border-4 border-lightMain opacity-50 rounded-lg rounded-l-none focus:outline-none"
          type="text"
          placeholder="데이트 지역(시/도, 구/군) / 장소명으로 검색"
          onChange={handleSearch}
        />
        <button
          className="flex flex-col justify-center items-center ml-4 bg-lightMain p-2 rounded-xl hover:opacity-80"
          title="검색"
        >
          <img className="w-[32px]" src={SearchIcon} alt="검색" />
        </button>
      </div>
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
      </div>
      <hr className="my-[3vh]" />
      <TMap state={state} />
      <hr className="mt-[3vh]" />
      <ItemList />
      <Modal open={modalOpen} close={closeModal} header="장소 태그 선택">
        <div className="modal-content">
          <div onClick={() => setTag0((cur) => !cur)}>
            <img
              src={Cutlery}
              alt=""
              style={tag0 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            음식
          </div>
          <div onClick={() => setTag1((cur) => !cur)}>
            <img
              src={Coffee}
              alt=""
              style={tag1 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            카페
          </div>
          <div onClick={() => setTag2((cur) => !cur)}>
            <img
              src={Cinema}
              alt=""
              style={tag2 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            문화
          </div>
          <div onClick={() => setTag3((cur) => !cur)}>
            <img
              src={Shopping}
              alt=""
              style={tag3 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            쇼핑
          </div>
          <div onClick={() => setTag4((cur) => !cur)}>
            <img
              src={Suitcase}
              alt=""
              style={tag4 ? { opacity: 1 } : { opacity: 0.3 }}
            />
            관광
          </div>
          <div onClick={() => setTag5((cur) => !cur)}>
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
