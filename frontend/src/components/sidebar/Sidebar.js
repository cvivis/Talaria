import { Box, Center, HStack, Text, VStack, useColorMode, useColorModeValue } from '@chakra-ui/react';
import React, { Children } from 'react';
import { Scrollbars } from 'react-custom-scrollbars-2';
import { ReactComponent as Logo } from '../../assets/svg/talaria-logo-dark-small.svg';
import { HSeparator } from '../separator/Separator';

const Sidebar = ({setWidth, setHeight, children}) => {

    let sidebarBg = useColorModeValue('white', 'navy.800');
    const { colorMode } = useColorMode();

    return (
        <>
            <Box w={setWidth} h='95vh' maxH={setHeight} bg={sidebarBg} borderRadius='20px' m='5' overflowY={'auto'} position='fixed' >
                <Scrollbars>
                    <Center>
                        <VStack>
                            <HStack m='10px' h='50px'>
                                <Logo />
                                <Text fontSize='3xl' fontWeight='bold'>Talaria</Text>
                            </HStack>
                            <HSeparator />
                            {children}
                        </VStack>
                    </Center>
                </Scrollbars>
            </Box>
        </>
    );
};

export default Sidebar;