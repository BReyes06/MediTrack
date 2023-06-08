import { useState, useEffect, useContext } from "react";
import { authenticate } from "../services/auth";
import { useNavigate, Link, useLocation } from "react-router-dom";
import { AuthContext } from "../contexts/AuthContext";
import { refresh } from "../services/auth";

type User = {
  username: string;
  password: string;
};

const INITIAL_USER: User = {
  username: "",
  password: "",
};

export const Login: React.FC = () => {
  const [user, setUser] = useState(INITIAL_USER);
  const [error, setError] = useState(false);
  //eslint-disable-next-line
  const context = useContext(AuthContext);
  const navigate = useNavigate();
  const location = useLocation();

  function handleChange({
    target: { name, value },
  }: {
    target: { name: string; value: string };
  }) {
    setUser({ ...user, [name]: value });
  }

  function handleSubmit(event: React.FormEvent<HTMLFormElement>) {
    event.preventDefault();
    authenticate(user)
      .then((loggedInUser) => {
        context!.login(loggedInUser);
        setTimeout(refresh, 29 * 60 * 1000);
        navigate("/");
      })
      .catch(() => setError(true));
  }

  useEffect(() => {
    if (location.state?.user) {
      setUser(location.state.user);
    } else {
      setUser(INITIAL_USER);
    }
  }, [location.state]);

  return (
    <section>
      <form
        className="d-flex flex-column align-items-center"
        onSubmit={handleSubmit}
        autoComplete="off"
      >
        <h2>Enter your log in details.</h2>
        <div className="form-group">
          <label htmlFor="username">Username:&nbsp;</label>
          <input
            type="text"
            name="username"
            id="username"
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password:&nbsp;</label>
          <input
            type="password"
            name="password"
            id="password"
            className="form-control"
            onChange={handleChange}
          />
        </div>
        <button className="btn btn-dark mt-3">Log In</button>
        {error && (
          <div className="alert alert-danger mt-2" role="alert">
            No user found with this combination.{" "}
            <Link to="/signup">Sign up</Link> or try again.
          </div>
        )}
      </form>
    </section>
  );
};
