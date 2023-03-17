import { Outlet } from "react-router-dom";
import Footer from "../components/common/Footer";
import Header from "../components/common/Header";

function NoHeaderLayout() {
  return (
    <>
      <Outlet />
      <Footer />
    </>
  );
}

export default NoHeaderLayout;
