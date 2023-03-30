import styled from "styled-components";

export const Stars = styled.div`
      display: flex;
      padding-top: 5px;
      
      & svg {
        color: gray;
        cursor: pointer;
      }
      
      :hover svg {
        color: #fcc419;
      }
      
      & svg:hover ~ svg {
        color: gray;
      }
      
      .yellowStar {
        color: #fcc419;
      }`