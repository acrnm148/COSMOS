import React, { useEffect, useState } from "react";
import axios from "axios";
import { useRecoilState, useSetRecoilState } from "recoil";
// import { isLoggedInState,userSeqState, acTokenState, coupleIdState } from "../../recoil/states/UserState";
import { Navigate, useNavigate } from "react-router";
import {
    darkMode,
    loggedIn,
    LUser,
    userState,
} from "../../recoil/states/UserState";
import { invitedCoupleId } from "../../recoil/states/ServeyPageState";

declare const window: typeof globalThis & {
    Kakao: any;
    document: any;
};
export default function KakaoLogin() {
    const navigate = useNavigate();

    // userState recoil
    const [LoginUser, setLoginUser] = useRecoilState<LUser>(userState);

    // couple매칭으로 들어온 사람은 로그인 후 설문페이지로 이동시킴
    const [invited, setInvited] = useState(false);
    const [invitedId, x] = useRecoilState(invitedCoupleId);
    const [isLoggedIn, setIsLogin] = useRecoilState(loggedIn);
    const [isSolo, setIsSolo] = useRecoilState(darkMode);

    if (invitedId) {
        setInvited(true);
    }

    const params = new URLSearchParams(window.location.search);
    let code: any = params.get("code");
    useEffect(() => {
        CosmosLogin(code, setIsLogin, setLoginUser);
    });

    function CosmosLogin(
        code: string,
        setIsLogin: Function,
        setLoginUser: Function
    ) {
        axios({
            url: "https://j8e104.p.ssafy.io/api/accounts/auth/login/kakao",
            method: "GET",
            params: { code },
        })
            .then((res: { data: any }) => {
                // 로그인 예외처리
                if (res.data.code === "444") {
                    console.log("카카오 프로필 정보가 없는 사용자");
                    alert("카카오톡 회원정보가 없습니다");
                    if (invited) {
                        navigate("/servey");
                    } else {
                        navigate("/");
                    }
                } else {
                    // 응답받은 userSeq 저장
                    const us = res.data.userSeq;
                    const user = {
                        seq: Number(us),
                        isLoggedIn: true,
                        acToken: res.data.accessToken,
                        coupleId: res.data.coupleId,
                    };
                    setLoginUser(user);
                    // setLoginUser({seq:us, isLoggedIn:true, acToken:res.data.accessToken, coupleId:res.data.coupleId})
                    console.log("코스모스 로그인 성공", res);
                    setIsLogin(true);

                    if (user.coupleId != 0) {
                        setIsSolo(false);
                    }

                    navigate("/");
                }
            })
            .catch((err: any) => {
                console.log("코스모스 로그인 실패", err);
                navigate("/");
                // 카카오 로그아웃요청
            });
    }
    return <div> </div>;
}
export const onLoginSuccess = (
    seq: number,
    ac: string,
    login: boolean,
    coupleId: any,
    setLoginUser: Function
) => {
    const JWT_EXPIRRY_TIME = 29 * 60 * 1000; // 29분
    // userState recoil
    // 로그인 후 29분마다 토큰 재발급요청
    //  TODO : 로그아웃시 clearTimeout 로직 추가
    let autoLogin = setInterval(() => {
        axios
            .get(`${process.env.REACT_APP_API_URL}/access/${seq}`, {
                headers: {
                    Authorization: `Bearer ${ac}`,
                },
            })
            .then((res) => {
                console.log(res.data);
                setLoginUser({
                    seq: seq,
                    isLoggedIn: true,
                    acToken: res.data.accessToken,
                    coupleId: coupleId,
                });
                return "refresh acTocken sucess";
            })
            .catch((err) => {
                console.log(err);
                return "refresh acTocken error";
            });
    }, JWT_EXPIRRY_TIME);
    if (!login) {
        clearInterval(autoLogin);
    }
    return;
};
