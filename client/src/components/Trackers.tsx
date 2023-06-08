import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { addTracker, getTrackers } from "../services/trackers";

interface TrackerType {
  prescriptionId: number;
  administrationTime: string;
  trackerId: number;
}

export const Tracker = () => {
  const { prescriptionId } = useParams();

  const [trackers, setTrackers] = useState<TrackerType[]>([]);

  function handleSubmit() {
    const currentDate = new Date();
    const isoString = currentDate.toISOString().slice(0, -8).replace("T", " ");

    const finalObject = {
      prescriptionId: parseInt(prescriptionId as string, 10),
      administrationTime: isoString,
      trackerId: 0,
    };

    console.log(finalObject.administrationTime);
    addTracker(finalObject)
      .then(() => {
        fetchTrackers(); // Fetch and update the trackers after adding a new one
      })
      .catch((error) => {
        console.error("Error adding tracker:", error);
      });
  }

  async function fetchTrackers() {
    const track = await getTrackers(parseInt(prescriptionId as string, 10));
    const sortedTrackers = track.sort((a: any, b: any) => {
      const dateA = new Date(a.administrationTime).getTime();
      const dateB = new Date(b.administrationTime).getTime();
      return dateB - dateA;
    });
    setTrackers(sortedTrackers);
  }

  useEffect(() => {
    fetchTrackers();

    //eslint-disable-next-line
  }, [prescriptionId]);

  return (
    <section className="d-flex flex-column align-items-center w-100 justify-content-start">
      <h1>Time Taken</h1>
      <div className="mb-3">
        <Link to="/prescriptions" className="btn btn-danger mx-1">
          Go Back
        </Link>
        <button className="btn btn-dark mx-1" onClick={handleSubmit}>
          Took It
        </button>
      </div>
      {trackers.length > 0 ? (
        // eslint-disable-next-line
        trackers?.sort().map((tracker, index) => {
          return (
            <p
              className={
                index % 2 === 0 ? "even text-center" : "odd text-center"
              }
              key={index}
            >
              {tracker.administrationTime}
            </p>
          );
        })
      ) : (
        <h1 className="text-center">
          You can track your medication by pressing the button.
        </h1>
      )}
    </section>
  );
};
