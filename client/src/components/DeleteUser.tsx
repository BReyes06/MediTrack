import { useState, useEffect } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { getAppUsers } from "../services/appUsers";
import { deleteAppUser } from "../services/appUsers";

interface UserProfilesProps {
    appUser: AppUser
}

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

const defaultUser: AppUser = {
    appUserId: 0,
    firstName: "",
    middleName: "",
    lastName: "",
    email: "",
    phone: "",
    username: "",
    authorities: []
};

const DeleteUser: React.FC = ({}) => {
    const {appUserId} = useParams<{ appUserId: string }>();
    const [user, setUser] = useState<AppUser>(defaultUser);
    const navigate = useNavigate();
  
    useEffect(() => {
        fetchUser();

    }, [])

    const fetchUser = async () => {
        try {
            const users = await getAppUsers();
            const matchedUser = users.find(
                (user:AppUser) => user.appUserId === Number(appUserId)
            );
            setUser(matchedUser);
        } catch (error) {
            console.log("Error Fetching")
        }
    }

    async function handleDelete() {
        await deleteAppUser(Number(appUserId))
        navigate("/all_users")
    }
    
    return (
        <div className="container deleteUser">
            <section className="alert alert-danger" role="alert">
                <h2>Are you sure you want to delete User with ID: {appUserId}</h2>
                <h4>Account Type: {user.authorities[0]?.authority}</h4>
                <p>
                    <strong>Username:&nbsp;</strong>
                    {user.username}
                </p>
                <p>
                    <strong>Name:&nbsp;</strong>
                    {user.firstName} {user.middleName} {user.lastName}
                </p>
                <p>
                    <strong>Email:&nbsp;</strong>
                    {user.email}
                </p>
                <p>
                    <strong>Phone Number:&nbsp;</strong>
                    {user.phone}
                </p>
                <div>
                    <button className="btn btn-danger" onClick={handleDelete}>
                    Delete
                    </button>
                    <Link to="/all_users" className="btn btn-dark mx-2">
                    Cancel
                    </Link>
                </div>
            </section>
        </div>
    )
} 

export default DeleteUser;