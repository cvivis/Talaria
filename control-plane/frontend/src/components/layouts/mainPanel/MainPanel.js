import { Box } from '@chakra-ui/react';

const MainPanel = ({setWidth, setHeight, children}) => {

    return (
        <Box w={setWidth} h={setHeight} minH="95vh" borderRadius='20px' mx='10'>
            {children}
        </Box>
    );
};

export default MainPanel;