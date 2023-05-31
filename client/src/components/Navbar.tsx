import { NavLink } from "react-router-dom";

import Logo from "../images/logo.ico";

export const Navbar = () => {
  return (
    <nav className="navbar navbar-expand-lg navbar-dark bg-dark">
      <NavLink
        to="/"
        className="navbar-brand mx-3"
        data-toggle="collapse"
        data-target="#navbarNav"
      >
        <img src={Logo} alt="MediTrack Logo" />
      </NavLink>
      <button
        className="navbar-toggler"
        type="button"
        data-toggle="collapse"
        data-target="#navbarNav"
        aria-controls="navbarNav"
        aria-expanded="false"
        aria-label="Toggle navigation"
      >
        <span className="navbar-toggler-icon"></span>
      </button>
      <div className="collapse navbar-collapse" id="navbarNav">
        <ul className="navbar-nav mx-2">
          <li className="nav-item active">
            <NavLink
              to="/"
              className="nav-link"
              data-toggle="collapse"
              data-target="#navbarNav"
            >
              Home
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink
              to="/login"
              className="nav-link"
              data-toggle="collapse"
              data-target="#navbarNav"
            >
              Log In
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink
              to="/signup"
              className="nav-link"
              data-toggle="collapse"
              data-target="#navbarNav"
            >
              Sign Up
            </NavLink>
          </li>
          <li className="nav-item">
            <NavLink
              to="/prescriptions"
              className="nav-link"
              data-toggle="collapse"
              data-target="#navbarNav"
            >
              Prescriptions
            </NavLink>
          </li>
        </ul>
      </div>
    </nav>
  );
};
