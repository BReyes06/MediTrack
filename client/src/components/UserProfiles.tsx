import { useContext, useEffect, useState } from "react";
import UserProfileCard from "./UserProfileCard";

import { getAppUsers } from "../services/appUsers";

interface AppUser {
    appUserId: number;
    firstName: string;
    middleName: string;
    lastName: string;
    email: string;
    phone: string;
    username: string;
    authorities: {authority: string}[];
}

export const UserProfiles: React.FC = () => {
    
    const[appUsers, setAppUsers] = useState<AppUser[]>([]);
    
    useEffect(() => {
        fetchUsers();
    }, [])

    const fetchUsers = async () => {
        try {
            const users = await getAppUsers();
            setAppUsers(users);
        } catch (error) {
            console.log("Error Fetching")
        }
    }
    
    return (
        <div className="container">
            {appUsers.map((appUser) => {
                return(
                    <UserProfileCard
                        key={appUser?.username}
                        appUser={appUser}
                    />
                )
            })}
        </div>    
    )
}
