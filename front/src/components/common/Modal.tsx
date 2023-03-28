import React from "react";
import "../../css/modal.css";

export default function Modal(props: any) {
  const { open, close, header, size } = props;

  return (
    <div className={open ? "openModal modal" : "modal"}>
      {open ? (
        <section
          className={size === "small" ? "max-w-[450px]" : "max-w-[800px]"}
        >
          <header>
            {header}
            <button className="close" onClick={close}>
              &times;
            </button>
          </header>
          <main>{props.children}</main>
          <footer>
            <button className="close" onClick={close}>
              {size === "small" ? "확인" : "닫기"}
            </button>
          </footer>
        </section>
      ) : null}
    </div>
  );
}
