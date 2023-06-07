export async function getMeds(drug: string) {
  const response = await fetch(
    `https://api.fda.gov/drug/ndc.json?search=generic_name:"${drug}"&limit=5`
  );
  const json = await response.json();
  return json;
}

interface Medication {
  medication: string;
  hourlyInterval: number;
  pillCount: number;
  startTime: string;
  num: number;
  product_ndc: string;
  prescriptionId: number;
  app_user_id: number;
  doctor?: object;
  pharmacy?: object;
  appUser?: object;
}

interface AppUser {
  app_user_id: number;
}

export async function addPrescription(medication: Medication, user: AppUser) {
  const finalObject = {
    medication: medication.medication,
    hourlyInterval: medication.hourlyInterval * medication.num,
    pillCount: medication.pillCount,
    startTime: medication.startTime,
    product_ndc: medication.product_ndc,
    app_user_id: user.app_user_id,
  };

  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
    body: JSON.stringify(finalObject),
  };

  try {
    const response = await fetch("http://localhost:8080/prescription", init);

    if (response.ok) {
      const json = await response.json();
      console.log(json);
    } else {
      console.error("Error:", response.status, response.statusText);
    }
  } catch (error: any) {
    console.error("Error:", error.message);
  }
}

export async function getUserPrescriptions(userId: number) {
  const init = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(
    `http://localhost:8080/prescription/user/${userId}`,
    init
  );

  if (response.ok) {
    const json = await response.json();
    return json;
  } else {
    console.log("ERROR!!");
  }
}

export async function updatePrescription(prescription: Medication) {
  console.log(prescription);
  delete prescription?.appUser;
  delete prescription?.doctor;
  delete prescription?.pharmacy;

  const init = {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
    body: JSON.stringify(prescription),
  };

  const response = await fetch(
    `http://localhost:8080/prescription/${prescription.prescriptionId}`,
    init
  );

  if (response.ok) {
    return Promise.resolve();
  } else {
    const json = await response.json();
    console.log(json);
  }
}

export async function deletePrescription(prescriptionId: number) {
  const init = {
    method: "DELETE",
    headers: {
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(
    `http://localhost:8080/prescription/${prescriptionId}`,
    init
  );

  if (!response.ok) {
    const json = await response.json();
    console.log(json);
  }
}
