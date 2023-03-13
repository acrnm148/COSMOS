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
      { path: "/recoco", element: <Reco /> },
      //   설문조사
      { path: "/servey", element: <Servey /> },
      { path: "/result/:cate/:cateNum", element: <ServeyResult /> },
    ],
  },
  // 장소 검색 & 빅데이터 추천
  {
    path: "/place",
    element: <SubLayout />,
    errorElement: <NotFound />,
    children: [{ path: "search", element: <PlaceSearch /> }],
  },
]);

export default router;
