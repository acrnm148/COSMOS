import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { BrowserRouter, Route, Router, RouterProvider, Routes } from "react-router-dom";

// React Query
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from "react-query/devtools";
// Recoil
import { RecoilRoot } from "recoil";

import router from "./routes";
import Loading from "./components/common/Loading";
import RequireAuth from "./apis/utils/RequireAuth";

const client = new QueryClient();

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);

root.render(
  <RecoilRoot>
    <QueryClientProvider client={client}>
      <RouterProvider router={router} fallbackElement={<Loading />} />
      <ReactQueryDevtools initialIsOpen={false} position="bottom-right" />
    </QueryClientProvider>
  </RecoilRoot>
);
