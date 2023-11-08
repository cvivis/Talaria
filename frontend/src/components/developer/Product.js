import { Button, Box, Flex, Spacer, Text, 
    useColorModeValue } from '@chakra-ui/react';
import React, { useEffect } from 'react';
import { useParams } from 'react-router-dom';
import apiSpec from "../../assets/json/openapi.json";
import 'swagger-ui-react/swagger-ui.css';

const Product = () => {
    useEffect(() => {
    },[]);
    
    let params = useParams(); // params 통해서 조회 API 돌릴 예정
    const mainText = useColorModeValue("white", "gray.200");
   
    return (
        <>
            <Flex mb={2}>
                <Text fontSize="2xl" color={mainText} m={0}>{params.productName}</Text>
                <Spacer />
                <Box mr={3}>
                    <Button variant='solid' colorScheme='pink' fontSize='25px' borderRadius='5'>UPDATE</Button>                
                </Box>
            </Flex>
            <Box bgColor='white' borderRadius={20} p='1px'>
               
            </Box>
        </>
    );
};

export default Product;