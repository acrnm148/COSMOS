import { createContext } from "react";
import { Outlet } from "react-router-dom";
import { useRecoilState } from "recoil";
import Footer from "../components/common/Footer";
import Header from "../components/common/Header";
import { userState } from "../recoil/states/UserState";

export const UserDispatch = createContext<null|string>(null)
function MainLayout() {
  const [user, setUser] = useRecoilState(userState)
  // function getUserAcToken(){return user.acToken}
  return (
    <>
      <Header />
        <UserDispatch.Provider value={user.acToken}>
          <Outlet />
        </UserDispatch.Provider>
      <Footer />
    </>
  );
}

export default MainLayout;
