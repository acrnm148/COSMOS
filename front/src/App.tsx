import { BrowserRouter, Route, Routes } from "react-router-dom";
import Servey from "./pages/servey/ServeyPage";
import Reco from "./pages/servey/Recoreconi"; //test용

import SubLayout from "./layouts/SubLayout";
import PlaceSearch from "./pages/search/PlaceSearch";

// React Query
import { QueryClient, QueryClientProvider } from "react-query";
import { ReactQueryDevtools } from "react-query/devtools";
// Recoil
import { RecoilRoot } from "recoil";
import MainLayout from "./layouts/MainLayout";
import ServeyResult from "./pages/servey/ServeyResult";

function App() {
  const client = new QueryClient();
  return (
    <RecoilRoot>
      <QueryClientProvider client={client}>
        <BrowserRouter>
          <Routes>
            {/* Header,Footer,Nav를 
                  보여주는 페이지 */}
            <Route element={<MainLayout />}>
              <Route path="/recoreco" element={<Reco />} />
            </Route>
            {/* 안 보여주는 페이지 */}
            <Route path="/servey" element={<Servey />} />
            <Route path="/result/:cate/:cateNum" element={<ServeyResult />} />

            {/* 장소 검색 페이지 */}
            <Route element={<SubLayout />}>
              <Route path="/place/search" element={<PlaceSearch />} />
            </Route>
          </Routes>
        </BrowserRouter>
        <ReactQueryDevtools initialIsOpen={false} position="bottom-right" />
      </QueryClientProvider>
    </RecoilRoot>
  );
}

export default App;
