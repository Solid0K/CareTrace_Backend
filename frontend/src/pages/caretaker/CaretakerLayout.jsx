import CaretakerHeader from "../../components/caretaker/CaretakerHeader";
import CaretakerSidebar from "../../components/caretaker/CaretakerSidebar";
import { Outlet } from "react-router-dom";

function CaretakerLayout() {
  return (
    <div className="caretaker-layout">
      <CaretakerHeader />

      <div className="caretaker-body">
        <CaretakerSidebar />

        <main className="caretaker-main">
          <Outlet />
        </main>
      </div>
    </div>
  );
}

export default CaretakerLayout;