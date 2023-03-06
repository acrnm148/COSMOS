import { BrowserRouter, Route, Routes } from 'react-router-dom';
import Footer from './fixtures/Footer';
import Header from './fixtures/Header';
import Servey from "./pages/servey/ServeyPage"
function App() {
  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path='/servey'element={<Servey />} />
      </Routes>
      <Footer />
    </BrowserRouter>
      
  );
}

export default App;
