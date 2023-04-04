import React from "react";
import LoadingGif from "../../assets/loading/cosmos-loading.gif";
import LoadingGif2 from "../../assets/loading/modal-loading.gif";
export default function Loading() {
  return (
    <div className="h-[calc(100vh-10rem)] text-center flex bg-white justif-center">
      <div className="m-auto">
        <img src={LoadingGif} alt="logo" className="h-20 w-20 m-auto" />
        <div className="mt-4 text-xl font-medium">
          <img src={LoadingGif2} alt="" />
        </div>
      </div>
    </div>
  );
}
