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
  effects_UNSTABLE: [persistAtom],
});
//임시 데이터
// export const userState = atom<LUser>({
//   key: "userState",
//   default: {
//     seq: 1,
//     isLoggedIn: true,
//     acToken:
//       "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNjQ5Mjc5ODQ0IiwiZXhwIjoxNjgwODM2MjUwLCJ1c2VySWQiOiIyNjQ5Mjc5ODQ0IiwidXNlclNlcSI6MX0.350fTiMKIciFBZNGMqtxBQ6dkYnb2yEiCI3C173WUXk1YYuHhbyuQcsli_wqKzsPQTK_xzY-wINOrJNI66-5hw",
//     coupleId: "1231231234",
//     // seq: -1,
//     // isLoggedIn: false,
//     // acToken: "",
//     // coupleId: "0",
//   },
//   // effects_UNSTABLE: [persistAtom],
// });

export const loggedIn = atom({
  key: "loggedIn",
  default: false,
  effects_UNSTABLE: [persistAtom],
  // default: true,
});

export const darkMode = atom({
  key: "darkMode",
  default: true,
  effects_UNSTABLE: [persistAtom],
});
