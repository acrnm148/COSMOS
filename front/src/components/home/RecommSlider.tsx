import React from "react";
import Slider from "react-slick";
import "../../css/slick.css";
import "../../css/slickTheme.css";

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

  const cardContent: string[] = [
    "나를 위한 추천 코스",
    "애인을 위한 추천 코스",
    "우리 커플을 위한 추천 코스",
  ];

  return (
    <Slider {...settings}>
      {cardContent.map((e) => {
        return (
          <div>
            <Card content={e} />
          </div>
        );
      })}
    </Slider>
  );
}

function Card(props: { content: string }) {
  return (
    <div>
      <div className="list bg-lightMain4 w-full h-48 my-2 p-4 rounded-2xl">
        <div className="content ml-3 mt-3 text-2xl font-bold">{props.content}</div>
        <div className="absolute top-[136px] ml-3 p-1 px-2 text-base font-light text-white bg-lightMain rounded-full">
          코스 보러 가기 ▶
        </div>
      </div>
    </div>
  );
}
