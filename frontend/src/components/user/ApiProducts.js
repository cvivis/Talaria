import { Box, Card, CardBody, CardFooter, CardHeader, Grid, Heading, LinkBox, LinkOverlay, SimpleGrid, Stack, Text, VStack, useColorModeValue } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { HSeparator } from '../separator/Separator';
import { ReactComponent as RocketIcon } from '../../assets/svg/IoRocket-light.svg';
import { Link as ReactRouterLink, useNavigate } from 'react-router-dom';
import { Link as ChakraLink } from '@chakra-ui/react';

const ApiProducts = () => {

    const [products,setProducts] = useState([]);

    const mainText = useColorModeValue("white", "gray.200");
    const textColor = useColorModeValue("gray.400", "white");
    const boxShadow = useColorModeValue(
        "0px 5px 14px rgba(0, 0, 0, 0.05)",
        "unset"
    );
    const navigate = useNavigate();

    const goProductPage = (productName) => {
        return navigate("/user/API Products/"+productName);
    }

    useEffect(() => {
        setProducts([
            {
                name:"product1",
                description:"냉무1",
            },
            {
                name:"product2",
                description:"",
            },
            {
                name:"product3",
                description:"냉무3",
            },
            {
                name:"product4",
                description:"냉무4",
            },
            {
                name:"product5",
                description:"냉무5",
            },
        ]);
    },[]);

    return (
        <>
            <Box mb={2}>
                <Text fontSize="2xl" color={mainText}>API Products</Text>
            </Box>
            <Box>
                <SimpleGrid spacing={5} templateColumns='repeat(4, minmax(200px, 1fr))' >
                {
                    products.map((product,index) => (
                                <Card maxH='300px' maxW='sm' borderRadius={20} boxShadow={boxShadow} key={index}
                                    onClick={() => goProductPage(product.name)}
                                    style={{WebkitUserSelect:"none",MozUserSelect:"none",msUserSelect:"none",userSelect:"none"}}
                                    cursor={"pointer"}
                                >
                                    <CardHeader 
                                        borderTopRadius={20} 
                                        bgGradient="linear(to-br, #313860, #151928)"
                                        display="flex" 
                                        justifyContent="center" 
                                        alignItems="center"
                                    >
                                        <Box 
                                            // backgroundColor='white' 
                                            // borderRadius={10} 
                                            w='60px' h='60px'
                                            display="flex" 
                                            justifyContent="center" 
                                            alignItems="center"   
                                        >
                                            <RocketIcon />
                                        </Box>
                                    </CardHeader>
                                    <CardBody alignSelf='center'>
                                        <Stack align='center'>
                                            <Heading size='md'>{product.name}</Heading>
                                            <HSeparator w='200%' />
                                        </Stack>
                                    </CardBody>
                                    <CardFooter alignSelf='center'>
                                        <Grid templateColumns='repeat(2, 1fr)' gap={3}>
                                            <VStack minW='50px' minH='50px' borderRadius={10} spacing={0}>
                                                <Box color={textColor} fontWeight={'bold'}>APIs</Box>
                                                <Box fontWeight={'bold'}>10</Box>
                                            </VStack>
                                            <VStack minW='50px' minH='50px' borderRadius={10} spacing={0}>
                                                <Box color={textColor} fontWeight={'bold'}>In Use</Box>
                                                <Box fontWeight={'bold'}>6</Box>
                                            </VStack>
                                        </Grid>
                                    </CardFooter>
                                </Card>
                    ))
                }
                </SimpleGrid>
            </Box>
        </>
    );
};

export default ApiProducts;