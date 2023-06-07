import { Link, useNavigate, useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import { addDoctor } from "../services/doctors";

interface Doctor {
  firstName: string;
  middleName: string;
  lastName: string;
  location: string;
  phone: number;
  prescriptionId: string;
  doctorId: number;
}

export const DoctorForm = () => {
  const [doctor, setDoctor] = useState<Doctor>({
    firstName: "",
    middleName: "",
    lastName: "",
    location: "",
    phone: 0,
    prescriptionId: "",
    doctorId: 0,
  });
  let { prescriptionId } = useParams();
  const navigate = useNavigate();

  function handleChange({
    target: { name, value },
  }: {
    target: { name: string; value: string };
  }) {
    setDoctor({ ...doctor, [name]: value });
  }

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    addDoctor(doctor);
    navigate("/prescriptions");
  }
  useEffect(() => {
    // eslint-disable-next-line
    prescriptionId = prescriptionId as string;
    setDoctor({ ...doctor, prescriptionId });
  }, []);

  return (
    <section className="d-flex flex-column align-items-center">
      <h1>Add a Doctor</h1>
      <form
        className="d-flex flex-column align-items-center"
        onSubmit={handleSubmit}
      >
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
            Address
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
            Phone Number
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
        <div className="mt-2">
          <button className="btn btn-primary mx-1">Submit</button>
          <Link to="/prescriptions" className="btn btn-dark mx-1">
            Cancel
          </Link>
        </div>
      </form>
    </section>
  );
};
