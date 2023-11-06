import { Box } from "@chakra-ui/react";
import AppCharts from '../components/dashboard/StatusLiveChart';

function Admin() {

    return (
        <>
            <Box>
                어드민 페이지
                <AppCharts></AppCharts>
            </Box>
        </>
    );
}

export default Admin;