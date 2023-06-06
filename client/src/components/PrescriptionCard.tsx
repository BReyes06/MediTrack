import { Link } from "react-router-dom";

type PrescriptionProps = {
  prescription: Prescription;
};

interface Prescription {
  productNDC: string;
  prescriptionId: number;
  hourlyInterval: number;
  pillCount: number;
  startTime: string;
  results: {
    product_ndc: string;
    generic_name: string;
    dosage_form: string;
  }[];
}

const PrescriptionCard: React.FC<PrescriptionProps> = ({ prescription }) => {
  const { results } = prescription;
  const firstResult = results[0];

  return (
    <div className="d-flex flex-column align-items-center prescription-card mt-2">
      <h2>{firstResult?.generic_name}</h2>
      <small>
        <strong>Product NDC</strong>
      </small>
      <small>{firstResult?.product_ndc}</small>
      <small>
        <strong>Dosage Form</strong>
      </small>
      <small>{firstResult?.dosage_form}</small>
      <div className="d-flex">
        <small>
          <Link to="/doctor" className="mx-2 card-links">
            Doctor
          </Link>
        </small>
        <small>
          <Link to="/pharmacy" className="mx-2 card-links">
            Pharmacy
          </Link>
        </small>
      </div>
      <Link to="/tracker" className="btn btn-dark w-75 mt-1">
        Track
      </Link>
      <div className="d-flex mt-2">
        <Link
          to={`/edit/${prescription.prescriptionId}`}
          className="btn btn-dark mx-1"
        >
          Edit
        </Link>
        <Link
          to={`/delete/${prescription.prescriptionId}`}
          className="btn btn-danger mx-1"
        >
          Delete
        </Link>
      </div>
    </div>
  );
};

export default PrescriptionCard;
