export default function ServeyPage(){
    return(
        <>
            <hr />
            <div className="bg-slate-400 grid gap-10 lg:grid-cols-2 xl:grid-cols-3 xl:place-content-center min-h-screen">
            <div className="bg-darkBackground sm:bg-red-400 md:bg-orange-300 lg:bg-yellow-400 xl:bg-green-300 2xl:bg-blue-300 p-6 rounded-3xl shadow-xl place-content-center">
                <h1 className="font-bold font-baloo text-2xl text-darkMain">데이트 취향설문</h1>
                <span className="font-baloo text-base text-darkMain">설문을 기반으로 데이트코스를 맞춤 추천해드려요.</span>
                <div className="mt-7 mb-7 w-full text-center">
                    <span className="font-baloo text-base text-darkMain">1 / 7</span>
                    <div className="border-solid border-2 h-5 border-darkMain w-full"><div className="h-full bg-darkMain w-10"></div></div>
                </div>
                <h1 className="font-baloo text-base text-white mb-2">1. 데이트 시 가장 중요한 포인트는?</h1>
                <ul className="flex flex-wrap w-full grid-rows-3 grid-flow-col sm:gap-1 gap-2 justify-center">
                {['맛집탐방', '관광명소', '감성낭만', '문화경험', '카페투어', ,'빵지순례', '엑티비티', '쇼핑하기', '휴식하기'].map((item, key) => {
                    return (
                    <div
                        key={key}
                        className="sm:p-8 p-10 flex bg-darkMain4 pt-12 pb-12 rounded-lg 
                                    hover:bg-opacity-75" 
                    >
                        <div className="text-white">{item}</div>
                    </div>
                    );
                })}
                </ul>
                <div className="flex justify-between mt-2 pt-2 border-t-2 border-dashed">
                </div>
                <button
                className="flex border-solid border-2 border-darkMain justify-center mt-5 text-darkMain p-3 text-center rounded-lg w-3/4 mx-auto 
                hover:bg-darkMain hover:text-white
                "
                >
                다음
                </button>
            </div>
            {/* <div className="bg-white overflow-hidden rounded-3xl shadow-xl">
                <div className="bg-blue-500 p-6 pb-14">
                <span className="text-white text-2xl">프로필</span>
                </div>
                <div className="rounded-3xl p-6 bg-white relative -top-5">
                <div className="flex relative -top-16 items-end justify-between">
                    <div className="flex flex-col items-center">
                    <span className="text-xs text-gray-500">Orders</span>
                    <span className="font-medium">340</span>
                    </div>
                    <div className="h-24 w-24 bg-zinc-300 rounded-full" />
                    <div className="flex flex-col items-center">
                    <span className="text-xs text-gray-500">Spent</span>
                    <span className="font-medium">$340</span>
                    </div>
                </div>
                <div className="relative  flex flex-col items-center -mt-14 -mb-5">
                    <span className="text-lg font-medium">KKANA KANA</span>
                    <span className="text-sm text-gray-500">KANA</span>
                </div>
                </div>
            </div>
            <div className="bg-white p-6 rounded-3xl shadow-xl">
                <div className="flex mb-5 justify-between items-center">
                <span>⬅️</span>
                <div className="space-x-3">
                    <span>😀4.9</span>
                    <span className="shadow-xl p-2 rounded-md">💖</span>
                </div>
                </div>
                <div className="bg-zinc-400 h-72 mb-5" />
                <div className="flex flex-col">
                <span className="font-medium text-xl">맛있는 음식</span>
                <span className="text-xs text-gray-500">Chair</span>
                <div className="mt-3 mb-5 flex justify-between items-center">
                    <div className="space-x-2">
                    <button className="w-5 h-5 rounded-full bg-red-500 focus:ring-2 ring-offset-2 ring-red-500 transition" />
                    <button className="w-5 h-5 rounded-full bg-indigo-500 focus:ring-2 ring-offset-2 ring-indigo-500 transition" />
                    <button className="w-5 h-5 rounded-full bg-violet-600 focus:ring-2 ring-offset-2 ring-violet-500 transition" />
                    </div>
                    <div className="flex items-center space-x-5">
                    <button className="rounded-lg bg-blue-200 flex justify-center items-center aspect-square w-8 text-xl text-gray-500">
                        -
                    </button>
                    <span>1</span>
                    <button className="rounded-lg bg-blue-200 flex justify-center items-center aspect-square w-8 text-xl text-gray-500">
                        +
                    </button>
                    </div>
                </div>
                </div>
                <div className="flex justify-around items-center">
                <span className="font-medium text-2xl">$450</span>
                <button className="bg-blue-500 p-2 px-5 text-center text-xs text-white rounded-lg">
                    Add to cart
                </button>
                </div>
            </div> */}
            </div>
        </>
    )
}