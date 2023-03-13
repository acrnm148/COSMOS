import RecommSlider from "../../components/home/RecommSlider";
import MainMenuBtn from "../../components/home/MainMenuBtn";
import MemoryComponent from "../../components/home/MemoryComponent";

export default function Home() {
  return (
    <div className="pt-20 mt-8 mx-4">
      <RecommSlider />
      <MainMenuBtn />
      <MemoryComponent />
    </div>
  );
}
