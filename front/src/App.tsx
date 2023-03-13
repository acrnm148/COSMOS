import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Footer from './fixtures/Footer';
import Header from './fixtures/Header';
import Servey from "./pages/servey/ServeyPage"
import Reco from "./pages/servey/Recoreconi" //test용

// React Query
import { QueryClient, QueryClientProvider } from 'react-query'
import { ReactQueryDevtools } from 'react-query/devtools'
// Recoil
import { RecoilRoot } from "recoil";
import MainLayout from './components/MainLayout';
import ServeyResult from './pages/servey/ServeyResult';

function App() {
  const client = new QueryClient()
  return (
    <RecoilRoot>
      <QueryClientProvider client={client}>
        <BrowserRouter>
  
          <Routes>
            {/* Header,Footer,Nav를 
                  보여주는 페이지 */}
            <Route element={<MainLayout />}>
              <Route path='/recoreco'element={<Reco />} />
            </Route>
            {/* 안 보여주는 페이지 */}
            <Route path='/servey'element={<Servey />} />
            <Route path='/result/:cate/:cateNum' element={<ServeyResult />} />
          </Routes>

        </BrowserRouter>
        <ReactQueryDevtools initialIsOpen={false} position='bottom-right' />
        </QueryClientProvider>
    </RecoilRoot>
  );
}

export default App;
