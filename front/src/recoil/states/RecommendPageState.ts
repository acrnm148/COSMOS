import { atom } from "recoil";

export const selectSidoState = atom({
  key: "recoilSidoState",
  default: { sidoCode: null, name: null },
});

export const recommendState = atom({
  key: "recoilRecommendState",
  default: { sido: null, gugun: null, categories: [], userSeq: null },
});
