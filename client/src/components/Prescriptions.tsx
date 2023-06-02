import { useState } from "react";
import { Link } from "react-router-dom";

export const Prescriptions = () => {
  const [prescriptions, setPrescriptions] = useState([]);
  return (
    <section className="prescriptions">
      {prescriptions.length <= 0 ? (
        <h1>You have no registered prescriptions.</h1>
      ) : (
        <h1>Prescription card.</h1>
      )}
      <Link to="/prescriptions/add" className="btn btn-dark">
        Add A Prescription
      </Link>
    </section>
  );
};
