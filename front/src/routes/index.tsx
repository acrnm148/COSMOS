import React from "react";
import { createBrowserRouter } from "react-router-dom";
import MainLayout from "../layouts/MainLayout";

import Reco from "../pages/servey/Recoreconi";
import Home from "../pages/home/Home";
import NotFound from "../components/common/NotFound";
import Servey from "../pages/servey/ServeyPage";
import ServeyResult from "../pages/servey/ServeyResult";
import SubLayout from "../layouts/SubLayout";
import PlaceSearch from "../pages/search/PlaceSearch";
import NoHeaderLayout from "../layouts/NoHeaderLayout";
import KakaoLogin from "../pages/login/KakaoLogin";
import Login from "../pages/login/Login";
import MyPage from "../pages/mypage/MyPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: <MainLayout />,
    errorElement: <NotFound />,
    children: [
      {
        index: true,
        path: "/",
        element: <Home />,
      },
      //로그인
      { path: "/login", element: <Login /> },
      { path: "/login/oauth", element: <KakaoLogin />},
      // 마이페이지
      { path: "/mypage", element:<MyPage />},
    ],
  },
  // 장소 검색 & 빅데이터 추천
  {
    path: "/place",
    element: <SubLayout />,
    errorElement: <NotFound />,
    children: [{ path: "search", element: <PlaceSearch /> }],
  },
  // 설문조사
  {
    path: "/servey",
    element: <NoHeaderLayout />,
    errorElement: <NotFound />,
    children: [
      { path: "", element: <Servey /> },
      { path: "result/:cate/:cateNum", element: <ServeyResult /> },
    ],
  },
]);

export default router;
