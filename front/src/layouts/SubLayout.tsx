import React from "react";
import { Outlet } from "react-router-dom";
import Header from "../fixtures/Header";
import Footer from "../fixtures/Footer";

export default function SubLayout() {
  return (
    <div className="flex flex-col justify-center items-center">
      <Header />
      <Outlet />
      <Footer />
    </div>
  );
}
