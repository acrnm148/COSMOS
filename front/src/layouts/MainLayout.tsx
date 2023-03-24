import { Outlet } from "react-router-dom";
import Footer from "../components/common/Footer";
import Header from "../components/common/Header";

function MainLayout() {
  return (
    <div className="flex flex-col justify-center items-center max-w-[950px] m-auto">
      <Header />
      <Outlet />
      <Footer />
    </div>
  );
}

export default MainLayout;
