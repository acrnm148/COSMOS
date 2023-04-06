import React from "react";
import Slider from "react-slick";
import "../../css/slick.css";
import "../../css/slickTheme.css";

export default function recommSlider({ isDark }: any) {
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

    const cardContent: { id: number; title: string; imgSrc: string }[] = [
        { id: 0, title: "나를 위한 추천 코스", imgSrc: "slider1" },
    ];

    const cardContent2: { id: number; title: string; imgSrc: string }[] = [
        { id: 0, title: "나를 위한 추천 코스", imgSrc: "slider1" },
        { id: 1, title: "애인을 위한 추천 코스", imgSrc: "slider2" },
        { id: 2, title: "우리 커플을 위한 추천 코스", imgSrc: "slider5" },
    ];

    return (
        <Slider {...settings}>
            {isDark[0]
                ? cardContent.map((e) => {
                      return (
                          <div key={e.id}>
                              <Card
                                  content={e.title}
                                  isDark={isDark}
                                  imgSrc={e.imgSrc}
                              />
                          </div>
                      );
                  })
                : cardContent2.map((e) => {
                      return (
                          <div key={e.id}>
                              <Card
                                  content={e.title}
                                  isDark={isDark}
                                  imgSrc={e.imgSrc}
                              />
                          </div>
                      );
                  })}
        </Slider>
    );
}

function Card(props: { content: string; imgSrc: string; isDark: any }) {
    return (
        <div>
            <div className="list bg-lightMain4 w-full h-48 my-2 p-4 rounded-2xl relative dark:bg-darkMain3">
                <div className="content ml-3 mt-3 text-2xl font-bold z-40 dark:text-white">
                    {props.content}
                </div>
                <div className="absolute top-[136px] ml-3 p-1 px-2 text-base font-light text-white bg-lightMain rounded-full dark:bg-darkMain">
                    코스 보러 가기 ▶
                </div>
                {props.isDark[0] ? (
                    <img
                        src={require(`../../assets/home/dark/${props.imgSrc}.png`)}
                        alt="slide"
                        width="125px"
                        className="absolute right-3 bottom-2 z-20"
                    />
                ) : (
                    <img
                        src={require(`../../assets/home/${props.imgSrc}.png`)}
                        alt="slide"
                        width="125px"
                        className="absolute right-3 bottom-2 z-20"
                    />
                )}
            </div>
        </div>
    );
}
