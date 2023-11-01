import { Navigate } from "react-router-dom";

const PublicRoute = ({component : Component}) => {

    const login = false;
    const role = "user";

    if(login) {
        if(role === "user") {
            return <Navigate to="/user" />
        } else if(role === "admin") {
            return <Navigate to="/admin" />
        } else {
            return <Navigate to="/developer" />
        }
    } else {
        return Component;
    }

};

export default PublicRoute;