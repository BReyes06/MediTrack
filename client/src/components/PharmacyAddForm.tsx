import { useParams, Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { addPharmacy } from "../services/phamarcies";

interface Pharmacy {
  name: string;
  email: string;
  phone: string;
  address: string;
  prescriptionId: number;
  pharmacyId: number;
}

export const PharmacyAddForm = () => {
  const { prescriptionId } = useParams();

  const [pharmacy, setPharmacy] = useState<Pharmacy>({
    name: "",
    email: "",
    phone: "",
    address: "",
    prescriptionId: parseInt(prescriptionId as string, 10),
    pharmacyId: 0,
  });
  const navigate = useNavigate();

  function handleChange({
    target: { name, value },
  }: {
    target: { name: string; value: string };
  }) {
    setPharmacy({ ...pharmacy, [name]: value });
  }

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    addPharmacy(pharmacy);
    navigate("/prescriptions");
  }
  return (
    <section className="d-flex flex-column align-items-center">
      <form onSubmit={handleSubmit}>
        <h2>Add a Pharmacy</h2>
        <div>
          <label htmlFor="name" className="form-text">
            Name:
          </label>
          <input
            type="text"
            name="name"
            id="name"
            value={pharmacy.name}
            onChange={handleChange}
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="email" className="form-text">
            Email:
          </label>
          <input
            type="email"
            name="email"
            id="email"
            value={pharmacy.email}
            onChange={handleChange}
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="phone" className="form-text">
            Phone:
          </label>
          <input
            type="tel"
            name="phone"
            id="phone"
            value={pharmacy.phone}
            onChange={handleChange}
            className="form-control"
          />
        </div>
        <div>
          <label htmlFor="address" className="form-text">
            Address:
          </label>
          <input
            type="text"
            name="address"
            id="address"
            value={pharmacy.address}
            onChange={handleChange}
            className="form-control"
          />
        </div>
        <div className="mt-2 d-flex justify-content-center">
          <button className="btn btn-primary mx-1">Submit</button>
          <Link to="/prescriptions" className="btn btn-dark mx-1">
            Cancel
          </Link>
        </div>
      </form>
    </section>
  );
};
