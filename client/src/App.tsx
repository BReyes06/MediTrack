import "./App.css";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";

import { Navbar } from "./components/Navbar";
import { Home } from "./components/Home";
import { Login } from "./components/Login";
import { Signup } from "./components/Signup";
import { Prescriptions } from "./components/Prescriptions";
import { NotFound } from "./components/NotFound";

function App() {
  return (
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
          <Route path="*" element={<NotFound />} />
        </Routes>
      </main>
    </Router>
  );
}

export default App;
