import { useParams, Link } from "react-router-dom";
import {
  getUserPrescriptions,
  updatePrescription,
} from "../services/prescriptions";
import { useEffect, useState, useContext } from "react";
import { AuthContext } from "../contexts/AuthContext";

interface Prescription {
  prescriptionId: number;
  productNDC: string;
  hourlyInterval: number;
  pillCount: number;
  startTime: string;
  app_user_id: number;
  num: number;
  medication: string;
  product_ndc: string;
}

export const EditPrescriptionForm: React.FC = () => {
  const [prescription, setPrescription] = useState<Prescription>({
    prescriptionId: 0,
    productNDC: "",
    hourlyInterval: 0,
    pillCount: 0,
    startTime: "",
    app_user_id: 0,
    num: 0,
    medication: "",
    product_ndc: "",
  });
  const { prescriptionId } = useParams();
  const context = useContext(AuthContext);

  function handleChange({
    target: { name, value },
  }: {
    target: { name: string; value: string };
  }) {
    setPrescription({ ...prescription, [name]: value });
  }

  async function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    updatePrescription({
      ...prescription,
      app_user_id: context!.user!.app_user_id,
    });
  }

  useEffect(() => {
    async function getPrescription() {
      const userPrescriptions = await getUserPrescriptions(
        context!.user!.app_user_id
      );
      const desiredPrescription = userPrescriptions.find(
        (prescription: Prescription) =>
          prescription.prescriptionId === parseInt(prescriptionId as string, 10)
      );

      setPrescription(desiredPrescription || prescription);
    }
    getPrescription();
    //eslint-disable-next-line
  }, []);

  return (
    <section>
      <form onSubmit={handleSubmit}>
        <div>
          <label htmlFor="product" className="form-text">
            Product NDC
          </label>
          <input
            type="text"
            name="product"
            id="product"
            value={prescription?.productNDC || ""}
            className="form-control"
            disabled
          />
        </div>
        <div>
          <label htmlFor="hourlyInterval" className="form-text">
            Hourly Interval
          </label>
          <input
            type="number"
            name="hourlyInterval"
            id="hourlyInterval"
            value={prescription?.hourlyInterval || ""}
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="pillCount" className="form-text">
            Pill Count
          </label>
          <input
            type="number"
            name="pillCount"
            id="pillCount"
            value={prescription?.pillCount || ""}
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <div className="d-flex flex-column">
          <label htmlFor="startTime" className="form-text">
            Start Time
          </label>
          <input
            type="datetime-local"
            name="startTime"
            id="startTime"
            className="form-control"
            onChange={handleChange}
            value={prescription?.startTime || ""}
          />
        </div>
        <div className="mt-2 text-center">
          <button className="btn btn-primary mx-1">Submit</button>
          <Link to="/prescriptions" className="btn btn-dark mx-1">
            Cancel
          </Link>
        </div>
      </form>
    </section>
  );
};
