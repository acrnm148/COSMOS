import { Outlet } from 'react-router-dom';
import Footer from '../fixtures/Footer';
import Header from '../fixtures/Header';

function MainLayout() {
  return (
    <>
      <Header />
        <Outlet />
      <Footer />
    </>
  );
}

export default MainLayout;
