import { Link } from "react-router-dom";

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

const UserProfileCard: React.FC<UserProfilesProps> = ({appUser}) => {

    console.log(appUser)
    const authority = appUser.authorities[0]?.authority;
    return (
        <div className="d-flex flex-column align-items-center prescription-card mt-2">
            <h4>{authority}</h4>
            <h3>User Id: {appUser.appUserId}</h3>
            <small>
                <strong>Username</strong>
            </small>
            <small>{appUser.username}</small>
            <small>
                <strong>Name</strong>
            </small>
            <small>{appUser.firstName} {appUser.middleName} {appUser.lastName}</small>
            <small>
                <strong>Email</strong>
            </small>
            <small>{appUser.email}</small>
            <small>
                <strong>Phone</strong>
            </small>
            <small>{appUser.phone}</small>
            <div className="d-flex">
                <Link to={`/delete_user/${appUser.appUserId}`}
                        className="btn btn-danger mx-1">
                    Delete
                </Link>

            </div>
        </div>
    )
}

export default UserProfileCard;