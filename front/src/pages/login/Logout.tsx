import { useEffect } from "react"
import { useNavigate } from "react-router";
import { useRecoilState } from "recoil";
import { userState } from "../../recoil/states/UserState";

declare const window: typeof globalThis & {
    Kakao: any;
  };
export default function Logout(){
    const navigate = useNavigate();
    // userState recoil
    const [loginUser, setLoginUser] = useRecoilState(userState)
    // 페이지 들어왔을 때 로그아웃되게
    useEffect(()=>{
        // 카카오 sdk 찾도록 초기화
        if (!window.Kakao.isInitialized()){
            window.Kakao.init(process.env.REACT_APP_KAKAO_SHARE_JS_MYE)
        }
        window.Kakao.Auth.logout()
      .then(function() {
        alert('logout ok\naccess token -> ' + window.Kakao.Auth.getAccessToken());
        setLoginUser({...loginUser,acToken:'',seq:0, isLoggedIn:false, coupleId:"" })
        console.log('loggedout')
        navigate('/')
      })
      .catch(function() {
        alert('Not logged in');
        console.log('loggedout')
      });
    })
    return(
        <>
        </>
    )
}