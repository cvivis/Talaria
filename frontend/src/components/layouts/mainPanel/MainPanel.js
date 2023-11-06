import { Box } from '@chakra-ui/react';

const MainPanel = ({setWidth, setHeight, children}) => {

    return (
        <Box w={setWidth} h={setHeight} borderRadius='20px' mx='10'>
            <Box>
                {children}
            </Box>
        </Box>
    );
};

export default MainPanel;