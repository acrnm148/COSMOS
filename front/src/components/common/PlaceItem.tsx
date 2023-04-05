import { useEffect } from "react"
import drgnDrop from "../../assets/schedule/dragndrop.png"
interface Place{
    idx : number,
    name : string,
    imgUrl : string,
    category : string,
    location : string,
}
export function PlaceItem(props:{place:Place}){
    const plc:Place = props.place
    useEffect(()=>{
    })
    return (
        <div className="flex w-full h-40 bg-calendarGray mt-2 rounded-lg justify-between p-2">
            <div className='h-full w-40 bg-calendarDark rounded-lg overflow-hidden'>
                <img className="h-full w-full" src={plc.imgUrl} alt="" />
            </div>
            <div className="flex flex-col w-4/6">
                <div className="flex justify-start ml-2 text-sm">
                    <div className="">{plc.location}</div>
                    <p className="ml-1 mr-1">|</p>
                    <div>{plc.category}</div>
                </div>
                <div className="ml-2 font-bold">{plc.name}</div>
            </div>
            <div className="flex items-center">
                <img src={drgnDrop} alt="" />
            </div>
        </div>
    )
}