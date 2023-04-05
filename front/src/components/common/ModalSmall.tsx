import React from "react";
import "../../css/modal-small.css";

export default function ModalSmall(props: any) {
  const { open, close, header, size } = props;

  return (
    <div className={open ? "openModal modal-small" : "modal-small"}>
      {open ? (
        <section className="max-w-[450px]">
          <header className="dark:bg-darkMain">
            {header}
            <button className="close" onClick={close}>
              &times;
            </button>
          </header>
          <main className="dark:bg-darkBackground">{props.children}</main>
          <footer className="dark:bg-darkBackground">
            <button className="close dark:bg-darkMain" onClick={close}>
              {size === "small" ? "확인" : "닫기"}
            </button>
          </footer>
        </section>
      ) : null}
    </div>
  );
}
