import { atom, RecoilState } from "recoil";
import { recoilPersist } from "recoil-persist";

const { persistAtom } = recoilPersist();
export interface LUser {
  seq: number;
  isLoggedIn: boolean;
  acToken: string;
  coupleId: any;
}
export const userState = atom<LUser>({
  key: "userState",
  default: {
    seq: -1,
    isLoggedIn: false,
    acToken: "",
    coupleId: "0",
  },
  // effects_UNSTABLE: [persistAtom],
});

export const loggedIn = atom({
  key: "loggedIn",
  // default: false,
  default: true,
});

export const darkMode = atom({
  key: "darkMode",
  default: true,
  effects_UNSTABLE: [persistAtom],
});
