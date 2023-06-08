const url = "http://localhost:8080"

export async function getAppUsers() {
    const init = {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
        },
    };
    
    const response = await fetch(`${url}/all_users`, init);

    if(response.ok) {
        const json = await response.json();
        return json;
    } else {
        console.log("ERROR!!")
    }
};

export async function deleteAppUser(appUserId: number) {
    const init = {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${localStorage.getItem("jwt")}`,
        },
    };
    const response = await fetch(`${url}/delete_account/${appUserId}`, init);
}