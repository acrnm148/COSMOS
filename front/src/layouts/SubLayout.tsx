import React from "react";
import { Outlet } from "react-router-dom";
import Header from "../components/common/Header";
import Footer from "../components/common/Footer";

export default function SubLayout() {
  return (
    <div className="flex flex-col justify-center items-center">
      <Header />
      <Outlet />
      <Footer />
    </div>
  );
}
