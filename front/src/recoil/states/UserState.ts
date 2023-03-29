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
        seq: 1,
        isLoggedIn: false,
        acToken:
            "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyNTQzNTA5NTU0IiwiZXhwIjoxNjgwMDc2NjY2LCJ1c2VySWQiOiIyNTQzNTA5NTU0IiwidXNlclNlcSI6MX0.avVEbKcFaDmSWySDm11xGtqOdaMWvPrQIxb9OOXvD6FOLrm4IVPkD-QDhZCMQzIV_sIIPhmAhRXZI3I5FET4ig",
        coupleId: "",
    },
    effects_UNSTABLE: [persistAtom],
});
