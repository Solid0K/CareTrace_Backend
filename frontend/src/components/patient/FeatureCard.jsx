import { Link } from "react-router-dom";

function FeatureCard({ icon, title, description, path, className = "" }) {
  return (
    <Link
      to={path}
      className={`feature-card ${className}`}
    >
      <h2>
        {icon} {title}
      </h2>

      <p>{description}</p>
    </Link>
  );
}

export default FeatureCard;