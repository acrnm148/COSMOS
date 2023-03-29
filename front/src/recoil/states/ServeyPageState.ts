import { getValue } from '@testing-library/user-event/dist/utils';
import { atom, selector, useRecoilState } from 'recoil';

////////////// Start servey Data //////////
export const bgPngUrl = atom({
    key : 'bgPngUrl',
    default : {
        'JOT' : 'https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png',
        'JAT' : 'https://user-images.githubusercontent.com/87971876/224617790-2608b469-cf8a-4816-a921-389d90917afe.png',
        'JOY' : 'https://user-images.githubusercontent.com/87971876/224617776-32bde666-af05-4edc-9ba4-dc03eb47a552.png',
        'JAY' : 'https://user-images.githubusercontent.com/87971876/224617786-5044128f-4542-4f56-821f-9a9cd5232e79.png',
        'EOT' : 'https://user-images.githubusercontent.com/87971876/224617773-f658d9a2-75a3-426a-9966-f0cd6f1824b3.png',
        'EOY' : 'https://user-images.githubusercontent.com/87971876/224617783-416e1bc1-4c2e-40bb-b4bb-2d5eaea6b487.png',
        'EAY' : 'https://i.pinimg.com/736x/75/b3/ba/75b3ba306c7f60a6d242c8ccd959d81c.jpg',
        'EAT':'https://user-images.githubusercontent.com/87971876/224617791-41df0eb7-5e5e-4f7e-8be8-e2b9d21f5cc8.png'
    }
})

// png tailwind용
export const bgPngUrlTailwind = atom({
    key : 'bgPngUrlTailwind',
    default : {
        'JOT' : "bg-[url('https://user-images.githubusercontent.com/87971876/224617778-7c532060-7f94-4082-9c1c-e27bff0b30cd.png')]",
        'JAT' : "bg-[url('https://user-images.githubusercontent.com/87971876/224617790-2608b469-cf8a-4816-a921-389d90917afe.png')]",
        'JOY' : "bg-[url('https://user-images.githubusercontent.com/87971876/224617776-32bde666-af05-4edc-9ba4-dc03eb47a552.png')]",
        'JAY' : "bg-[url('https://user-images.githubusercontent.com/87971876/224617786-5044128f-4542-4f56-821f-9a9cd5232e79.png')]",
        'EOT' : "bg-[url('https://user-images.githubusercontent.com/87971876/224617773-f658d9a2-75a3-426a-9966-f0cd6f1824b3.png')]",
        'EOY' : "bg-[url('https://user-images.githubusercontent.com/87971876/224617783-416e1bc1-4c2e-40bb-b4bb-2d5eaea6b487.png')]",
        'EAY' : "bg-[url('https://i.pinimg.com/736x/75/b3/ba/75b3ba306c7f60a6d242c8ccd959d81c.jpg')]",
        'EAT': "bg-[url('https://user-images.githubusercontent.com/87971876/224617791-41df0eb7-5e5e-4f7e-8be8-e2b9d21f5cc8.png')]"
    }
})
// 설문조사 결과 이미지
export const backgroundImageGif = atom ({
    key : 'backgroundImageGif',
    default : {
        // - 휴식(EAY) - 소소한 힐링 추구
        // - 먹방(EAT) - 내기중독 먹보
        // - 체험(JAT) - 여기 어때 JAT
        // - 추억(EOT)- 행복의 완성은 사진 EOT
        // - 예술(JOY) - 호기심 많은 게으름뱅이
        // - 가성비(JAY) - 임금 뒷편의 권력
        // - Flex(EOY) - 오늘은 제가 쏠게요
        // - 인플루언서(JOT) - 화려한 조명이 나를 감싸네
        'JOT' : "bg-[url('https://user-images.githubusercontent.com/87971876/223929252-df69cc40-1a58-4fc5-b07c-8503bd659e12.gif')]",
        'JAT' : "bg-[url('https://user-images.githubusercontent.com/87971876/223928638-bc47005c-e85e-4311-b349-c5ede8087bed.gif')]",
        'JOY' : "bg-[url('https://user-images.githubusercontent.com/87971876/223930566-fc5f372e-945f-40e5-ab9d-08a71af8f6d4.gif')]",
        'JAY' : "bg-[url('https://user-images.githubusercontent.com/87971876/223932284-fe395bcf-3eed-46a1-b34a-9628fa517892.gif')]",
        'EOT' : "bg-[url('https://user-images.githubusercontent.com/87971876/223929175-dd275fbc-a6bb-48b3-85eb-1a059a8580c7.gif')]",
        'EOY' : "bg-[url('https://user-images.githubusercontent.com/87971876/223929359-bc4ee9e0-f04e-4276-914d-4bc3cc9375eb.gif')]",
        'EAY' : "bg-[url('https://i.pinimg.com/736x/75/b3/ba/75b3ba306c7f60a6d242c8ccd959d81c.jpg')]",
        'EAT':"bg-[url('https://user-images.githubusercontent.com/87971876/223905743-c2736c6e-0a0a-44ea-9363-eb1e137f8265.gif')]"
        }
      })
export const dateCateNames = atom({
    key:'dateCateNames',
    default:{
        'JOT' : ["인플루언서","핫플 도장깨기 전문가"],
        'JAT' : ["체험","올 겨울 여기 어때"],
        'JOY' : ["예술","호기심 많은 게으름벵이"],
        'JAY' : ["가성비","효율적인 데이트플래너"],
        'EOT' : ["추억","그때의 조명..온도..습도.."],
        'EOY' : ["Flex","아낌없이 쓰는 나무"],
        'EAY' : ["휴식","소소한 힐링 추구"],
        'EAT': ["먹방","맛있는걸 찾아 떠나는 먹보"]
    }
})


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