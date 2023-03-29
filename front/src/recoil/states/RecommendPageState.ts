import { atom } from "recoil";

export const selectSido = atom({
  key: "selectSidoRec",
  default: { sidoCode: "", sidoName: "" },
});

export const selectGugun = atom({
  key: "selectGugunRec",
  default: { gugunCode: "", gugunName: "" },
});

export const selectCategory = atom({
  key: "selectCategoryRec",
  default: [{}],
});
