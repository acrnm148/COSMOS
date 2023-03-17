import { atom } from 'recoil';


const JWT_EXPIRY_TIME = 24 * 3600 * 1000; // 만료 시간 (24시간 밀리 초로 표현)


export const userState = atom({
  key : 'userState',
})
export const userSeqState = atom({
  key:'userSeqState',
  default : 1,
})
