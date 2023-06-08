interface smsMessage {
  phone: string;
  message: string;
}

export async function sendText(smsMsg: smsMessage) {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
    body: JSON.stringify(smsMsg),
  };

  const response = await fetch("http://localhost:8080/api/sms", init);
  if (response.ok) {
    return Promise.resolve();
  } else {
    const json = await response.json();
    console.log(json);
  }
}
