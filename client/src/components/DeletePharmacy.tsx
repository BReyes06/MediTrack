import { useState, useEffect } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { getPharmacy, deletePharmacy } from "../services/phamarcies";

interface Pharmacy {
  name: string;
  email: string;
  phone: string;
  address: string;
  prescriptionId: number;
  pharmacyId: number;
}

export const DeletePharmacy = () => {
  const [pharmacy, setPharmacy] = useState<Pharmacy>();
  const { pharmacyId } = useParams();
  const navigate = useNavigate();

  function handleSubmit(event: React.MouseEvent<HTMLButtonElement>) {
    event.preventDefault();
    deletePharmacy(parseInt(pharmacyId as string, 10));
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
    <section
      className="d-flex flex-column align-items-center alert alert-danger"
      role="alert"
    >
      <h2>Are you sure you want to delete: </h2>
      <p>
        <strong>Name:&nbsp;</strong>
        {pharmacy?.name}
      </p>
      <p>
        <strong>Email:&nbsp;</strong>
        {pharmacy?.email}
      </p>
      <p>
        <strong>Address:&nbsp;</strong>
        {pharmacy?.address}
      </p>
      <p>
        <strong>Phone:&nbsp;</strong>
        {pharmacy?.phone}
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
