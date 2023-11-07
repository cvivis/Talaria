import { Box,Text } from "@chakra-ui/react";
import { useEffect, useState } from "react";

function Chart1() {

    return (
        <>
        <Box bg='white' w='36vw' h='40vh' borderRadius="20px"  boxShadow="lg">
            <Text p={3}>CPU</Text>
        </Box>
        </>

    );
}
export default Chart1;