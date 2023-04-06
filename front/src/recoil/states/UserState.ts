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
<<<<<<< HEAD
    seq: -1,
    isLoggedIn: false,
    acToken: "",
    coupleId: "0",
=======
    seq: 98,
    isLoggedIn: true,
    acToken:
      "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNzI3ODc5MzkyIiwiZXhwIjoxNjgwODEyMTgyLCJ1c2VySWQiOiIyNzI3ODc5MzkyIiwidXNlclNlcSI6MTl9.wvYGz4x64YuUE_fQAPB-L1UM1_yzPiWiNrbhWDNCtoBx6iRcNOtdquCRxx1oGM5Oo5emR1HrSH4_sfnRismCCw",
    coupleId: "226927908",
    // seq: -1,
    // isLoggedIn: false,
    // acToken: "",
    // coupleId: "0",
>>>>>>> c7337cf393bb212864516e3eda571f9e67ce4b4e
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
