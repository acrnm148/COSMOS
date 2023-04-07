import React from "react";
import { createBrowserRouter } from "react-router-dom";
import MainLayout from "../layouts/MainLayout";

import Home from "../pages/home/Home";
import NotFound from "../components/common/NotFound";
import Servey from "../pages/servey/ServeyPage";
import ServeyResult from "../pages/servey/ServeyResult";
import SubLayout from "../layouts/SubLayout";
import PlaceSearch from "../pages/place/PlaceSearch";
import NoHeaderLayout from "../layouts/NoHeaderLayout";
import KakaoLogin from "../pages/login/KakaoLogin";
import Login from "../pages/login/Login";
import Landing from "../pages/landing/LandingPage";
import PlaceRecommend from "../pages/place/PlaceRecommend";
import MyPage from "../pages/mypage/MyPage";
import { MonthSchedulePage } from "../pages/schedule/MonthSchedulePage";
import { DaySchedulePage } from "../pages/schedule/DaySchedulePage";
import { ScheduleDetail } from "../pages/schedule/ScheduleDetail";
import { GalleryPage } from "../pages/schedule/GalleryPage";
import ScheduleLayout from "../layouts/ScheduleLayout";
import WishList from "../pages/wish/WishList";
import PlaceResult from "../pages/place/PlaceResult";
import SearchLayout from "../layouts/SearchLayout";
import Logout from "../pages/login/Logout";
import RequireAuth from "../apis/utils/RequireAuth";

// import Test from "../pages/place/Test"; // 드래그앤드롭
const router = createBrowserRouter([
    {
        path: "/",
        element: <MainLayout />,
        errorElement: <NotFound />,
        children: [
            
            //로그인
            { path: "login", element: <Login /> },
            { path: "login/oauth2", element: <KakaoLogin /> },
            // https://도메인/login/oauth2
        ],
    },
    // auth
    // ,ainpage, mypage
    { 
        path: "/",
        element: <RequireAuth />,
        errorElement: <NotFound />,
        children: [{ path: "", 
                    element: <MainLayout />,
                    errorElement: <NotFound />,
                    children: [
                        { index: true, path: "", element: <Home /> },
                        { path: "mypage", element: <MyPage />}, // 마이페이지
                        { path: "logout", element: <Logout /> }, // 로그아웃
                        // 찜 목록
                        { path: "wish", element: <WishList /> },
                        { path: "wish/course/:wishId", element: <WishList /> },
                        { path: "wish/course/:courseId/detail", element: <WishList /> },
                        { path: "wish/course/:editId/edit", element: <WishList /> },
                        { path: "wish/:makeCourse", element: <WishList /> },
                        // 장소 검색 & 빅데이터 추천
                        { path: "place/recommend", element: <PlaceRecommend /> },
                        { path: "place/result", element: <PlaceResult /> },
                    ],
                }]
    },
    // 랜딩페이지
    {
        path: "/landing",
        element: <SubLayout />,
        errorElement: <NotFound />,
        children: [{ path: "/landing", element: <Landing /> }],
    },
    {
        path: "/place",
        element: <SearchLayout />,
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
            { path: ":coupleId/:inviteId", element: <Servey /> },
            { path: "result/:cate/:cateNum", element: <ServeyResult /> },
        ],
    },
    //일정관리
    {
        path: "/schedule",
        element: <RequireAuth />,
        errorElement: <NotFound />,
        children: [{
            path: "",
            element: <ScheduleLayout />,
            errorElement: <NotFound />,
            children: [
                { path: "month", element: <MonthSchedulePage /> },
                { path: "day", element: <DaySchedulePage /> },
                { path: "detail", element: <ScheduleDetail /> },
                { path: "gallery", element: <GalleryPage /> },
            ]
         }]
    },
    // 찜 목록
    // {
    //     path: "/wish",
    //     element: <MainLayout />,
    //     errorElement: <NotFound />,
    //     children: [
    //         { path: "", element: <WishList /> },
    //         { path: "/wish/course/:wishId", element: <WishList /> },
    //         { path: "/wish/course/:courseId/detail", element: <WishList /> },
    //         { path: "/wish/course/:editId/edit", element: <WishList /> },
    //         { path: "/wish/:makeCourse", element: <WishList /> },
    //     ],
    // },
    // 장소 검색 & 빅데이터 추천
    // {
    //     path: "/place",
    //     element: <MainLayout />,
    //     errorElement: <NotFound />,
    //     children: [
    //         { path: "recommend", element: <PlaceRecommend /> },
    //         { path: "result", element: <PlaceResult /> },
    //     ],
    // },

]);

export default router;
