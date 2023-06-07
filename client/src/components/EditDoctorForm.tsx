import { useParams, Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { getDoctor, updateDoctor } from "../services/doctors";

interface Doctor {
  firstName: string;
  middleName: string;
  lastName: string;
  location: string;
  phone: number;
  prescriptionId: string;
  doctorId: number;
}

export const EditDoctorForm = () => {
  const { doctorId } = useParams();
  const navigate = useNavigate();

  const [doctor, setDoctor] = useState<Doctor>({
    firstName: "",
    middleName: "",
    lastName: "",
    location: "",
    phone: 0,
    prescriptionId: "",
    doctorId: parseInt(doctorId as string, 10),
  });

  function handleChange({
    target: { name, value },
  }: {
    target: { name: string; value: string };
  }) {
    setDoctor({ ...doctor, [name]: value });
  }

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    updateDoctor(doctor);
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
    <section>
      <form onSubmit={handleSubmit}>
        <h2>Edit a Doctor</h2>
        <div>
          <label htmlFor="firstName" className="form-text">
            First Name
          </label>
          <input
            type="text"
            name="firstName"
            id="firstName"
            value={doctor.firstName}
            onChange={handleChange}
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="middleName" className="form-text">
            Middle Name
          </label>
          <input
            type="text"
            name="middleName"
            id="middleName"
            value={doctor.middleName}
            onChange={handleChange}
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="lastName" className="form-text">
            Last Name
          </label>
          <input
            type="text"
            name="lastName"
            id="lastName"
            value={doctor.lastName}
            onChange={handleChange}
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="location" className="form-text">
            Location
          </label>
          <input
            type="text"
            name="location"
            id="location"
            value={doctor.location}
            onChange={handleChange}
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="phone" className="form-text">
            Phone
          </label>
          <input
            type="tel"
            name="phone"
            id="phone"
            value={doctor.phone}
            onChange={handleChange}
            className="form-control"
          />
        </div>
        <div className="mt-2 d-flex justify-content-center">
          <button className="btn btn-dark mx-1">Submit</button>
          <Link to="/prescriptions" className="btn btn-danger mx-1">
            Cancel
          </Link>
        </div>
      </form>
    </section>
  );
};
