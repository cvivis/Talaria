import {
    Accordion,
    AccordionButton,
    AccordionIcon,
    AccordionItem,
    AccordionPanel,
    AlertDialog, AlertDialogBody, AlertDialogContent, AlertDialogFooter, AlertDialogHeader, AlertDialogOverlay,
    Box,
    Breadcrumb,
    BreadcrumbItem,
    BreadcrumbLink,
    Button,
    Flex,
    ListItem,
    Text,
    UnorderedList,
    useColorModeValue,
    useDisclosure
} from "@chakra-ui/react";
import Footer from "../components/footer/Footer";
import MainPanel from "../components/layouts/mainPanel/MainPanel";
import Sidebar from "../components/sidebar/Sidebar";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState, useRef } from "react";
import { useDispatch } from "react-redux";
import { logoutUser } from "../components/slices/UserInfoSlice";
import { ReactComponent as LogoutIcon } from '../assets/svg/Logout.svg';
import CustomAxios from '../components/axios/CustomAxios';
import { useSelector } from 'react-redux';

function Developer() {

    let bgBoxColor = useColorModeValue('blue.100', 'navy.900');
    let mainText = useColorModeValue("black", "gray.200");

    const [mainCategory, setMainCategory] = useState("API Products");
    const [secondCategory, setSecondCategory] = useState("");
    const [thirdCategory, setThirdCategory] = useState("");
    const [products, setProducts] = useState([]);

    const location = useLocation();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { isOpen, onOpen, onClose } = useDisclosure();
    const cancelRef = useRef();
    const access_token = useSelector(state => state.userInfo.access_token)

    function moveProductPage(productName) {
        return navigate("/developer/API Products/" + productName);
    };

    const movePage = (route, e) => {
        e.preventDefault();
        return navigate(decodeURI(route));
    }

    const logOut = () => {
        dispatch(logoutUser());
        onClose();
        return navigate("/");
    }

    useEffect(() => {
        setMainCategory("API Products");
        setSecondCategory("");
        setThirdCategory("");

        console.log(location.pathname);
        const locationArray = decodeURI(location.pathname).split('/');
        console.log(locationArray);
        for (let i = 0; i < locationArray.length; i++) {
            if (i === 2) {
                setMainCategory(locationArray[i]);
            } else if (i === 3) {
                setSecondCategory(locationArray[i]);
            } else if (i === 4) {
                setThirdCategory(locationArray[i]);
            }
        }

        const fetchData = async () => {
            try {
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

    }, [location, mainCategory]);


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
                        <AccordionItem maxW='13vw'>
                            <AccordionButton 
                                _hover={{ backgroundColor: "white" }} 
                                _expanded={{ bg: "white" }} borderRadius={10}
                                boxShadow={mainCategory === "API Products" ? "0px 5px 14px rgba(0, 0, 0, 0.05)" : ""}
                                color={mainCategory === "API Products" ? "black" : "gray.400"}
                            >
                                <Box as="span" flex='1' textAlign='left' onClick={(e) => movePage("/developer", e)} w='15vw'>
                                    <Text fontSize={"lg"} fontWeight={"bold"}>API Products</Text>
                                </Box>
                                <AccordionIcon />
                            </AccordionButton>
                            <AccordionPanel pb={4} ml={4}>
                                <UnorderedList spacing={3}>
                                    {
                                        [...products].reverse().map((product, index) => (
                                            <ListItem key={index} onClick={() => moveProductPage(product.name)} cursor={"pointer"}
                                                style={{ WebkitUserSelect: "none", MozUserSelect: "none", msUserSelect: "none", userSelect: "none" }}
                                                color={secondCategory === product.name ? "black" : "gray.400"}
                                            >
                                                {product.name}
                                            </ListItem>
                                        ))
                                    }
                                </UnorderedList>
                            </AccordionPanel>
                        </AccordionItem>
                        
                        <AccordionItem maxW='13vw'>
                            <AccordionButton 
                                _hover={{backgroundColor: "white"}}
                                color="black"
                            >
                                <Box as="span" flex='1' textAlign='left' w='15vw' onClick={() => onOpen()}>
                                    <Text fontSize={"lg"} fontWeight={"bold"}>LogOut</Text>
                                </Box>
                                <LogoutIcon/>
                            </AccordionButton>
                        </AccordionItem>

                    </Accordion>
                </Sidebar>
                
                <AlertDialog
                    isOpen={isOpen}
                    leastDestructiveRef={cancelRef}
                    onClose={onClose}
                >
                    <AlertDialogOverlay>
                    <AlertDialogContent>
                        <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                        Confirm LogOut
                        </AlertDialogHeader>

                        <AlertDialogBody>
                        Are you sure you want to log out?
                        </AlertDialogBody>

                        <AlertDialogFooter>
                        <Button ref={cancelRef} onClick={onClose}>
                            Cancel
                        </Button>
                        <Button colorScheme='red' onClick={() => {logOut()}} ml={3}>
                            LogOut
                        </Button>
                        </AlertDialogFooter>
                    </AlertDialogContent>
                    </AlertDialogOverlay>
                </AlertDialog>

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
                                            "/developer"
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