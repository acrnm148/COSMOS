import { atom } from 'recoil';


const JWT_EXPIRY_TIME = 24 * 3600 * 1000; // 만료 시간 (24시간 밀리 초로 표현)

// 로그인 여부 boolean
export const isLoggedInState = atom({
  key : 'isLoggedIn',
  default : false,
})
export const userSeqState = atom({
  key:'userSeqState',
  default : 1,
})

// accessToken, userSeq, coupleId
// string
export const acTokenState = atom({
  key : 'userInfoState',
  default : ''
})

// string
export const coupleIdState = atom({
  key : 'coupleIdState',
  default : ''
})