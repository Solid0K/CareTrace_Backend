import { Link } from "react-router-dom";

function Activities() {
  return (
    <main className="activities-page">
      <Link to="/" className="back-home">
        ← Back to Home
      </Link>

      <h1>🎮 Activities</h1>
      <p>Take a moment to enjoy a familiar activity.</p>

      <section className="activity-list">
        <div className="activity-card">
          <div className="activity-icon">📍</div>

          <div className="activity-info">
            <h2>Where Am I?</h2>
            <p>
              Look at a familiar place and see if you recognize it.
            </p>
          </div>

          <button className="activity-button">
            Play
          </button>
        </div>

        <div className="activity-card">
          <div className="activity-icon">👤</div>

          <div className="activity-info">
            <h2>Who Is This?</h2>
            <p>
              Look at a familiar person and see if you know them.
            </p>
          </div>

          <button className="activity-button">
            Play
          </button>
        </div>
      </section>
    </main>
  );
}

export default Activities;