import { useParams, Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getDoctor, deleteDoctor } from "../services/doctors";

interface Doctor {
  firstName: string;
  middleName: string;
  lastName: string;
  location: string;
  phone: number;
  prescriptionId: string;
  doctorId: number;
}

export const DeleteDoctor = () => {
  const [doctor, setDoctor] = useState<Doctor>();
  const { doctorId } = useParams();
  const navigate = useNavigate();

  function handleSubmit(event: React.MouseEvent<HTMLButtonElement>) {
    event.preventDefault();
    deleteDoctor(parseInt(doctorId as string, 10));
    navigate("/prescriptions");
  }
  useEffect(() => {
    async function fetchDoc() {
      setDoctor(await getDoctor(doctorId as string));
    }
    fetchDoc();
    // eslint-disable-next-line
  }, []);
  return (
    <section
      className="d-flex flex-column align-items-center alert alert-danger"
      role="alert"
    >
      <h2>Are you sure you want to delete: </h2>
      <p>
        <strong>Full Name:&nbsp;</strong>
        {doctor?.firstName} {doctor?.middleName}. {doctor?.lastName}
      </p>
      <p>
        <strong>Location:&nbsp;</strong>
        {doctor?.location}
      </p>
      <p>
        <strong>Phone:&nbsp;</strong>
        {doctor?.phone}
      </p>
      <div>
        <button className="btn btn-danger mx-1" onClick={handleSubmit}>
          Delete
        </button>
        <Link to="/prescriptions" className="btn btn-dark mx-1">
          Cancel
        </Link>
      </div>
    </section>
  );
};
