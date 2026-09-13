import { Link } from "react-router-dom";
function CaretakerOverview() {

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

  return (
    <main className="caretaker-overview">
      <section className="overview-welcome">
        <h1>Good evening 👋</h1>
        <p>You are currently caring for 6 patients.</p>
      </section>

      <section className="overview-stats">
        <div className="stat-card">
          <span className="stat-icon">👤</span>
          <strong>6</strong>
          <p>Patients</p>
        </div>

        <div className="stat-card">
          <span className="stat-icon">💊</span>
          <strong>2</strong>
          <p>Due Soon</p>
        </div>

        <div className="stat-card">
          <span className="stat-icon">🔔</span>
          <strong>4</strong>
          <p>Attention</p>
        </div>
      </section>

      <section className="overview-patients">
        <div className="section-heading">
          <h2>Patients</h2>

          <Link to="/caretaker/patients">
            View all →
          </Link>
        </div>

        <div className="patient-list">
          {patients.map((patient) => (
            <Link
              to={`/caretaker/patient/${patient.id}`}
              className="patient-row"
              key={patient.id}
            >              <div className="patient-info">
                <h3>👤 {patient.name}</h3>
                <p>{patient.next}</p>
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
            </Link>
          ))}
        </div>
      </section>
    </main>
  )
};
export default CaretakerOverview;