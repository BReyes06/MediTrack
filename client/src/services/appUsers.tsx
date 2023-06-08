const url = "http://localhost:8080";

export async function getAppUsers() {
  const init = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(`${url}/all_users`, init);

  if (response.ok) {
    const json = await response.json();
    return json;
  } else {
    console.log("Could not find App Users");
  }
}

export async function getAppUserById(appUserId: number) {
  console.log(appUserId);
  const init = {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };

  const response = await fetch(`${url}/user/${appUserId}`, init);
  if (response.ok) {
    const json = await response.json();
    return json;
  } else {
    console.log("Could not find User");
  }
}

export async function deleteAppUser(appUserId: number) {
  const init = {
    method: "DELETE",
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${localStorage.getItem("jwt")}`,
    },
  };
  const response = await fetch(`${url}/delete_account/${appUserId}`, init);

  if (response.ok) {
    return Promise.resolve();
  } else {
    return Promise.reject();
  }
}
