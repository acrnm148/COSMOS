import { NavLink } from "react-router-dom";

export default function Header(){
    return(
        <div className="text-3xl font-bold">
            <NavLink to="">메인페이지</NavLink> |
            <NavLink to="/servey">취향설문</NavLink>
        </div>
    )
}