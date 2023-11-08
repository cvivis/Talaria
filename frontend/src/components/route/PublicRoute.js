import { Navigate } from "react-router-dom";

const PublicRoute = ({component : Component}) => {

    const login = false;
    const role = "user";

    if(login) {
        if(role === "USER") {
            return <Navigate to="/user" />
        } else if(role === "ADMIN") {
            return <Navigate to="/admin" />
        } else {
            return <Navigate to="/developer" />
        }
    } else {
        return Component;
    }

};

export default PublicRoute;