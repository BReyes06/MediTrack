import { useEffect, useState, useContext } from "react";
import { Link } from "react-router-dom";
import { getUserPrescriptions } from "../services/prescriptions";
import { AuthContext } from "../contexts/AuthContext";

import PrescriptionCard from "./PrescriptionCard";

interface Prescription {
  productNDC: string;
  prescriptionId: number;
  hourlyInterval: number;
  pillCount: number;
  startTime: string;
  results: [
    {
      product_ndc: string;
      generic_name: string;
      dosage_form: string;
    }
  ];
}

export const Prescriptions = () => {
  const [prescriptions, setPrescriptions] = useState<Prescription[]>([]);

  const context = useContext(AuthContext);

  useEffect(() => {
    const fetchPrescriptions = async () => {
      try {
        const prescriptionList = await getUserPrescriptions(
          context!.user!.app_user_id
        );

        const mappedPrescriptions = await Promise.all(
          prescriptionList.map(async (prescription: Prescription) => {
            const response = await fetch(
              `https://api.fda.gov/drug/ndc.json?search=product_ndc:"${prescription.productNDC}"`
            );
            const json = await response.json();
            const finalJson = {
              ...json,
              prescriptionId: prescription.prescriptionId,
              hourlyInterval: prescription.hourlyInterval,
              startTime: prescription.startTime,
              pillCount: prescription.pillCount,
              productNDC: prescription.productNDC,
            };
            return finalJson || [];
          })
        );
        setPrescriptions(mappedPrescriptions);
      } catch (error) {
        console.error("Error fetching prescriptions:", error);
      }
    };

    fetchPrescriptions(); // eslint-disable-next-line
  }, [context]);

  return (
    <section className="prescriptions d-flex flex-column align-items-center">
      {prescriptions.length <= 0 ? (
        <h1 className="text-center">You have no registered prescriptions.</h1>
      ) : (
        <>
          {prescriptions.map((prescription) => {
            return (
              <PrescriptionCard
                key={prescription?.results[0]?.product_ndc}
                prescription={prescription}
              />
            );
          })}
        </>
      )}
      <Link to="/prescriptions/add" className="btn btn-dark my-3">
        Add A Prescription
      </Link>
    </section>
  );
};
