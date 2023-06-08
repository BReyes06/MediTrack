import { Link, useParams } from "react-router-dom";
import { useState, useEffect, useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";
import { getPharmaciesByUser } from "../services/phamarcies";

interface Pharmacy {
  name: string;
  email: string;
  phone: string;
  address: string;
  prescriptionId: number;
  pharmacyId: number;
}

export const PharmacyInfo = () => {
  const [pharmacies, setPharmacies] = useState<Pharmacy[]>([]);
  const { prescriptionId } = useParams();
  const context = useContext(AuthContext);

  useEffect(() => {
    async function fetchPharmacies() {
      const pharm = await getPharmaciesByUser(context!.user!.app_user_id);
      setPharmacies(pharm);
    }

    fetchPharmacies();
    // eslint-disable-next-line
  }, []);

  return (
    <>
      {pharmacies.length > 0 ? (
        <section className="d-flex flex-column align-items-center doctor-card">
          {pharmacies.map((pharmacy) => (
            <div key={pharmacy.pharmacyId}>
              <h2 className="text-center">{pharmacy.name}</h2>
              <p>
                <strong>Email:&nbsp;</strong>
                {pharmacy.email}
              </p>
              <p>
                <strong>Location:&nbsp;</strong>
                {pharmacy.address}
              </p>
              <p>
                <strong>Phone:&nbsp;</strong>
                {pharmacy.phone}
              </p>
              <div className="text-center">
                <Link
                  to={`/pharmacies/edit/${pharmacy.pharmacyId}`}
                  className="btn btn-dark mx-1"
                >
                  Edit
                </Link>
                <Link
                  to={`/pharmacies/delete/${pharmacy.pharmacyId}`}
                  className="btn btn-danger mx-1"
                >
                  Delete
                </Link>
              </div>
            </div>
          ))}
        </section>
      ) : (
        <>
          <h1 className="text-center">You have no registered pharmacies.</h1>
        </>
      )}
      <Link
        to={`/pharmacies/add/${prescriptionId}`}
        className="btn btn-dark mt-3"
      >
        Add a Pharmacy
      </Link>
      <Link to={`/prescriptions`} className="btn btn-dark mt-3">
        Go Back
      </Link>
    </>
  );
};
