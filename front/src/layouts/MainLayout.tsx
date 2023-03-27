import { createContext } from "react";
import { Outlet } from "react-router-dom";
import { useRecoilState } from "recoil";
import Footer from "../components/common/Footer";
import Header from "../components/common/Header";
import { userState } from "../recoil/states/UserState";

export const UserDispatch = createContext<null|string>(null)
function MainLayout() {
const [user, setUser] = useRecoilState(userState)
    return (
        <div className="flex flex-col justify-center items-center max-w-[950px] m-auto">
            <Header />
              <UserDispatch.Provider value={user.acToken}>
                <Outlet />
              </UserDispatch.Provider>
            <Footer />
        </div>
    );
}

export default MainLayout;
