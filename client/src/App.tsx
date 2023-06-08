import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { useState, useEffect, useContext } from "react";

import { AuthProvider, AuthContext } from "./contexts/AuthContext";

import { Navbar } from "./components/Navbar";
import { Home } from "./components/Home";
import { Login } from "./components/Login";
import { Signup } from "./components/Signup";
import { Prescriptions } from "./components/Prescriptions";
import { NotFound } from "./components/NotFound";
import { EditPrescriptionForm } from "./components/EditPrescriptionForm";
import { PrescriptionForm } from "./components/PrescriptionForm";
import { DeletePrescription } from "./components/DeletePrescription";
import { Tracker } from "./components/Trackers";
import { DoctorInfo } from "./components/DoctorInfo";
import { DoctorForm } from "./components/DoctorForm";
import { EditDoctorForm } from "./components/EditDoctorForm";
import { UserProfiles } from "./components/UserProfiles";
import DeleteUser from "./components/DeleteUser";

import { refresh } from "./services/auth";

const App: React.FC = () => {
  const [restoreLoginAttemptCompleted, setRestoreLoginAttemptCompleted] =
    useState(false);
  const context = useContext(AuthContext);

  useEffect(() => {
    refresh()
      .then((user) => {
        context?.login(user);
      })
      .catch(() => {
        context?.logout();
      })
      .finally(() => setRestoreLoginAttemptCompleted(true));
    //eslint-disable-next-line
  }, []);

  if (!restoreLoginAttemptCompleted) {
    return null;
  }

  return (
    <AuthProvider>
      <Router>
        <header>
          <Navbar />
        </header>
        <main className="d-flex flex-column align-items-center mt-3">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/signup" element={<Signup />} />
            <Route path="/prescriptions" element={<Prescriptions />} />
            <Route path="/prescriptions/add" element={<PrescriptionForm />} />
            <Route
              path="/doctors/add/:prescriptionId"
              element={<DoctorForm />}
            />
            <Route
              path="/doctors/edit/:doctorId"
              element={<EditDoctorForm />}
            />
            <Route
              path="/edit/:prescriptionId"
              element={<EditPrescriptionForm />}
            />
            <Route
              path="/delete/:prescriptionId"
              element={<DeletePrescription />}
            />
            <Route path="/tracker/:prescriptionId" element={<Tracker />} />
            <Route path="/doctor/:prescriptionId" element={<DoctorInfo />} />
            <Route path="/all_users" element={<UserProfiles />} />
            <Route path="/delete_user/:appUserId" element={<DeleteUser />} />
            <Route path="*" element={<NotFound />} />
          </Routes>
        </main>
      </Router>
    </AuthProvider>
  );
};

export default App;
