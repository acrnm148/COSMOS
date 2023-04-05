import React from "react";
import "../../css/modal-small.css";

export default function ModalSmall(props: any) {
  const { open, close, header, size } = props;

  return (
    <div className={open ? "openModal modal-small" : "modal-small"}>
      {open ? (
        <section className="max-w-[450px]">
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
