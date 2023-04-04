import axios from "axios"
import { Navigate, Outlet } from "react-router-dom"
import { useRecoilState } from "recoil"
import { loggedIn, userState } from "../../recoil/states/UserState"
import { onLoginSuccess } from "../../pages/login/KakaoLogin"
import React, { useEffect, useState } from "react"


export default function RequireAuth(props:any) {
    const [user, setUser] = useRecoilState(userState)
    const[ acChecked, setAcChecked] = useRecoilState(loggedIn)
    // console.log(user)
    // console.log(user.acToken , user.seq,acChecked)
    useEffect(()=>{
        if(!acChecked){
            getNewAcToken(user, setUser)
            setAcChecked(true)
        } 
    },[acChecked])
    if (user.acToken && user.seq){
        onLoginSuccess(user.seq, user.acToken, true, user.coupleId, setUser)
        return  <div><Outlet /> </div>
    } else {
        console.log(user)
        console.log('there is NO acToken')
        return <div><Navigate to="/login" /></div>
    }  
}

function getNewAcToken(LoginUser:any, setLoginUser:Function){// userState recoil
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

