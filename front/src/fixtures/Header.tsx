import { NavLink } from "react-router-dom";

export default function Header() {
  return (
    <div className="text-xl font-bold">
      <NavLink to="">메인페이지</NavLink> |
      <NavLink to="/servey"> 취향설문</NavLink> |
      <NavLink to="/recoreco"> Recoil React Query 공부</NavLink>
    </div>
  );
}
