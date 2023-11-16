import { Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, AlertDialog, AlertDialogBody, AlertDialogContent, AlertDialogFooter, AlertDialogHeader, AlertDialogOverlay, Box, Breadcrumb, BreadcrumbItem, BreadcrumbLink, Button, Flex, ListItem, Text, UnorderedList, useColorModeValue, useDisclosure } from "@chakra-ui/react";
import Sidebar from "../components/sidebar/Sidebar";
import MainPanel from "../components/layouts/mainPanel/MainPanel";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { useEffect, useRef, useState } from "react";
import { useDispatch } from "react-redux";
import { logoutUser } from "../components/slices/UserInfoSlice";
import { ReactComponent as LogoutIcon } from '../assets/svg/Logout.svg';
import instance from "../components/axios/CustomAxios";

function User() {

	let bgBoxColor = useColorModeValue('blue.500', 'navy.900');
    let mainText = useColorModeValue("white", "gray.200");

    const [mainCategory, setMainCategory] = useState("API Products");
    const [secondCategory, setSecondCategory] = useState("");
    const [thirdCategory, setThirdCategory] = useState("");
    const [products,setProducts] = useState([]);
    const location = useLocation();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { isOpen, onOpen, onClose } = useDisclosure();
    const cancelRef = useRef();

    function moveProductPage(productName) {
        return navigate("/user/API Products/"+productName);
    };

    const movePage = (route,e) => {
        e.preventDefault();
        return navigate(decodeURI(route));
    }

    const logOut = () => {
        dispatch(logoutUser());
        onClose();
        return navigate("/");
    }

    const GetProducts = async() => {
        try {
            const data = await instance.get("apis/user",{
                params: {
                    status: "approved_on",
                }
            });
            setProducts(data.data);
        } catch(error) {
            alert(error);
        }
    }

    useEffect(() => {
        setMainCategory("API Products");
        setSecondCategory("");
        setThirdCategory("");

        const locationArray = decodeURI(location.pathname).split('/');
        for(let i = 0; i<locationArray.length; i++) {
            if(i === 2) {
                setMainCategory(locationArray[i]);
            } else if(i === 3) {
                setSecondCategory(locationArray[i]);
            } else if(i === 4) {
                setThirdCategory(locationArray[i]);
            }
        }

        GetProducts();

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
                        <AccordionItem maxW='13vw'>
                            <AccordionButton 
                                _hover={{backgroundColor: "white"}}
                                _expanded={{bg:"white"}} borderRadius={10}
                                boxShadow={mainCategory === "API Products" ? "0px 5px 14px rgba(0, 0, 0, 0.05)" : ""}
                                color={mainCategory === "API Products" ? "black" : "gray.400"}
                            >
                                <Box as="span" flex='1' textAlign='left' onClick={(e) => movePage("/user",e)} w='15vw'>
                                <Text fontSize={"lg"} fontWeight={"bold"} >API Products</Text>
                                </Box>
                                <AccordionIcon/>
                            </AccordionButton>
                            <AccordionPanel pb={4} ml={4}>
                                <UnorderedList spacing={3}>
                                    {
                                        products.map((product,index) => (
                                            <ListItem key={index} onClick={() => moveProductPage(product.name)} cursor={"pointer"}
                                                style={{WebkitUserSelect:"none",MozUserSelect:"none",msUserSelect:"none",userSelect:"none"}}
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
                                _expanded={{bg:"white"}} borderRadius={10} 
                                boxShadow={mainCategory === "My Subscription" ? "0px 5px 14px rgba(0, 0, 0, 0.05)" : ""}
                                color={mainCategory === "My Subscription" ? "black" : "gray.400"}
                            >
                                <Box as="span" flex='1' textAlign='left' w='15vw' onClick={(e) => movePage(encodeURI("/user/My Subscription"),e)}>
                                <Text fontSize={"lg"} fontWeight={"bold"}>My Subscription</Text>
                                </Box>
                            </AccordionButton>
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
                                                "/user" 
                                                : 
                                                "/user/"+JSON.parse(JSON.stringify(mainCategory))
                                            : 
                                            "/user"
                                        ,e
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
                </Box>
            </Flex>
        </>
    );
}

export default User;