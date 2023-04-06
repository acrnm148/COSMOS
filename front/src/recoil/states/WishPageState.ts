import { atom } from "recoil";

export const mapCenter = atom({
    key: "mapCenterRecWish",
    default: { lat: 0, lng: 0 },
});

export const mapMarkers = atom({
    key: "mapMarkersRecWish",
    default: [{}],
});

export const placeDetail = atom({
    key: "placeDetailRecWish",
    default: { placeId: 1, type: "tour" },
});
