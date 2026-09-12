import PatientHeader from "../../components/patient/PatientHeader";
import HelpCard from "../../components/patient/HelpCard";
import FeatureCard from "../../components/patient/FeatureCard";
function PatientHome() {
  return (
    <div className="patient-home">
      <PatientHeader />

      <main className="patient-dashboard">
        <HelpCard />

        <section className="feature-grid">
          <FeatureCard
            icon="💊"
            title="Medicines"
            description="Next medicine at 10:00 AM"
            className="medicine-card"
            path="/medicines"
          />

          <FeatureCard
            icon="📅"
            title="Appointments"
            description="2 appointments today"
            path="/appointments"
          />

          <FeatureCard
            icon="✅"
            title="To-Dos"
            description="3 things to do today"
            path="/todos"
          />

          <FeatureCard
            icon="🎮"
            title="Activities"
            description="Play a familiar game"
            path="/activities"
          />
        </section>
      </main>
    </div>
  );
}

export default PatientHome;