import axios from "axios"
import { Navigate, Outlet } from "react-router-dom"
import { useRecoilState } from "recoil"
import { userState } from "../../recoil/states/UserState"


function RequireAuth() {
    const [user, setUser] = useRecoilState(userState)
    if (user.acToken){
        GetNewAcToken()
        return  <Outlet /> 
    } else {
        return <Navigate to="/login" />
    }  
}
export function GetNewAcToken(){// userState recoil
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
export default {RequireAuth, GetNewAcToken}
