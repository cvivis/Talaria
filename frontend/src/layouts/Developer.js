import {
    Accordion,
    AccordionButton,
    AccordionIcon,
    AccordionItem,
    AccordionPanel,
    Box,
    Breadcrumb,
    BreadcrumbItem,
    BreadcrumbLink,
    Flex,
    List,
    ListItem,
    Text,
    useColorModeValue
} from "@chakra-ui/react";
import Footer from "../components/footer/Footer";
import MainPanel from "../components/layouts/mainPanel/MainPanel";
import Sidebar from "../components/sidebar/Sidebar";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

function Developer() {

    let bgBoxColor = useColorModeValue('blue.500', 'navy.900');
    let mainText = useColorModeValue("white", "gray.200");
    
    const [mainCategory, setMainCategory] = useState("API Products");
    const [secondCategory, setSecondCategory] = useState("");
    const [thirdCategory, setThirdCategory] = useState("");
    const [products,setProducts] = useState([]);
    
    const location = useLocation();
    const navigate = useNavigate();


    function moveProductPage(productName) {
        return navigate("/developer/API Products/"+productName);
    };

    const movePage = (route,e) => {
        console.log(decodeURI(route));
        e.preventDefault();
        return navigate(decodeURI(route));
    }


    useEffect(() => {
        console.log(location.pathname);
        const locationArray = decodeURI(location.pathname).split('/');
        console.log(locationArray);
        for(let i = 0; i<locationArray.length; i++) {
            if(i === 2) {
                setMainCategory(locationArray[i]);
            } else if(i === 3) {
                setSecondCategory(locationArray[i]);
            } else if(i === 4) {
                setThirdCategory(locationArray[i]);
            }
        }

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
            {
                name:"하나 더 테스트",
                description:"냉무5",
            },
        ]);


    },[location,mainCategory]);


    return (
        <>
            <Box h='40vh' w='100%' position='absolute' bg={bgBoxColor} top='0' zIndex="-1" />
            <Flex direction='row'>
                <Sidebar setWidth='15vw' setHeigth='95vh'>
                    <Accordion
                        mt={5}
                        allowMultiple
                        borderColor={"white"}
                    >
                        <AccordionItem maxW='15vw'>
                            <AccordionButton _hover={{ backgroundColor: "white" }} _expanded={{ bg: "blue", color: "white" }} borderRadius={10}>
                                <Box as="span" flex='1' textAlign='left' onClick={(e) => movePage("/developer", e)} w='15vw'>
                                    <Text fontSize={"xl"} fontWeight={"bold"}>API Products</Text>
                                </Box>
                                <AccordionIcon />
                            </AccordionButton>
                            <AccordionPanel pb={4} ml={4}>
                                <List spacing={3}>
                                    {
                                        products.map((product, index) => (
                                            <ListItem key={index} onClick={() => moveProductPage(product.name)} cursor={"pointer"}
                                                style={{ WebkitUserSelect: "none", MozUserSelect: "none", msUserSelect: "none", userSelect: "none" }}
                                            >
                                                {product.name}
                                            </ListItem>
                                        ))
                                    }
                                </List>
                            </AccordionPanel>
                        </AccordionItem>
                        {/* <AccordionItem maxW='15vw' _disabled={true}>
                            <AccordionButton _hover={{ backgroundColor: "white" }} bgColor={"red"} onClick={(e) => movePage(encodeURI("/developer/My Subscription"), e)}>
                                <Box as="span" flex='1' textAlign='left' w='15vw'>
                                    <Text fontSize={"xl"} fontWeight={"bold"}>My Subscription</Text>
                                </Box>
                            </AccordionButton>
                        </AccordionItem> */}
                    </Accordion>
                </Sidebar>
                <Box ml='16vw' >
                    <Box mx='10' mt='10'>
                        <Breadcrumb>
                            <BreadcrumbItem color={mainText}>
                                <BreadcrumbLink
                                    onClick={(e) => movePage(
                                        mainCategory !== "" ?
                                            mainCategory === "API Products" ?
                                                "/developer"
                                                :
                                                "/developer/" + JSON.parse(JSON.stringify(mainCategory))
                                            :
                                            "/user"
                                        , e
                                    )}
                                    color={mainText}
                                >
                                    {mainCategory}
                                </BreadcrumbLink>
                            </BreadcrumbItem>
                            {
                                secondCategory !== "" ?
                                    <BreadcrumbItem isCurrentPage={thirdCategory !== "" ? false : true} color={mainText}>
                                        <BreadcrumbLink href='#' color={mainText}>{secondCategory}</BreadcrumbLink>
                                    </BreadcrumbItem>
                                    :
                                    null
                            }
                            {
                                thirdCategory !== "" ?
                                    <BreadcrumbItem isCurrentPage color={mainText}>
                                        <BreadcrumbLink href='#' color={mainText}>{thirdCategory}</BreadcrumbLink>
                                    </BreadcrumbItem>
                                    :
                                    null
                            }
                        </Breadcrumb>
                    </Box>
                    <MainPanel setWidth='80vw' setHeigth='95vh' >
                        <Outlet />
                    </MainPanel>
                    <Footer />
                </Box>
            </Flex>
        </>
    );
}

export default Developer;