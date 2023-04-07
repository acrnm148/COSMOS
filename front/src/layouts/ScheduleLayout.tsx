import { Outlet } from "react-router-dom";
import Footer from "../components/common/Footer";
import Header from "../components/common/Header";

function NoHeaderLayout() {
  return (
    <div className="flex flex-col justify-center max-w-[950px] m-auto">
      <Outlet />
      <Footer />
    </div>
  );
}

export default NoHeaderLayout;
