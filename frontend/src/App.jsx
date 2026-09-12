import { BrowserRouter, Routes, Route } from "react-router-dom";
import "./App.css";
import PatientHome from "./pages/patient/PatientHome";
import Medicines from "./pages/patient/Medicines";
import Appointments from "./pages/patient/Appointments";
import Todos from "./pages/patient/Todos";
import Activities from "./pages/patient/Activities";

import CaretakerLayout from "./pages/caretaker/CaretakerLayout";
import CaretakerOverview from "./pages/caretaker/CaretakerOverview";
import CaretakerSection from "./pages/caretaker/CaretakerSection";

function App() {
  return (
    <BrowserRouter>
      <Routes>

        {/* Patient */}
        <Route path="/" element={<PatientHome />} />
        <Route path="/medicines" element={<Medicines />} />
        <Route path="/appointments" element={<Appointments />} />
        <Route path="/todos" element={<Todos />} />
        <Route path="/activities" element={<Activities />} />

        {/* Caretaker */}
        <Route path="/caretaker" element={<CaretakerLayout />}>
          <Route index element={<CaretakerOverview />} />
          <Route path="patients" element={<CaretakerSection title="Patients" description="Manage the people connected to your care." />} />
          <Route path="medicines" element={<CaretakerSection title="Medicines" description="Review and manage patient medicines." />} />
          <Route path="routines" element={<CaretakerSection title="Routines" description="Keep track of daily care routines." />} />
          <Route path="reminders" element={<CaretakerSection title="Reminders" description="Review upcoming care reminders." />} />
          <Route path="locations" element={<CaretakerSection title="Locations" description="Manage important patient locations." />} />
          <Route path="safe-zones" element={<CaretakerSection title="Safe Zones" description="Manage patient safe zones." />} />
          <Route path="settings" element={<CaretakerSection title="Settings" description="Update your caregiver account settings." />} />
        </Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;