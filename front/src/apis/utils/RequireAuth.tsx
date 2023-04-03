import axios from "axios"
import { Navigate, Outlet } from "react-router-dom"
import { useRecoilState } from "recoil"
import { userState } from "../../recoil/states/UserState"
import { OnLoginSuccess } from "../../pages/login/KakaoLogin"
import { useState } from "react"


export default function RequireAuth() {
    const [user, setUser] = useRecoilState(userState)
    console.log(user.acToken)

    if (user.acToken && user.seq){
        
        OnLoginSuccess(user.seq, user.acToken, true)
        return  <div><Outlet /> </div>
    } else {
        return <div><Navigate to="/login" /></div>
    }  
}
function GetNewAcToken(){// userState recoil
    const [LoginUser, setLoginUser] = useRecoilState(userState)
    axios .get(`${process.env.REACT_APP_API_URL}/access/${LoginUser.seq}`, {
        headers: {
            Authorization: `Bearer ${LoginUser.acToken}`,
        },
    })
    .then((res) => {
        console.log(res.data)
        setLoginUser({seq:LoginUser.seq, isLoggedIn:true, acToken:res.data.accessToken, coupleId:LoginUser.coupleId})
        return 'refresh acTocken sucess'
    }).catch((err)=>{
        console.log(err)
        return 'refresh acTocken error'
    })
    return 
}

