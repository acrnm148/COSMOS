import Cosmos from "../../assets/login/pinkCosmos.png";

export default function Login() {
    return (
        <>
            <div className="w-full flex flex-col content-center justify-center p-10 items-center">
                <img src={Cosmos} alt="" />
                <p className="text-sm">간편하게 코스모스를 시작해보세요</p>
                {/* https://kauth.kakao.com/oauth/authorize?client_id=097d883a03c0da953d919d990701da5f&redirect_uri=http://localhost:3000/login/oauth&response_type=code&scope=age_range,birthday */}
                {/* https://kauth.kakao.com/oauth/authorize?client_id=097d883a03c0da953d919d990701da5f&redirect_uri=https://j8e104.p.ssafy.io/api/login/oauth2/code/kakao&response_type=code&scope=age_range,birthday */}
                <a
                    href={`https://kauth.kakao.com/oauth/authorize?client_id=097d883a03c0da953d919d990701da5f&redirect_uri=http://localhost:3000/login/oauth&response_type=code`}
                >
                    <button className="h-12 w-5/6 rounded-md flex justify-center">
                        <img
                            src="https://k.kakaocdn.net/14/dn/btroDszwNrM/I6efHub1SN5KCJqLm1Ovx1/o.jpg"
                            width="222"
                            alt="카카오 로그인 버튼"
                        />
                    </button>
                </a>
            </div>
        </>
    );
}
