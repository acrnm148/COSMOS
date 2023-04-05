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
          <header className="dark:bg-darkMain2">
            {header}
            <button className="close" onClick={close}>
              &times;
            </button>
          </header>
          <main className="dark:bg-darkBackground">{props.children}</main>
          <footer className="dark:bg-darkBackground">
            <button className="close dark:bg-darkMain2" onClick={close}>
              {size === "small" ? "확인" : "닫기"}
            </button>
          </footer>
        </section>
      ) : null}
    </div>
  );
}
