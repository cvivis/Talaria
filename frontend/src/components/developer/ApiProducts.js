import {
    Badge, Box, Card, CardBody, CardHeader, CardFooter, Heading, SimpleGrid, Stack, Text, useColorModeValue
    , Flex, Spacer, Button
} from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { HSeparator } from '../separator/Separator';
import { ReactComponent as RocketIcon } from '../../assets/svg/IoRocket-light.svg';
import { Link as ReactRouterLink, useNavigate } from 'react-router-dom';
import CreateAPIs from '../modal/CreateAPIs';
import CustomAxios from '../axios/CustomAxios';
import { useSelector } from 'react-redux';

const ApiProducts = () => {

    const [products, setProducts] = useState([]);
    const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);

    const mainText = useColorModeValue("white", "gray.200");
    const textColor = useColorModeValue("gray.400", "white");
    const boxShadow = useColorModeValue(
        "0px 5px 14px rgba(0, 0, 0, 0.05)",
        "unset"
    );
    const navigate = useNavigate();
    const access_token = useSelector(state => state.userInfo.access_token)

    const openCreateModal = () => {
        setIsCreateModalOpen(true);
    };

    const closeCreateModal = () => {
        setIsCreateModalOpen(false);
    };

    const goProductPage = (product) => {
        console.log('product', product)
        return navigate("/developer/API Products/" + product.name, { state: { product } });
    }

    useEffect(() => {
        const fetchData = async () => {
            try {
                console.log(`access_token`, access_token)
                CustomAxios.get(
                    `apis/developer`,
                    {
                        headers: { Authorization: `Bearer ${access_token}` },
                    }
                )
                    .then((res) => {
                        console.log(res);

                        setProducts(res.data);
                    })
            } catch (error) {
                console.error('Error:', error);
            }

        }

        fetchData();
    }, []);

    return (
        <>
            <Flex mb={2}>
                <Text fontSize="2xl" color={mainText}>API Products</Text>
                <Spacer />
                <Box m={3}>
                    <Button variant='solid' colorScheme='pink' fontSize='25px' borderRadius='5'
                        onClick={openCreateModal}>CREATE</Button>
                </Box>
            </Flex>
            <Box>
                <SimpleGrid spacing={5} templateColumns='repeat(4, minmax(200px, 1fr))' >
                    {
                        [...products].reverse().map((product, index) => (
                            <Card maxH='300px' maxW='sm' borderRadius={20} boxShadow={boxShadow} key={index}
                                onClick={() => goProductPage(product)}
                                style={{ WebkitUserSelect: "none", MozUserSelect: "none", msUserSelect: "none", userSelect: "none" }}
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
                                        w='60px' h='60px'
                                        display="flex"
                                        justifyContent="center"
                                        alignItems="center"
                                    >
                                        <RocketIcon />
                                    </Box>
                                </CardHeader>
                                <CardBody alignSelf='center' pb={0}>
                                    <Stack align='center'>
                                        <Heading size='md'>{product.name}</Heading>
                                        <HSeparator w='200%' />
                                    </Stack>
                                </CardBody>
                                <CardFooter alignSelf='center'>
                                    {/* <Grid templateColumns='repeat(2, 1fr)' gap={3}>
                                        <VStack minW='50px' minH='50px' borderRadius={10} spacing={0}>
                                            <Box color={textColor} fontWeight={'bold'}>APIs</Box>
                                            <Box fontWeight={'bold'}>10</Box>
                                        </VStack>
                                        <VStack minW='50px' minH='50px' borderRadius={10} spacing={0}>
                                            <Box color={textColor} fontWeight={'bold'}>In Use</Box>
                                            <Box fontWeight={'bold'}>6</Box>
                                        </VStack>
                                    </Grid> */}
                                    {
                                        {
                                            REJECTED:
                                                <Badge variant='solid' colorScheme='pink' fontSize='15px' borderRadius='5' cursor={"default"}>REJECTED</Badge>,
                                            PENDING:
                                                <Badge variant='solid' colorScheme='yellow' fontSize='15px' borderRadius='5' cursor={"default"}>PENDING</Badge>,
                                            APPROVED_ON:
                                                <Badge variant='solid' colorScheme='green' fontSize='15px' borderRadius='5' cursor={"default"}>APPROVED_ON</Badge>,
                                            APPROVED_OFF:
                                                <Badge variant='solid' colorScheme='gray' fontSize='15px' borderRadius='5' cursor={"default"}>APPROVED_OFF</Badge>,
                                        }[product.status]
                                    }
                                </CardFooter>
                            </Card>
                        ))
                    }
                </SimpleGrid>
            </Box>

            <CreateAPIs isOpen={isCreateModalOpen} onClose={closeCreateModal}></CreateAPIs>
        </>
    );
};

export default ApiProducts;