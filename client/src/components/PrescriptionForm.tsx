import { Link } from "react-router-dom";
import { useState, useContext } from "react";
import { addPrescription, getMeds } from "../services/prescriptions";
import { AuthContext } from "../contexts/AuthContext";

type MedForm = {
  medication: string;
  hourlyInterval: number;
  pillCount: number;
  startTime: string;
  num: number;
  product_ndc: string;
};

const INITIAL_MED: MedForm = {
  medication: "",
  hourlyInterval: 1,
  pillCount: 1,
  startTime: "",
  num: 1,
  product_ndc: "",
};

interface SearchResult {
  product_ndc: string;
  generic_name: string;
}

export const PrescriptionForm: React.FC = () => {
  const [medication, setMedication] = useState(INITIAL_MED);
  const [searchResults, setSearchResults] = useState<SearchResult[]>([]);
  const [validatedMedication, setValidatedMedication] = useState(false);
  const context = useContext(AuthContext);

  function handleChange(event: React.ChangeEvent<HTMLInputElement>) {
    const { name, value, type } = event.target;

    if (type === "radio") {
      setMedication((prevMedication) => ({
        ...prevMedication,
        [name]: parseInt(value),
      }));
    } else {
      setMedication((prevMedication) => ({
        ...prevMedication,
        [name]: value,
      }));
    }
  }

  const resetForm = () => {
    setMedication(INITIAL_MED);
    setSearchResults([]);
    setValidatedMedication(false);
  };

  const handleMedSearch = async () => {
    const search = await getMeds(medication.medication);
    setSearchResults(search.results);
  };

  const handleSelectMed = (event: React.MouseEvent<HTMLLIElement>) => {
    const selectedMed = event.currentTarget.innerText;
    const filteredSearchArray = searchResults.filter((med) => {
      return med.generic_name === selectedMed;
    });
    setValidatedMedication(true);
    setMedication({
      ...medication,
      medication: selectedMed,
      product_ndc: filteredSearchArray[0]?.product_ndc,
    });
    setSearchResults([]);
    const medicationInput = document.getElementById(
      "medication"
    ) as HTMLInputElement;
    if (medicationInput) {
      medicationInput.value = selectedMed;
    }
  };

  const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (validatedMedication) {
      console.log(context?.user);
      addPrescription(medication, context?.user!);
      resetForm();
    }
  };

  return (
    <section className="prescription-form">
      <form onSubmit={handleSubmit}>
        <div className="d-flex flex-column">
          <label htmlFor="medication" className="form-text">
            Medication Name:
          </label>
          <div className="d-flex">
            <input
              type="text"
              name="medication"
              id="medication"
              value={medication.medication}
              className="form-control med-input"
              onChange={handleChange}
            />
            <div
              className="med-btn btn btn-dark"
              onClick={() => {
                handleMedSearch();
              }}
            >
              🔎
            </div>
          </div>
          {!validatedMedication && searchResults.length === 0 ? (
            <div className="alert alert-danger mt-2" role="alert">
              Please verify your medication by clicking the magnifying glass and
              selecting it.
            </div>
          ) : (
            <></>
          )}
          <div>
            {searchResults?.length > 0 ? (
              <>
                <h4>Select Medication</h4>
                <ul className="mt-2">
                  {searchResults.map((result) => (
                    <li
                      key={result.product_ndc}
                      onClick={handleSelectMed}
                      className="select-med"
                    >
                      {result.generic_name}
                    </li>
                  ))}
                </ul>
              </>
            ) : (
              <></>
            )}
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
              value={medication.hourlyInterval}
              onChange={handleChange}
            />
            <div className="d-flex justify-content-center">
              <input
                type="radio"
                name="num"
                id="hours"
                value={1}
                onChange={handleChange}
                defaultChecked
              />
              <label htmlFor="hours" className="mx-2 form-text">
                Hours
              </label>
              <input
                type="radio"
                name="num"
                id="days"
                value={24}
                onChange={handleChange}
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
              value={medication.pillCount}
              onChange={handleChange}
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
              onChange={handleChange}
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
