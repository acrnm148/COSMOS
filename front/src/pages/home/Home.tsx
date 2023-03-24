import RecommSlider from "../../components/home/RecommSlider";
import MainMenuBtn from "../../components/home/MainMenuBtn";
import MemoryComponent from "../../components/home/MemoryComponent";

export default function Home() {
  return (
    <div className="pb-24 mt-8 mx-4 w-full h-full">
      <RecommSlider />
      <MainMenuBtn />
      <MemoryComponent />
    </div>
  );
}
