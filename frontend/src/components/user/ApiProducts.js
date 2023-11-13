import { Box, Card, CardBody, CardHeader, Heading, SimpleGrid, Text, useColorModeValue } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { ReactComponent as RocketIcon } from '../../assets/svg/IoRocket-light.svg';
import { useNavigate } from 'react-router-dom';
import instance from '../axios/CustomAxios';
import Footer from '../footer/Footer';

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

    const GetProducts = async() => {
        try {
            const data = await instance.get("apis/user");
            setProducts(data);
        } catch {
            alert("에러 api 리스트 가져오기");
        }
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
        // GetProducts();
    },[]);

    return (
        <>
            <Box mb={2}>
                <Text fontSize="2xl" color={mainText}>API Products</Text>
            </Box>
            <Box minH="82vh">
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
                                            w='60px' h='60px'
                                            display="flex" 
                                            justifyContent="center" 
                                            alignItems="center"   
                                        >
                                            <RocketIcon />
                                        </Box>
                                    </CardHeader>
                                    <CardBody>
                                        <Box 
                                            h={"10vh"}
                                            display="flex" 
                                            justifyContent="center" 
                                            alignItems="center"
                                        >
                                            <Heading size='md'>{product.name}</Heading>
                                        </Box>
                                    </CardBody>
                                </Card>
                    ))
                }
                </SimpleGrid>
            </Box>
            <Footer />
        </>
    );
};

export default ApiProducts;