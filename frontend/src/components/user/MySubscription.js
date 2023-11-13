import { Badge, Box, Button, Card, CardBody, Flex, Spacer, Table, Tbody, Td, Text, Th, Thead, Tr, useColorModeValue, useToast } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { ReactComponent as ApiIcon } from '../../assets/svg/BsFileEarmarkCodeFill.svg';
import { CopyIcon } from '@chakra-ui/icons';
import instance from '../axios/CustomAxios';
import { useSelector } from 'react-redux';
import Footer from '../footer/Footer';

const MySubscription = () => {

    const mainText = useColorModeValue("white", "gray.200");
    const textColor = useColorModeValue("gray.700", "white");
    const borderColor = useColorModeValue("gray.200", "gray.600");
    const bgProfile = useColorModeValue("hsla(0,0%,100%,.8)", "navy.800");
    const mainColor = useColorModeValue("gray.500", "white");
    const secondaryColor = useColorModeValue("gray.400", "white");
    const borderProfileColor = useColorModeValue("white", "transparent");
    const [apiKey, setApiKey] = useState("No keys available");
    const [mySubscription, setMySubscription] = useState([]);
    const memberId = useSelector(state => state.userInfo.member_id);
    const toast = useToast();

    const copyText = async() => {
        await navigator.clipboard.writeText(apiKey);
        toast({
            title:"COPY SUCCESS",
            description:"Now you can paste the key",
            position:"top",
            status:"success",
            variant:"subtle",
            colorScheme:"blue"
        })
    };
    
    const GetKey = async() => {
        try {
            const data = await instance.get('keys/user');
            console.log(data);
            setApiKey(data);
        } catch {
            return alert("에러 겟");
        }
    };

    const ReIssueKey = async() => {
        try {
            const data = await instance.post('keys/user');
            setApiKey(data);
        } catch {
            return alert("에러 리이슈");
        }
    };

    const GetMySubscription = async(status) => {
        try {
            const data = await instance.get('apis/user/me',{
                params: {
                    member_id: memberId,
                    status: status,
                }
            });
            console.log(data);
            setMySubscription(data);
        } catch {
            return alert("에러 구독");
        }
    }

    useEffect(() => {

        setMySubscription([
            {
                name:"product1",
                discription:"설명1",
                domain:"www.asdasd1.com",
                quata:"60",
                status:"pending",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product2",
                discription:"설명2",
                domain:"www.asdasd2.com",
                quata:"100",
                status:"Rejected",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product3",
                discription:"설명3",
                domain:"www.asdasd1.com",
                quata:"75",
                status:"pending",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product4",
                discription:"설명4",
                domain:"www.asdasd1.com",
                quata:"160",
                status:"subscribing",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product5",
                discription:"설명5",
                domain:"www.asdasd5.com",
                quata:"50",
                status:"subscribing",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product5",
                discription:"설명6",
                domain:"www.asdasd6.com",
                quata:"50",
                status:"pending",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product7",
                discription:"설명7",
                domain:"www.asdasd5.com",
                quata:"50",
                status:"subscribing",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product8",
                discription:"설명8",
                domain:"www.asdasd5.com",
                quata:"50",
                status:"subscribing",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product9",
                discription:"설명9",
                domain:"www.asdasd9.com",
                quata:"50",
                status:"subscribing",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product10",
                discription:"설명10",
                domain:"www.asdasd5.com",
                quata:"50",
                status:"subscribing",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product11",
                discription:"설명11",
                domain:"www.asdasd5.com",
                quata:"50",
                status:"subscribing",
                exprtationDate:"2024.03.20",
            },
            {
                name:"product12",
                discription:"설명12",
                domain:"www.asdasd5.com",
                quata:"50",
                status:"subscribing",
                exprtationDate:"2024.03.20",
            },
        ]);

        // GetKey();

    },[]);

    useEffect(() => {

    },[apiKey,mySubscription])

    return (
        <>
            <Box mb={2}>
                <Text fontSize="2xl" color={mainText}>My Subscription</Text>
            </Box>
            <Flex direction="column">
                <Flex
                    direction="column"
                    mb="24px"
                    maxH="330px"
                    justifyContent={{ sm: "center", md: "space-between" }}
                    align="center"
                    backdropFilter="blur(21px)"
                    boxShadow="0px 2px 5.5px rgba(0, 0, 0, 0.02)"
                    border="1.5px solid"
                    borderColor={borderProfileColor}
                    bg={bgProfile}
                    p="24px"
                    borderRadius="15"
                >
                    <Flex alignSelf={"flex-start"}>
                        <Text fontSize={"xl"} fontWeight={"bold"}>My APIs</Text>
                    </Flex>
                    <Flex
                        align="center"
                        mt={"20px"}
                        direction={{ sm: "column", md: "row" }}
                        w={{ sm: "100%" }}
                        textAlign={{ sm: "center", md: "start" }}
                    >
                        <Box bgColor={"white"} borderRadius={10} w="70px" h="70px" display="flex" alignItems="center" justifyContent="center" >
                            <ApiIcon />
                        </Box>
                        <Box mx={"10px"}>
                            <Text fontSize={"xl"} fontWeight={"bold"}>API Key</Text>
                            <Flex flexDir={"row"} justifyContent={"center"} alignItems={"center"}>
                                <Text fontSize={"md"} color={"gray.400"}>{apiKey}</Text>
                                <CopyIcon mx={2} onClick={() => copyText()} cursor={"copy"}/>
                                <Button colorScheme="blue" size={"xs"} onClick={() => {ReIssueKey()}}>REISSUE</Button>
                            </Flex>
                        </Box>
                        <Spacer />
                        <Spacer />
                        <Spacer />
                        <Box>
                            <Card 
                                direction={{ base: 'column', sm: 'row' }}
                                overflow='hidden'
                                variant='outline'
                                display="flex"
                                border="2px"
                                borderColor="cyan.400"
                                onClick={() => {GetMySubscription(null)}}
                                cursor={"pointer"}
                            >
                                <Box h={"80px"} w={"60px"} maxW={{ base: '100%', sm: '60px' }} bgColor={"white"} color={"cyan.600"} display="flex" alignItems="center" justifyContent="center">
                                    <Text textAlign={"center"} as={"b"} fontSize={"4xl"}>12</Text>
                                </Box>
                                <Box h={"80px"} w={"160px"} display="flex" alignItems="center" justifyContent="center" bgColor={"cyan.400"} color={"white"}>
                                    <Text as={"b"} fontSize={"2xl"}>All APIs</Text>
                                </Box>
                            </Card>
                        </Box>
                        <Spacer />
                        <Box>
                            <Card 
                                direction={{ base: 'column', sm: 'row' }}
                                overflow='hidden'
                                variant='outline'
                                display="flex"
                                border="2px"
                                borderColor="yellow.400"
                                onClick={() => {GetMySubscription("PENDING")}}
                                cursor={"pointer"}
                            >
                                <Box h={"80px"} w={"60px"} maxW={{ base: '100%', sm: '60px' }} bgColor={"white"} color={"yellow.600"} display="flex" alignItems="center" justifyContent="center">
                                    <Text textAlign={"center"} as={"b"} fontSize={"4xl"}>3</Text>
                                </Box>
                                <Box h={"80px"} w={"160px"} display="flex" alignItems="center" justifyContent="center" bgColor={"yellow.400"} color={"white"} >
                                    <Text as={"b"} fontSize={"2xl"}>Pending</Text>
                                </Box>
                            </Card>
                        </Box>
                        <Spacer />
                        <Box>
                            <Card 
                                direction={{ base: 'column', sm: 'row' }}
                                overflow='hidden'
                                variant='outline'
                                display="flex"
                                border="2px"
                                borderColor="green.300"
                                onClick={() => {GetMySubscription("SUBSCRIBING")}}
                                cursor={"pointer"}
                            >
                                <Box h={"80px"} w={"60px"} maxW={{ base: '100%', sm: '60px' }} bgColor={"white"} color={"green.600"} display="flex" alignItems="center" justifyContent="center">
                                    <Text textAlign={"center"} as={"b"} fontSize={"4xl"}>8</Text>
                                </Box>
                                <Box h={"80px"} w={"160px"} display="flex" alignItems="center" justifyContent="center" bgColor={"green.300"} color={"white"} >
                                    <Text as={"b"} fontSize={"2xl"}>Subscribing</Text>
                                </Box>
                            </Card>
                        </Box>
                        <Spacer />
                        <Box>
                            <Card 
                                direction={{ base: 'column', sm: 'row' }}
                                overflow='hidden'
                                variant='outline'
                                display="flex"
                                border="2px"
                                borderColor="pink.300"
                                onClick={() => {GetMySubscription("REJECTED")}}
                                cursor={"pointer"}
                            >
                                <Box h={"80px"} w={"60px"} maxW={{ base: '100%', sm: '60px' }} bgColor={"white"} color={"pink.600"} display="flex" alignItems="center" justifyContent="center">
                                    <Text textAlign={"center"} as={"b"} fontSize={"4xl"}>1</Text>
                                </Box>
                                <Box h={"80px"} w={"160px"} display="flex" alignItems="center" justifyContent="center" bgColor={"pink.300"} color={"white"}>
                                    <Text as={"b"} fontSize={"2xl"}>Rejected</Text>
                                </Box>
                            </Card>
                        </Box>
                        <Spacer />
                    </Flex>
                </Flex>
            </Flex>
            <Card overflowX={{ sm: "scroll", lg: "hidden" }} borderRadius={15}>
                <CardBody>
                    <Table variant="simple" color={textColor}>
                        <Thead>
                        <Tr my=".8rem" ps="0px" color="gray.400">
                            <Th ps="0px" color="gray.400" borderColor={borderColor}>
                            Product
                            </Th>
                            <Th color="gray.400" borderColor={borderColor}>
                            Discription
                            </Th>
                            <Th color="gray.400" borderColor={borderColor}>
                            Application Domain
                            </Th>
                            <Th color="gray.400" borderColor={borderColor}>
                            Quata per Second
                            </Th>
                            <Th color="gray.400" borderColor={borderColor}>
                            Status
                            </Th>
                            <Th color="gray.400" borderColor={borderColor}>
                            Expiration Date
                            </Th>
                        </Tr>
                        </Thead>
                        <Tbody pb="0px">
                            {
                                mySubscription.map((product,index) => (
                                <Tr border="none" key={index}>
                                    <Td
                                        borderColor={borderColor}
                                        minW={{ sm: "220px", xl: "180px", "2xl": "220px" }}
                                        ps="0px"
                                        border={index === (mySubscription.length-1) ? "none" : null}
                                        px={{ xl: "2px", "2xl": "20px" }}
                                    >
                                        <Flex
                                            align="center"
                                            minWidth="100%"
                                            flexWrap="nowrap"
                                        >
                                        <Text
                                            fontSize={{ sm: "md", xl: "sm", "2xl": "md" }}
                                            color={textColor}
                                            fontWeight="bold"
                                            minWidth="100%"
                                        >
                                            {product.name}
                                        </Text>
                                        </Flex>
                                    </Td>
                                    <Td
                                        borderColor={borderColor}
                                        minW={{ sm: "150px", lg: "150px" }}
                                        border={index === (mySubscription.length-1) ? "none" : null}
                                        px={{ xl: "2px", "2xl": "20px" }}
                                    >
                                        <Flex direction="column">
                                        <Text
                                            fontSize={{ sm: "md", xl: "sm", "2xl": "md" }}
                                            color={mainColor}
                                            fontWeight="bold"
                                        >
                                            {product.discription}
                                        </Text>
                                        </Flex>
                                    </Td>
                                    <Td
                                        borderColor={borderColor}
                                        minW={{ sm: "150px", lg: "120px" }}
                                        border={index === (mySubscription.length-1) ? "none" : null}
                                        px={{ xl: "2px", "2xl": "20px" }}
                                    >
                                        <Flex align="center">
                                            <Text
                                                color={secondaryColor}
                                                fontSize={{ sm: "md", xl: "sm", "2xl": "md" }}
                                            >
                                                {product.domain}
                                            </Text>
                                        </Flex>
                                    </Td>
                                    <Td
                                        borderColor={borderColor}
                                        minW={{ sm: "200px", lg: "170px" }}
                                        border={index === (mySubscription.length-1) ? "none" : null}
                                        px={{ xl: "2px", "2xl": "20px" }}
                                    >
                                        <Text
                                            fontSize={{ sm: "md", xl: "sm", "2xl": "md" }}
                                            color={secondaryColor}
                                            fontWeight="normal"
                                            pb=".5rem"
                                        >
                                            {product.quata}
                                        </Text>
                                    </Td>
                                    <Td
                                        borderColor={borderColor}
                                        minW={{ sm: "150px", lg: "170px" }}
                                        border={index === (mySubscription.length-1) ? "none" : null}
                                        px={{ xl: "2px", "2xl": "20px" }}
                                    >
                                        <Text
                                            fontSize={{ sm: "md", xl: "sm", "2xl": "md" }}
                                            color={secondaryColor}
                                            fontWeight="normal"
                                            pb=".5rem"
                                        >
                                            {product.status}
                                        </Text>
                                    </Td>
                                    <Td
                                        borderColor={borderColor}
                                        minW={{ sm: "150px", lg: "170px" }}
                                        border={index === (mySubscription.length-1) ? "none" : null}
                                        px={{ xl: "2px", "2xl": "20px" }}
                                    >
                                        <Text
                                            fontSize={{ sm: "md", xl: "sm", "2xl": "md" }}
                                            color={secondaryColor}
                                            fontWeight="normal"
                                            pb=".5rem"
                                        >
                                            {product.exprtationDate}
                                        </Text>
                                    </Td>
                                </Tr>
                                ))
                            }
                        </Tbody>
                    </Table>
                </CardBody>
            </Card>
            <Footer />
        </>
    );
};

export default MySubscription;