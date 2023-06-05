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
  console.log(finalObject);
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
