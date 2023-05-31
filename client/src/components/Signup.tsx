import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";

import { createAccount } from "../services/auth";
import { FormErrors } from "./FormErrors";

type INITIAL_USER = {
  username: string;
  password: string;
  email: string;
  phone: string;
  firstName: string;
  middleName: string;
  lastName: string;
};

let INITIAL_USER = {
  username: "",
  password: "",
  email: "",
  phone: "",
  firstName: "",
  middleName: "",
  lastName: "",
};

export const Signup = () => {
  const [user, setUser] = useState(INITIAL_USER);
  const [errors, setErrors] = useState([]);
  const navigate = useNavigate();

  function handleChange({
    target: { name, value },
  }: {
    target: { name: string; value: string };
  }) {
    setUser({ ...user, [name]: value });
  }

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    createAccount(user)
      .then(() => {
        navigate("/login");
      })
      .catch(setErrors);
  }

  return (
    <section>
      <form onSubmit={handleSubmit}>
        <h2>Create an account.</h2>
        <div>
          <label htmlFor="username" className="form-text">
            Username:&nbsp;
          </label>
          <input
            type="text"
            name="username"
            id="username"
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="password" className="form-text">
            Password:&nbsp;
          </label>
          <input
            type="password"
            name="password"
            id="password"
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="firstName" className="form-text">
            First Name:&nbsp;
          </label>
          <input
            type="text"
            name="firstName"
            id="firstName"
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="middleName" className="form-text">
            Middle Name:&nbsp;
          </label>
          <input
            type="text"
            name="middleName"
            id="middleName"
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="lastName" className="form-text">
            Last Name:&nbsp;
          </label>
          <input
            type="text"
            name="lastName"
            id="lastName"
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="email" className="form-text">
            Email address:&nbsp;
          </label>
          <input
            type="email"
            name="email"
            id="email"
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <div>
          <label htmlFor="phone" className="form-text">
            Phone Number:&nbsp;
          </label>
          <input
            type="tel"
            name="phone"
            id="phone"
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <div className="d-flex justify-content-center mt-3">
          <button className="btn btn-danger mx-2">Submit</button>
          <Link to="/" className="btn btn-dark mx-2">
            Cancel
          </Link>
        </div>
        {errors.length > 0 && <FormErrors errors={errors} />}
      </form>
    </section>
  );
};
