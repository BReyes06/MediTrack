const API_URL = "http://localhost:8080/api/doctor";

interface Doctor {
  firstName: string;
  middleName: string;
  lastName: string;
  location: string;
  phone: number;
  doctorId: number;
}

export async function getDoctors(userId: number) {
  const init = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(`${API_URL}/user/${userId}`, init);

  if (response.ok) {
    const json = await response.json();
    return json;
  }
}

export async function getDoctor(doctorId: string) {
  const init = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(
    `http://localhost:8080/api/doctor/${doctorId}`,
    init
  );
  const json = await response.json();
  return json;
}

export async function addDoctor(doctor: Doctor) {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
    body: JSON.stringify(doctor),
  };
  const response = await fetch("http://localhost:8080/api/doctor", init);

  if (response.ok) {
    return true;
  } else {
    return false;
  }
}

export async function updateDoctor(doctor: Doctor) {
  const init = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
    body: JSON.stringify(doctor),
  };

  const response = await fetch(`${API_URL}/${doctor.doctorId}`, init);

  if (response.ok) {
    return Promise.resolve();
  } else {
    return Promise.reject();
  }
}

export async function deleteDoctor(doctorId: number) {
  const init = {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(`${API_URL}/${doctorId}`, init);

  if (response.ok) {
    return Promise.resolve();
  } else {
    return Promise.reject();
  }
}
