import React from "react";
import Slider from "react-slick";
import "../../css/slick.css";
import "../../css/slickTheme.css";
import Slider1 from "../../assets/home/slider1.png";

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

  return (
    <Slider {...settings}>
      <div>
        <Card />
      </div>
      <div>
        <p>애인을 위한 추천 코스</p>
      </div>
      <div>
        <p>우리 커플을 위한 추천 코스</p>
      </div>
    </Slider>
  );
}

function Card() {
  return (
    <div>
      <div className="absolute top-32 ml-5 p-1 px-2 text-base font-light text-white bg-lightMain rounded-full">
        코스 보러 가기 ▶
      </div>
      <img src={Slider1} />
    </div>
  );
}
