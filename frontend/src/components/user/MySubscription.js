import { Badge, Box, Button, Card, CardBody, Flex, Spacer, Table, Tbody, Td, Text, Th, Thead, Tr, useColorModeValue, useToast } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { ReactComponent as ApiIcon } from '../../assets/svg/BsFileEarmarkCodeFill.svg';
import { CopyIcon } from '@chakra-ui/icons';
import instance from '../axios/CustomAxios';
import { useDispatch, useSelector } from 'react-redux';
import Footer from '../footer/Footer';
import { useRef } from 'react';
import { useNavigate } from 'react-router-dom';

const MySubscription = () => {

    const mainText = useColorModeValue("white", "gray.200");
    const textColor = useColorModeValue("black", "white");
    const borderColor = useColorModeValue("gray.200", "gray.600");
    const bgProfile = useColorModeValue("hsla(0,0%,100%,.8)", "navy.800");
    const mainColor = useColorModeValue("black", "white");
    const secondaryColor = useColorModeValue("black", "white");
    const borderProfileColor = useColorModeValue("white", "transparent");
    const [apiKey, setApiKey] = useState("No keys available");
    const [mySubscription, setMySubscription] = useState([]);
    const [allApi, setAllApi] = useState(0);
    const [pendingApi, setPendingApi] = useState(0);
    const [subscribingApi, setSubscribingApi] = useState(0);
    const [rejectedApi, setRejectedApi] = useState(0);
    const allCount = useRef(0);
    const pendingCount = useRef(0);
    const subscribingCount = useRef(0);
    const rejectedCount = useRef(0);
    const memberId = useSelector(state => state.userInfo.member_id);
    const dispatch = useDispatch();
    const toast = useToast();
    const navigate = useNavigate();

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

    const goProductPage = (productName) => {
        return navigate("/user/API Products/"+productName);
    }
    
    const GetKey = async() => {
        try {
            const data = await instance.get('keys/user');
            setApiKey(data.data.key);
        } catch(error) {
            return alert(error);
        }
    };

    const ReIssueKey = async() => {
        try {
            const data = await instance.post('keys/user');
            setApiKey(data);
        } catch(error) {
            return alert(error);
        }
    };

    const GetMySubscription = async(status) => {
        try {
            const data = await instance.get('apis/user/me',{
                params: {
                    status: status,
                }
            });
            setMySubscription(data.data);
            if(status === "all") {
                CountApi(data.data);
            }
        } catch (error) {
            alert(error);
        }
    }

    const CountApi = (data) => {
        let pending = 0;
        let subscribing = 0;
        let rejected = 0;
        console.log(data);
        data.map(function(product) {
            if(product.status === "PENDING") {
                pending = pending + 1;
            } else if(product.status === "SUBSCRIBING") {
                subscribing = subscribing + 1;
                setSubscribingApi(subscribingApi+1);
            } else if(product.status === "REJECTED") {
                rejected = rejected + 1;
            }
        });

        setPendingApi(pending);
        pendingCount.current = pending;
        setSubscribingApi(subscribing);
        subscribingCount.current = subscribing;
        setRejectedApi(rejected);
        rejectedCount.current = rejected;
        setAllApi(pending+subscribing+rejected);
        allCount.current = (pending+subscribing+rejected);
    }

    useEffect(() => {

        GetMySubscription("all");
        GetKey();

    },[]);

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
                                onClick={() => {GetMySubscription("all")}}
                                cursor={"pointer"}
                            >
                                <Box h={"80px"} w={"60px"} maxW={{ base: '100%', sm: '60px' }} bgColor={"white"} color={"cyan.600"} display="flex" alignItems="center" justifyContent="center">
                                    <Text textAlign={"center"} as={"b"} fontSize={"4xl"}>{allCount.current}</Text>
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
                                    <Text textAlign={"center"} as={"b"} fontSize={"4xl"}>{pendingCount.current}</Text>
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
                                    <Text textAlign={"center"} as={"b"} fontSize={"4xl"}>{subscribingCount.current}</Text>
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
                                    <Text textAlign={"center"} as={"b"} fontSize={"4xl"}>{rejectedCount.current}</Text>
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
                            <Th color="gray.400" borderColor={borderColor}>
                            Product
                            </Th>
                            <Th color="gray.400" borderColor={borderColor}>
                            Application Domain
                            </Th>
                            <Th color="gray.400" borderColor={borderColor}>
                            Quota per Second
                            </Th>
                            <Th color="gray.400" borderColor={borderColor}>
                            Status
                            </Th>
                        </Tr>
                        </Thead>
                        <Tbody pb="0px">
                            {
                                mySubscription.map((product,index) => (
                                <Tr border="none" key={index} onClick={(e) => {goProductPage(product.name)}} cursor={"pointer"}>
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
                                        minW={{ sm: "150px", lg: "120px" }}
                                        border={index === (mySubscription.length-1) ? "none" : null}
                                        px={{ xl: "2px", "2xl": "20px" }}
                                    >
                                        <Flex align="center">
                                            <Text
                                                color={secondaryColor}
                                                fontSize={{ sm: "md", xl: "sm", "2xl": "md" }}
                                            >
                                                {product.routing_url}
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
                                            {product.quota}
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
                                            {
                                                {
                                                    REJECTED:
                                                        <Badge variant='solid' colorScheme='pink' fontSize='15px' borderRadius='5' cursor={"default"}>REJECTED</Badge>,
                                                    PENDING:
                                                        <Badge variant='solid' colorScheme='yellow' fontSize='15px' borderRadius='5' cursor={"default"}>PENDING</Badge>,
                                                    SUBSCRIBING:
                                                        <Badge variant='solid' colorScheme='green' fontSize='15px' borderRadius='5' cursor={"default"}>SUBSCRIBING</Badge>,
                                                }[product.status]
                                            }
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