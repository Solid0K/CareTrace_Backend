import CaretakerSidebar from "../../components/caretaker/CaretakerSidebar";

function CaretakerOverview() {
  return (
    <div className="caretaker-layout">
      <CaretakerSidebar />

      <main className="caretaker-main">
        <h1>Overview</h1>
        <p>Welcome to the CARETRACE caretaker dashboard.</p>
      </main>
    </div>
  );
}

export default CaretakerOverview;