import { Link } from "react-router-dom";
import { useState } from "react";
import { getMeds } from "../services/prescriptions";
type MedForm = {
  medication: string;
  hourlyInterval: number;
  pillCount: number;
  startTime: Date;
};

const INITIAL_MED: MedForm = {
  medication: "",
  hourlyInterval: 1,
  pillCount: 1,
  startTime: new Date("2020-01-01T08:00"), // Look at this!!!
};
export const PrescriptionForm = () => {
  const [medication, setMedication] = useState(INITIAL_MED);
  function handleChange({
    target: { name, value },
  }: {
    target: { name: string; value: string };
  }) {
    setMedication({ ...medication, [name]: value });
  }

  function handleMedSearch() {
    // getMeds();
  }
  return (
    <section className="prescription-form">
      <form>
        <div className="d-flex flex-column">
          <label htmlFor="medication" className="form-text">
            Medication Name:
          </label>
          <div className="d-flex">
            <input
              type="text"
              name="medication"
              id="medication"
              className="form-control med-input"
            />
            <div className="med-btn btn btn-dark">🔎</div>
          </div>
          <div className="d-flex flex-column">
            <label htmlFor="timeIntervals" className="form-text">
              Time Interval
            </label>
            <input
              type="number"
              name="hourlyInterval"
              id="hourlyInterval"
              min="1"
              className="form-control"
            />
            <div className="d-flex justify-content-center">
              <input type="radio" name="num" id="hours" value={1} checked />
              <label htmlFor="hours" className="mx-2 form-text">
                Hours
              </label>
              <input
                type="radio"
                name="num"
                id="days"
                value={24}
                className="mx-2"
              />
              <label htmlFor="hours" className="form-text">
                Days
              </label>
            </div>
          </div>
          <div>
            <label htmlFor="pillCount" className="form-text">
              Pill Count
            </label>
            <input
              type="number"
              name="pillCount"
              id="pillCount"
              className="form-control"
            />
          </div>
          <div>
            <label htmlFor="startTime" className="form-text">
              Start Time
            </label>
            <input
              type="datetime-local"
              name="startTime"
              id="startTime"
              className="form-control"
            />
          </div>
          <div className="mt-3 d-flex justify-content-center">
            <button className="btn btn-primary mx-2">Add</button>
            <Link to="/prescriptions" className="btn btn-dark mx-2">
              Cancel
            </Link>
          </div>
        </div>
      </form>
    </section>
  );
};
