function HelpCard() {
  return (
    <section className="assistant-panel">
      <h2>How can I help?</h2>

      <button className="help-button">
        🎙
        <span>Tap to talk</span>
      </button>

      <p>I'm here to help you.</p>
      <p style={{ fontWeight: "bold" }}>Click the button below if you need immediate assistance.</p>

      <button className="emergency-button">
        🆘 I NEED HELP
      </button>
    </section>
  );
}

export default HelpCard;