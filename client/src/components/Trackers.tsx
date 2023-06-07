import { Link } from "react-router-dom";

const times = ["1 PM", "2 PM", "3 PM", "4 PM", "5 PM"];

export const Tracker = () => {
  return (
    <section className="d-flex flex-column align-items-center w-100 justify-content-start">
      <h1>Time Taken</h1>
      {times.map((time, index) => {
        return (
          <p
            className={index % 2 === 0 ? "even text-center" : "odd text-center"}
            key={index}
          >
            {time}
          </p>
        );
      })}
      <div>
        <button className="btn btn-dark mx-1">TOOK IT</button>
        <Link to="/prescriptions" className="btn btn-dark mx-1">
          Go Back
        </Link>
      </div>
    </section>
  );
};
