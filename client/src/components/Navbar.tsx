import { NavLink, useNavigate } from "react-router-dom";
import { AuthContext } from "../contexts/AuthContext";
import { useContext } from "react";
import Logo from "../images/logo.ico";

export const Navbar = () => {
  const context = useContext(AuthContext);
  const navigate = useNavigate();
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
        className="navbar-toggler mx-2"
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
            {!context!.user && (
              <NavLink
                to="/login"
                className="nav-link"
                data-toggle="collapse"
                data-target="#navbarNav"
              >
                Log In
              </NavLink>
            )}
          </li>
          <li className="nav-item">
            {!context!.user && (
              <NavLink
                to="/signup"
                className="nav-link"
                data-toggle="collapse"
                data-target="#navbarNav"
              >
                Sign Up
              </NavLink>
            )}
          </li>
          <li className="nav-item">
            {context!.user && (
              <NavLink
                to="/prescriptions"
                className="nav-link"
                data-toggle="collapse"
                data-target="#navbarNav"
              >
                Prescriptions
              </NavLink>
            )}
          </li>
          <li className="nav-item">
            {context!.user && context!.user.authorities === "ADMIN" && (
              <NavLink
                to="/all_users"
                className="nav-link"
                data-toggle="collapse"
                data-target="#navbarNav"
              >
                User Accounts
              </NavLink>
            )}
          </li>
          <li className="nav-item">
            {context!.user && (
              <button
                className="btn btn-danger"
                onClick={() => {
                  context!.logout();
                  navigate("/");
                }}
              >
                Log Out
              </button>
            )}
          </li>
        </ul>
      </div>
    </nav>
  );
};
