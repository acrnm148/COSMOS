import React from "react";
import LoadingGif from "../../assets/loading/cosmos-loading.gif";

export default function Loading() {
    return (
        <div className="h-[calc(100vh-10rem)] w-screen text-center flex bg-white">
            <div className="m-auto">
                <img src={LoadingGif} alt="logo" className="h-16 w-16 m-auto" />
                <div className="mt-4 text-xl font-medium">Loading...</div>
            </div>
        </div>
    );
}
