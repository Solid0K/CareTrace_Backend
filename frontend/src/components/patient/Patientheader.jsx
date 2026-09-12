import { Link } from "react-router-dom";

function PatientHeader() {
  return (
    <header className="patient-header">
      <h1>CARETRACE</h1>

      <Link to="/caretaker" className="caregiver-button">
        👤
      </Link>
    </header>
  );
}

export default PatientHeader;