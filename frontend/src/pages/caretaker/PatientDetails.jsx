import { Link, useParams } from "react-router-dom";
function PatientDetails() {
    const { id } = useParams();
    const patientId = Number(id);
    const patients = [
        {
            id: 1,
            name: "Rajesh Kumar",
            status: "All good",
            next: "Medicine — 10:00 AM",
        },
        {
            id: 2,
            name: "Anita Sharma",
            status: "Needs attention",
            next: "Medicine not confirmed — 9:00 AM",
        },
        {
            id: 3,
            name: "Mohan Singh",
            status: "All good",
            next: "Appointment — 3:00 PM",
        },
    ];
    const patient = patients.find((patient) => patient.id === patientId);
    if (!patient) {
        return (
            <main>
                <h1>Patient not found</h1>
                <p>The patient you are looking for does not exist.</p>
                <Link to="/caretaker">← Back to Overview</Link>
            </main>
        );
    }
    return (
        <main className="patient-details">

            <Link to="/caretaker" className="back-overview">
                ← Back to Overview
            </Link>

            <section className="patient-details-header">
                <div>
                    <h1>{patient.name}</h1>
                    <p>Patient overview</p>
                </div>

                <span
                    className={
                        patient.status === "All good"
                            ? "patient-status status-good"
                            : "patient-status status-attention"
                    }
                >
                    {patient.status === "All good" ? "●" : "⚠"}{" "}
                    {patient.status}
                </span>
            </section>

            <section className="patient-summary">
                <div className="patient-summary-card">
                    <span>💊</span>
                    <div>
                        <strong>3</strong>
                        <p>Medicines today</p>
                    </div>
                </div>

                <div className="patient-summary-card">
                    <span>🔔</span>
                    <div>
                        <strong>1</strong>
                        <p>Upcoming reminder</p>
                    </div>
                </div>

                <div className="patient-summary-card">
                    <span>📍</span>
                    <div>
                        <strong>At home</strong>
                        <p>Current location</p>
                    </div>
                </div>

            </section>
            <section className="patient-schedule">
                <div className="section-heading">
                    <h2>Today's Schedule</h2>
                </div>

                <div className="schedule-list">

                    <div className="schedule-item">
                        <strong>10:00 AM</strong>

                        <div>
                            <h3>💊 Morning medicine</h3>
                            <p>Blood pressure medicine</p>
                        </div>
                    </div>

                    <div className="schedule-item">
                        <strong>3:00 PM</strong>

                        <div>
                            <h3>📅 Doctor appointment</h3>
                            <p>City Hospital</p>
                        </div>
                    </div>

                    <div className="schedule-item">
                        <strong>8:00 PM</strong>

                        <div>
                            <h3>💊 Evening medicine</h3>
                            <p>1 tablet</p>
                        </div>
                    </div>

                </div>
            </section>
            <section className="important-people">

                <div className="section-heading">
                    <h2>Important People</h2>
                </div>

                <div className="people-list">

                    <div className="person-card">
                        <div className="person-icon">👩</div>

                        <div className="person-info">
                            <h3>Sunita Singh</h3>
                            <p>Daughter</p>
                            <span>📞 Contact</span>
                        </div>
                    </div>

                    <div className="person-card">
                        <div className="person-icon">👨</div>

                        <div className="person-info">
                            <h3>Rahul Singh</h3>
                            <p>Son</p>
                            <span>📞 Contact</span>
                        </div>
                    </div>

                </div>

            </section>
        </main>
    );
}

export default PatientDetails;