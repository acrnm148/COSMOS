import { atom } from "recoil";

export const selectSidoState = atom({
  key: "recoilSidoState",
  default: { sidoCode: null, name: null },
});
