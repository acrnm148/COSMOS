// TMAP API
declare global {
  interface Window {
    Tmapv2: typeof Tmapv2;
  }

  namespace Tmapv2 {
    class Map {
      constructor(element: HTMLElement, options: MapOptions);
      setCenter(center: LatLng): void;
    }
    class LatLng {
      constructor(latitude: number, longitude: number);
    }
    class Marker {
      constructor(element: HTMLElement, options: MarkerOptions);
    }

    interface MapOptions {
      center: LatLng;
      width: string;
      height: string;
      zoom: number;
    }
    interface MarkerOptions {
      position: LatLng;
      Icon: string;
      map: Map;
    }
  }
}

export {};
