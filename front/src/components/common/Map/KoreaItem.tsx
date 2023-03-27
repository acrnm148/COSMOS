import React from "react";

export default function SouthKoreaItem(props: any) {
  const { title } = props;
  return (
    <path
      onMouseEnter={(e) => {
        const infoBox = document.querySelector<HTMLElement>(`.info`);
        if (infoBox !== null) {
          infoBox.style.display = "block";
          infoBox.textContent = `${title}`;
        }
      }}
      onMouseLeave={() => {
        const infoBox = document.querySelector<HTMLElement>(`.info`);
        if (infoBox !== null) {
          infoBox.style.display = "none";
        }
      }}
      onMouseMove={(e) => {
        const infoBox = document.querySelector<HTMLElement>(`.info`);
        if (infoBox !== null) {
          infoBox.style.top = `${e.clientY - 60}px`;
          infoBox.style.left = `${e.clientX}px`;
        }
      }}
      {...props}
    ></path>
  );
}
