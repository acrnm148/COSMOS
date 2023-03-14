import React from "react";
import Slider from "react-slick";
import "../../css/slick.css";
import "../../css/slickTheme.css";
import slider1 from "../../assets/home/slider1.png";
import slider2 from "../../assets/home/slider2.png";
import slider5 from "../../assets/home/slider5.png";

export default function recommSlider() {
  var settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 4000,
    arrows: false,
  };

  const cardContent: { title: string; imgSrc: string }[] = [
    { title: "나를 위한 추천 코스", imgSrc: "slider1" },
    { title: "애인을 위한 추천 코스", imgSrc: "slider2" },
    { title: "우리 커플을 위한 추천 코스", imgSrc: "slider2" },
  ];

  return (
    <Slider {...settings}>
      {cardContent.map((e) => {
        return (
          <div>
            <Card content={e.title} imgSrc={e.imgSrc} />
          </div>
        );
      })}
    </Slider>
  );
}

function Card(props: { content: string; imgSrc: string }) {
  return (
    <div>
      <div className="list bg-lightMain4 w-full h-48 my-2 p-4 rounded-2xl relative">
        <div className="content ml-3 mt-3 text-2xl font-bold z-40">{props.content}</div>
        <div className="absolute top-[136px] ml-3 p-1 px-2 text-base font-light text-white bg-lightMain rounded-full">
          코스 보러 가기 ▶
        </div>
        <img
          src={props.imgSrc}
          // src={slider2}
          alt="slide"
          width="150px"
          className="absolute right-3 bottom-2 z-20"
        />
      </div>
    </div>
  );
}
