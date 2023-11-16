import { Box, Center, Image, Text, VStack } from '@chakra-ui/react';
import React from 'react';
import Spinner from '../../assets/gif/Gear-0.2s-200px.gif';

const Loading = () => {
    return (
        <Center>
            <VStack>
                <Text fontSize={"4xl"}>Please wait for loading ...</Text>
                <Image src={Spinner} alt='Loading ...' w="7vw" />
            </VStack>
        </Center>
    );
};

export default Loading;