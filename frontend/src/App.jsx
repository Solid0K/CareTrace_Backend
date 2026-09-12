import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import CaretakerOverview from "./pages/caretaker/CaretakerOverview";
import PatientHome from "./pages/patient/PatientHome";
import Medicines from "./pages/patient/Medicines";
import Appointments from "./pages/patient/Appointments";
import Todos from "./pages/patient/Todos";
import Activities from "./pages/patient/Activities";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        <Route path="/" element={<PatientHome />} />

        <Route path="/medicines" element={<Medicines />} />

        <Route path="/appointments" element={<Appointments />} />

        <Route path="/todos" element={<Todos />} />

        <Route path="/activities" element={<Activities />} />

        <Route path="/caretaker" element={<CaretakerOverview />} />

      </Routes>
    </BrowserRouter>
  );
}

export default App;