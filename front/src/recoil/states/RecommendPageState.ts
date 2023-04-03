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

export const mapCenter = atom({
  key: "mapCenterRec",
  default: { lat: 0, lng: 0 },
});

export const mapMarkers = atom({
  key: "mapMarkersRec",
  default: [{}],
});

export const placeDetail = atom({
  key: "placeDetailRec",
  default: { placeId: 1, type: "tour" },
});

export const currentCourseId = atom({
  key: "currentCourseId",
  default: 0,
});
