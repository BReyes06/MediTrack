const API_URL = "http://localhost:8080/api/pharmacy";

interface Pharmacy {
  name: string;
  email: string;
  phone: string;
  address: string;
  prescriptionId: number;
  pharmacyId: number;
}

export async function getPharmaciesByUser(userId: number) {
  const init = {
    method: "GET",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(`${API_URL}/user/${userId}`, init);

  if (response.ok) {
    const json = await response.json();
    return json;
  } else {
    return Promise.reject();
  }
}

export async function getPharmacy(pharmacyId: string) {
  const init = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(
    `http://localhost:8080/api/pharmacy/${pharmacyId}`,
    init
  );
  const json = await response.json();
  return json;
}

export async function addPharmacy(pharmacy: Pharmacy) {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
    body: JSON.stringify(pharmacy),
  };

  const response = await fetch(API_URL, init);

  if (response.ok) {
    return Promise.resolve();
  } else {
    return Promise.reject();
  }
}

export async function updatePharmacy(pharmacy: Pharmacy) {
  const init = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
    body: JSON.stringify(pharmacy),
  };

  const response = await fetch(`${API_URL}/${pharmacy.pharmacyId}`, init);

  if (response.ok) {
    return Promise.resolve();
  } else {
    return Promise.reject();
  }
}

export async function deletePharmacy(pharmacyId: number) {
  const init = {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(`${API_URL}/${pharmacyId}`, init);

  if (response.ok) {
    return Promise.resolve();
  } else {
    return Promise.reject();
  }
}
