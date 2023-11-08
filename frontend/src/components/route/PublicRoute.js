import { Navigate } from "react-router-dom";
import { useSelector } from 'react-redux';

const PublicRoute = ({component : Component}) => {

    const login = useSelector(state => state.userInfo.access_token);;
    const role = useSelector(state => state.userInfo.role);

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