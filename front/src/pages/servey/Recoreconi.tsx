import React, { useCallback } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';
import { charCountState, testState } from "../../states/servey/ServeyPageState"

interface CommonState {
    value : string
}
export default function Recoreconi(){
    const [test, setTest] = useRecoilState(testState)
    const defaultRecoilTestState : CommonState = {...test}
    const onChange = useCallback((e:any) =>{
        defaultRecoilTestState.value = e.target.value
        setTest(defaultRecoilTestState)
    },[test])
    const count = useRecoilValue(charCountState)
    return (
        <>
            <h1>hi this is reco study page</h1>
            <span className='text-darkMain'>여기에 입력하면 취향설문 제목이 바뀝니다 {'>'}</span><input className='border-solid border-2 border-darkMain' type="text" value={test.value} onChange={(e)=>onChange(e)} />
            <h1>{test.value}</h1>
            <h2>{count}</h2>
        </>
    )
}