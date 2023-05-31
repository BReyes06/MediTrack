const API_URL = "https://localhost:8080";

function makeUser(token: string) {
  const splitToken = token.split(".");
  const payload = splitToken[1];
  const jsonString = atob(payload);
  const user = JSON.parse(jsonString);

  return user;
}

export async function authenticate(user: object) {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
  };

  const response = await fetch(`${API_URL}/authenticate`, init);

  if (response.ok) {
    const data = await response.json();
    const token = data.jwt_token;
    localStorage.setItem("jwt", token);
    const user = makeUser(token);

    return Promise.resolve(user);
  } else {
    return Promise.reject();
  }
}

export async function createAccount(newUser: object) {
  const init = {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(newUser),
  };

  const response = await fetch(`${API_URL}/create_account`, init);

  if (response.ok) {
    return Promise.resolve();
  }

  const errors = await response.json();
  return Promise.reject(errors);
}
