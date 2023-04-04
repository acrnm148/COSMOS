import { atom, RecoilState } from "recoil";
import { recoilPersist } from "recoil-persist";

const JWT_EXPIRY_TIME = 24 * 3600 * 1000; // 만료 시간 (24시간 밀리 초로 표현)
const { persistAtom } = recoilPersist();
export interface LUser {
    seq: number;
    isLoggedIn: boolean;
    acToken: string;
    coupleId: string;
}
export const userState = atom<LUser>({
    key: "userState",
    default: {
        seq: -1,
        isLoggedIn: false,
        acToken: "",
        coupleId: "0",
    },
    effects_UNSTABLE: [persistAtom],
});

export const loggedIn = atom({
    key: "loggedIn",
    default: false,
});

export const darkMode = atom({
    key: "darkMode",
    default: true,
    effects_UNSTABLE: [persistAtom],
});
