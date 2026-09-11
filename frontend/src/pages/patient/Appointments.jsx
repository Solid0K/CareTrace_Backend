import { useState } from "react";
import { DayPicker } from "react-day-picker";
import "react-day-picker/style.css";
import { Link } from "react-router-dom";
function Appointments() {
  const [selectedDate, setSelectedDate] = useState(new Date());
  const appointments = [
    {
      id: 1,
      date: new Date(2026, 8, 18),
      time: "10:30 AM",
      title: "Doctor appointment",
      location: "City Hospital",
    },
    {
      id: 2,
      date: new Date(2026, 8, 11),
      time: "3:00 PM",
      title: "Family visit",
      location: "At home",
    },
    {
      id: 3,
      date: new Date(2026, 8, 15),
      time: "11:00 AM",
      title: "Dentist appointment",
      location: "Dental Clinic",
    },
  ];
  const appointmentDates = appointments.map(
    (appointment) => appointment.date
  );
  const selectedAppointments = appointments.filter(
    (appointment) =>
      appointment.date.toDateString() === selectedDate.toDateString()
  );
  return (
    <main className="appointments-page">
      <Link to="/" className="back-home">
        ← Back to Home
      </Link>

      <h1>📅 Appointments</h1>
      <p>Your upcoming appointments</p>

      <section className="appointments-content">
        <div className="calendar-card">
          <DayPicker
            mode="single"
            selected={selectedDate}
            onSelect={setSelectedDate}
            modifiers={{
              hasAppointment: appointmentDates,
            }}
            modifiersStyles={{
              hasAppointment: {
                fontWeight: "700",
                color: "#245c5a",
                textDecoration: "underline",
                textDecorationThickness: "3px",
                textUnderlineOffset: "5px",
              },
            }}
          /> 
        </div>

        <div className="appointments-list">
          <h2>
            Appointments for{" "}
            {selectedDate.toLocaleDateString("en-US", {
              month: "long",
              day: "numeric",
              year: "numeric",
            })}
          </h2>
          {selectedAppointments.length > 0 ? (
            selectedAppointments.map((appointment) => (
              <div className="appointment-item" key={appointment.id}>
                <strong>{appointment.time}</strong>

                <div>
                  <h3>{appointment.title}</h3>
                  <p>{appointment.location}</p>
                </div>
              </div>
            ))
          ) : (
            <p>No appointments for this day.</p>
          )}
        </div>
      </section>
    </main>
  );
}

export default Appointments;