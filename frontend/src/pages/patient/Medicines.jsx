import { useState } from "react";
import { Link } from "react-router-dom";
function Medicines() {
  const [medicines, setMedicines] = useState([
    {
      id: 1,
      name: "Blood pressure medicine",
      time: "10:00 AM",
      dosage: "1 tablet",
      taken: false,
    },
    {
      id: 2,
      name: "Vitamin D",
      time: "2:00 PM",
      dosage: "1 tablet",
      taken: false,
    },
    {
      id: 3,
      name: "Evening medicine",
      time: "8:00 PM",
      dosage: "1 tablet",
      taken: false,
    },
  ]);

  function markAsTaken(id) {
    setMedicines(
      medicines.map((medicine) =>
        medicine.id === id
          ? { ...medicine, taken: true }
          : medicine
      )
    );
  }

  return (
    <main className="medicines-page">
      <Link to="/" className="back-home">
        ← Back to Home
      </Link>
      <h1>💊 Medicines</h1>
      <p>Today's medicine schedule</p>

      <section className="medicine-list">
        {medicines.map((medicine) => (
          <div className="medicine-item" key={medicine.id}>
            <div>
              <h2>{medicine.name}</h2>
              <p>{medicine.dosage}</p>
              <strong>{medicine.time}</strong>
            </div>

            {medicine.taken ? (
              <span className="medicine-taken">✓ Taken</span>
            ) : (
              <button
                onClick={() => markAsTaken(medicine.id)}
                className="take-medicine-button"
              >
                Mark as taken
              </button>
            )}
          </div>
        ))}
      </section>
    </main>
  );
}

export default Medicines;