export default function MemoryComponent() {
  return (
    <div className="pt-10 w-full">
      <div className="title text-xl font-bold">우리의 추억</div>
      <div className="list bg-lightMain4 w-full h-44 mt-2 p-4 rounded-2xl drop-shadow-lg flex overflow-x-scroll">
        <div className="item w-[135px] h-[135px] bg-white my-auto mr-5"></div>
        <div className="item w-[135px] h-[135px] bg-white my-auto mr-5"></div>
        <div className="item w-[135px] h-[135px] bg-white my-auto mr-5"></div>
      </div>
    </div>
  );
}
