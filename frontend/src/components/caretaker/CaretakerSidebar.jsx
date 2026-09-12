import { Link } from "react-router-dom";

function CaretakerSidebar() {
  return (
    <aside className="caretaker-sidebar">
      <h1>CARETRACE</h1>

      <nav className="caretaker-nav">
        <Link to="/caretaker">🏠 Overview</Link>
        <Link to="/caretaker/patients">👥 Patients</Link>
        <Link to="/caretaker/medicines">💊 Medicines</Link>
        <Link to="/caretaker/routines">🔄 Routines</Link>
        <Link to="/caretaker/reminders">🔔 Reminders</Link>
        <Link to="/caretaker/locations">📍 Locations</Link>
        <Link to="/caretaker/safe-zones">🛡️ Safe Zones</Link>
      </nav>

      <Link to="/caretaker/settings" className="caretaker-settings">
        ⚙️ Settings
      </Link>
    </aside>
  );
}

export default CaretakerSidebar;