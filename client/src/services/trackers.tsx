const API_URL = "http://localhost:8080/api/tracker";

interface TrackerType {
  prescriptionId: number;
  administrationTime: string;
  trackerId: number;
}

export async function getTrackers(prescriptionId: number) {
  const init = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(
    `${API_URL}/prescription/${prescriptionId}`,
    init
  );
  if (response.ok) {
    const json = await response.json();
    console.log(json);
    return json;
  }
}

export async function addTracker(tracker: TrackerType) {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
    body: JSON.stringify(tracker),
  };

  const response = await fetch(`http://localhost:8080/api/tracker`, init);

  if (response.ok) {
    return Promise.resolve();
  } else {
    return Promise.reject();
  }
}
