import { Navigate } from "react-router-dom";
import { useSelector } from 'react-redux';
import { useToast } from "@chakra-ui/react";

const PrivateRoute = ({selectRole, component : Component}) => {
    const login = useSelector(state => state.userInfo.access_token);;
    const role = useSelector(state => state.userInfo.role);
    const toast = useToast();

    if(!login) {
        toast({
            title:"ALERT",
            description:"Please sign in !",
            position:"top",
            status:"error",
            variant:"subtle",
            isClosable:"true",
        })
        return <Navigate to="/" />
    }

    if(role !== selectRole) {
        toast({
            title:"ERROR",
            description:"Account not authorized !",
            position:"top",
            status:"error",
            variant:"subtle",
            isClosable:"true",
        })
        return <Navigate to="/" />
    } 

    return Component;
}

export default PrivateRoute;