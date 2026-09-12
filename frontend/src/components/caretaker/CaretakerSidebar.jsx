import { NavLink } from "react-router-dom";

function CaretakerSidebar() {
    return (
        <aside className="caretaker-sidebar">
            <h1>CARETRACE</h1>

            <nav className="caretaker-nav">
                <NavLink
                    to="/caretaker"
                    end
                    className={({ isActive }) =>
                        isActive ? "caretaker-nav-link active" : "caretaker-nav-link"
                    }
                >
                    🏠 Overview
                </NavLink>
                <NavLink to="/caretaker/patients" className={({ isActive }) =>
                    isActive ? "caretaker-nav-link active" : "caretaker-nav-link"
                }>
                    👥 Patients
                </NavLink>
                <NavLink to="/caretaker/medicines" className={({ isActive }) =>
                    isActive ? "caretaker-nav-link active" : "caretaker-nav-link"
                }>
                    💊 Medicines
                </NavLink>
                <NavLink to="/caretaker/routines" className={({ isActive }) =>
                    isActive ? "caretaker-nav-link active" : "caretaker-nav-link"
                }>
                    🔄 Routines
                </NavLink>
                <NavLink to="/caretaker/reminders" className={({ isActive }) =>
                    isActive ? "caretaker-nav-link active" : "caretaker-nav-link"
                }>
                    🔔 Reminders
                </NavLink>
                <NavLink to="/caretaker/locations" className={({ isActive }) =>
                    isActive ? "caretaker-nav-link active" : "caretaker-nav-link"
                }>
                    📍 Locations
                </NavLink>
                <NavLink to="/caretaker/safe-zones" className={({ isActive }) =>
                    isActive ? "caretaker-nav-link active" : "caretaker-nav-link"
                }>
                    🛡️ Safe Zones
                </NavLink>
            </nav>

            <NavLink to="/caretaker/settings" className={({ isActive }) =>
                isActive ? "caretaker-nav-link active" : "caretaker-nav-link"
            }>
                ⚙️ Settings
            </NavLink>
        </aside>
    );
}

export default CaretakerSidebar;