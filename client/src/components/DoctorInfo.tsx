import { AuthContext } from "../contexts/AuthContext";
import { useContext, useEffect, useState } from "react";
import { getDoctors } from "../services/doctors";
import { Link, useParams } from "react-router-dom";

interface Doctor {
  firstName: string;
  middleName: string;
  lastName: string;
  location: string;
  phone: number;
  prescriptionId: string;
  doctorId: string;
}

export const DoctorInfo = () => {
  const [doctor, setDoctor] = useState([]);
  const { prescriptionId } = useParams();
  const context = useContext(AuthContext);
  useEffect(() => {
    async function fetchDoc() {
      const doc = await getDoctors(context!.user!.app_user_id);
      setDoctor(doc);
    }

    fetchDoc();
    // eslint-disable-next-line
  }, []);
  return (
    <>
      {doctor.length > 0 ? (
        <section>
          {doctor.map((doc: Doctor) => (
            <div
              className="d-flex flex-column align-items-center doctor-card"
              key={doc.doctorId}
            >
              <h2>
                {doc.firstName} {doc.middleName}. {doc.lastName}
              </h2>
              <p>
                <strong>Location:&nbsp;</strong>
                {doc.location}
              </p>
              <p>
                <strong>Phone:&nbsp;</strong>
                {doc.phone}
              </p>
              <div>
                <Link
                  to={`/doctors/edit/${doc.doctorId}`}
                  className="btn btn-dark mx-1"
                >
                  Edit
                </Link>
                <Link
                  to={`/doctors/delete/${doc.doctorId}`}
                  className="btn btn-danger mx-1"
                >
                  Delete
                </Link>
              </div>
            </div>
          ))}
        </section>
      ) : (
        <h1 className="text-center">
          There are no doctors registered to this medication.
        </h1>
      )}
      <Link to={`/doctors/add/${prescriptionId}`} className="btn btn-dark mt-3">
        Add a Doctor
      </Link>
      <Link to="/prescriptions" className="btn btn-dark mt-3">
        Go Back
      </Link>
    </>
  );
};
