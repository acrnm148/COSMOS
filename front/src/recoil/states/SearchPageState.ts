import { atom } from "recoil";

export const clickBackground = atom({
  key: "clickBackground",
  default: true,
});

export const selectSido = atom({
  key: "selectSido",
  default: { sidoCode: "", sidoName: "" },
});

export const selectGugun = atom({
  key: "selectGugun",
  default: { gugunCode: "", gugunName: "" },
});

export const searchWord = atom({
  key: "searchWord",
  default: "",
});

export const completeWord = atom({
  key: "completeword",
  default: "",
});

export const selectCategory = atom({
  key: "selectCategory",
  default: "",
});

export const selectOnePlace = atom({
  key: "selectOnePlace",
  default: {},
});

export const mapCenter = atom({
  key: "mapCenter",
  default: { lat: 0, lng: 0 },
});

export const mapMarkers = atom({
  key: "mapMarkers",
  default: [{}],
});

export const placeDetail = atom({
  key: "placeDetail",
  default: { placeId: 1, type: "tour" },
});
