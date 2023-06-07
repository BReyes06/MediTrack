import { useNavigate, useParams, Link } from "react-router-dom";
import { useState, useEffect } from "react";
import { getPharmacy, updatePharmacy } from "../services/phamarcies";

interface Pharmacy {
  name: string;
  email: string;
  phone: string;
  address: string;
  prescriptionId: number;
  pharmacyId: number;
}

export const PharmacyEditForm = () => {
  const { pharmacyId } = useParams();
  const navigate = useNavigate();

  const [pharmacy, setPharmacy] = useState<Pharmacy>({
    name: "",
    email: "",
    phone: "",
    address: "",
    prescriptionId: 0,
    pharmacyId: parseInt(pharmacyId as string, 10),
  });

  function handleChange({
    target: { name, value },
  }: {
    target: { name: string; value: string };
  }) {
    setPharmacy({ ...pharmacy, [name]: value });
  }

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    updatePharmacy(pharmacy);
    navigate("/prescriptions");
  }

  useEffect(() => {
    async function fetchPharmacy() {
      setPharmacy(await getPharmacy(pharmacyId as string));
    }
    fetchPharmacy();
    // eslint-disable-next-line
  }, []);
  return (
    <section>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="name" className="form-text">
            Name
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
            Email
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
            Phone
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
            Address
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
          <button className="btn btn-dark mx-1">Submit</button>
          <Link to="/prescriptions" className="btn btn-danger mx-1">
            Cancel
          </Link>
        </div>
      </form>
    </section>
  );
};
