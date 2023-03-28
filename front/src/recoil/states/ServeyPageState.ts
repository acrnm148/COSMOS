import { getValue } from '@testing-library/user-event/dist/utils';
import { atom, selector, useRecoilState } from 'recoil';

////////////// Start servey Data //////////
// 이전버튼으로 돌아가기 위해서는 해당 문항에 대한 값을 바꿔야하기 때문에
export const serveyChoice = atom({
    key:'serveyChoice',
    default : {1:'', 2:'', 3:'', 4:'', 5:'', 6:'', 7:'', 8:'', 9:''}
})

// 설문조사 페이지 (0: 설문시작, 1~9 : 질문, 10:설문결과)

export const serveyPage = atom({
    key:'serveyPage',
    default:0 //설문시작하기부터 시작
})

// 커플매칭을 위해 들어온사람의 coupleId 임시저장
export const invitedCoupleId = atom({
    key: 'invitedCoupleId',
    default : ''
})
// 커플매칭을 위해 들어온 사람의 커플의 userId 임시저장
export const invitedUserId = atom({
    key: 'invitedUserId',
    default : '',
})