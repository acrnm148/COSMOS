import { atom, RecoilState } from "recoil";
import { recoilPersist } from "recoil-persist";

const JWT_EXPIRY_TIME = 24 * 3600 * 1000; // 만료 시간 (24시간 밀리 초로 표현)
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
//     key: "userState",
//     default: {
//         seq: 1,
//         isLoggedIn: true,
//         acToken: "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNjQ5Mjc5ODQ0IiwiZXhwIjoxNjgwNzYzMTQ1LCJ1c2VySWQiOiIyNjQ5Mjc5ODQ0IiwidXNlclNlcSI6MX0.b7BVn3gufzTyH-Qt5HBxiw2QT7UmfrEbW5JzBFJMFXkJVyMxWCVCWQOAs_yEWFwpN5obYbieSylZ4RuHr6rwCQ",
//         coupleId: "0",
//         // seq: -1,
//         // isLoggedIn: false,
//         // acToken: "",
//         // coupleId: "0",
//     },
//     effects_UNSTABLE: [persistAtom],
// });

export const loggedIn = atom({
    key: "loggedIn",
    default: false,
    // default : true
});

export const darkMode = atom({
    key: "darkMode",
    default: true,
    effects_UNSTABLE: [persistAtom],
});
