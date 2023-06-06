import { useParams, Link, useNavigate } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import {
  deletePrescription,
  getUserPrescriptions,
} from "../services/prescriptions";
import { AuthContext } from "../contexts/AuthContext";

interface Prescription {
  prescriptionId: number;
  productNDC: string;
  hourlyInterval: number;
  pillCount: number;
  startTime: string;
  app_user_id: number;
}

export const DeletePrescription = () => {
  const [prescriptionInfo, setPrescriptionInfo] = useState<Prescription>({
    prescriptionId: 0,
    productNDC: "",
    hourlyInterval: 0,
    pillCount: 0,
    startTime: "",
    app_user_id: 0,
  });

  const { prescriptionId } = useParams();
  const context = useContext(AuthContext);
  const navigate = useNavigate();

  async function handleDelete() {
    deletePrescription(parseInt(prescriptionId as string, 10));
    navigate("/prescriptions");
  }
  useEffect(() => {
    async function getPrescription() {
      const userPrescriptions = await getUserPrescriptions(
        context!.user!.app_user_id
      );
      const filteredPrescription = userPrescriptions.filter(
        (prescription: Prescription) =>
          prescription.prescriptionId === parseInt(prescriptionId as string, 10)
      );
      setPrescriptionInfo({
        ...filteredPrescription[0],
        app_user_id: context!.user!.app_user_id,
      });
    }

    getPrescription();
    // eslint-disable-next-line
  }, []);

  return (
    <section className="alert alert-danger" role="alert">
      <h2>Are you sure you want to delete prescription ID: {prescriptionId}</h2>
      <p>
        <strong>Product NDC:&nbsp;</strong>
        {prescriptionInfo?.productNDC}
      </p>
      <p>
        <strong>Hourly Interval:&nbsp;</strong>
        {prescriptionInfo?.hourlyInterval}
      </p>
      <p>
        <strong>Start Time:&nbsp;</strong>
        {prescriptionInfo?.startTime}
      </p>
      <p>
        <strong>Pill Count:&nbsp;</strong>
        {prescriptionInfo?.pillCount}
      </p>
      <div>
        <button className="btn btn-danger" onClick={handleDelete}>
          Delete
        </button>
        <Link to="/prescriptions" className="btn btn-dark">
          Cancel
        </Link>
      </div>
    </section>
  );
};
