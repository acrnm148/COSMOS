import { Outlet } from "react-router-dom";
import Footer from "../components/common/Footer";
import Header from "../components/common/Header";

function NoHeaderLayout() {
  return (
    <div className="flex flex-col justify-center items-center m-auto bg-darkBackground">
      <Outlet />
    </div>
  );
}

export default NoHeaderLayout;
