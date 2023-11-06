import { Badge, Box, Flex, HStack, Spacer, Text, useColorModeValue } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import SwaggerUI from 'swagger-ui-react';
import "swagger-ui-react/swagger-ui.css";
import apiSpec from "../../assets/json/openapi.json";

const Product = () => {

    useEffect(() => {
        
    },[]);
    
    let params = useParams(); // params 통해서 조회 API 돌릴 예정
    const mainText = useColorModeValue("white", "gray.200");
    const [status, setStatus] = useState("subscribe");

    return (
        <>
            <Flex mb={2}>
                <Text fontSize="2xl" color={mainText} m={0}>{params.productName}</Text>
                <Spacer />
                <Box mr={3}>
                    {
                        {
                            reject:<Badge variant='solid' colorScheme='pink' fontSize='25px' borderRadius='5'>REJECTED</Badge>,
                            pending:<Badge variant='solid' colorScheme='yellow' fontSize='25px' borderRadius='5'>PENDING</Badge>,
                            subscribing:<Badge variant='solid' colorScheme='green' fontSize='25px' borderRadius='5'>SUBSCRIBING</Badge>,
                            subscribe:<Badge variant='solid' colorScheme='cyan' fontSize='25px' borderRadius='5'>SUBSCRIBE</Badge>,
                        }[status]
                    }
                </Box>
            </Flex>
            <Box bgColor='white' borderRadius={20} p='1px'>
                <Box>
                    <SwaggerUI spec={apiSpec} />
                </Box>
            </Box>
        </>
    );
};

export default Product;