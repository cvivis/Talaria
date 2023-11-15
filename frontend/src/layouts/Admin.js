import AppCharts from '../components/dashboard/StatusLiveChart';
import { Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, Badge, Box, Breadcrumb, BreadcrumbItem, BreadcrumbLink, Flex, LinkBox, LinkOverlay, List, ListIcon, ListItem, Spacer, Text, useColorModeValue, AlertDialog, AlertDialogOverlay, AlertDialogContent, AlertDialogHeader, AlertDialogBody, AlertDialogFooter, Button, useDisclosure } from "@chakra-ui/react";
import Sidebar from "../components/sidebar/Sidebar";
import MainPanel from "../components/layouts/mainPanel/MainPanel";
import Footer from "../components/footer/Footer";
import { useEffect, useRef, useState } from "react";
import { useDispatch } from "react-redux";
import { logoutUser } from "../components/slices/UserInfoSlice";
import { Outlet, useLocation, useNavigate } from "react-router-dom";
import { ReactComponent as LogoutIcon } from '../assets/svg/Logout.svg';

function Admin() {
    let bgBoxColor = useColorModeValue('navy.900', 'navy.900');
    let mainText = useColorModeValue("gray.200", "gray.200");
    const location = useLocation();
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const { isOpen, onOpen, onClose } = useDisclosure();
    const cancelRef = useRef();

    const [mainCategory, setMainCategory] = useState("대시 보드");

    const movePage = (route,e) => {
        e.preventDefault();
        return navigate(decodeURI(route));
    }

    const logOut = () => {
        dispatch(logoutUser());
        onClose();
        return navigate("/");
    }

    useEffect(() => {
        if(location.pathname.includes('apiManagement')) {
            setMainCategory("API Management")
        } else if(location.pathname.includes('apiRegistration')) {
            setMainCategory("API Registration")
        } else if(location.pathname.includes('apiSubscription')) {
            setMainCategory("API Subscription")
        } else if(location.pathname.includes('memberManagement')) {
            setMainCategory("Member Management")
        } else {
            setMainCategory("Administrator")
        }
    },[location]);
    return (
        <>
             <Box h='40vh' w='100%' position='absolute' bg={bgBoxColor} top='0' zIndex="-1" />
             <Flex direction='row' >
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
                                boxShadow={mainCategory === "API Management" ? "0px 5px 14px rgba(0, 0, 0, 0.05)" : ""}
                                color={mainCategory === "API Management" ? "black" : "gray.400"}
                            >
                                <Box as="span" flex='1' textAlign='left' w='15vw' onClick={(e) => movePage(encodeURI("/admin/apiManagement"),e)}>
                                <Text fontSize={"lg"} fontWeight={"bold"}>API Management</Text>
                                </Box>
                            </AccordionButton>
                        </AccordionItem>
                        <AccordionItem maxW='13vw'>
                            <AccordionButton 
                                _hover={{backgroundColor: "white"}}
                                _expanded={{bg:"white"}} borderRadius={10} 
                                boxShadow={mainCategory === "API Registration" ? "0px 5px 14px rgba(0, 0, 0, 0.05)" : ""}
                                color={mainCategory === "API Registration" ? "black" : "gray.400"}
                            >
                                <Box as="span" flex='1' textAlign='left' w='15vw' onClick={(e) => movePage(encodeURI("/admin/apiRegistration"),e)}>
                                <Text fontSize={"lg"} fontWeight={"bold"}>API Registration</Text>
                                </Box>
                            </AccordionButton>
                        </AccordionItem>
                        <AccordionItem maxW='13vw'>
                            <AccordionButton 
                                _hover={{backgroundColor: "white"}}
                                _expanded={{bg:"white"}} borderRadius={10} 
                                boxShadow={mainCategory === "API Subscription" ? "0px 5px 14px rgba(0, 0, 0, 0.05)" : ""}
                                color={mainCategory === "API Subscription" ? "black" : "gray.400"}
                            >
                                <Box as="span" flex='1' textAlign='left' w='15vw' onClick={(e) => movePage(encodeURI("/admin/apiSubscription"),e)}>
                                <Text fontSize={"lg"} fontWeight={"bold"}>API Subscription</Text>
                                </Box>
                            </AccordionButton>
                        </AccordionItem>
                        <AccordionItem maxW='13vw'>
                            <AccordionButton 
                                _hover={{backgroundColor: "white"}}
                                _expanded={{bg:"white"}} borderRadius={10} 
                                boxShadow={mainCategory === "Member Management" ? "0px 5px 14px rgba(0, 0, 0, 0.05)" : ""}
                                color={mainCategory === "Member Management" ? "black" : "gray.400"}
                            >
                                <Box as="span" flex='1' textAlign='left' w='15vw' onClick={(e) => movePage(encodeURI("/admin/memberManagement"),e)}>
                                <Text fontSize={"sm"} fontWeight={"bold"}>Member Management</Text>
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
                {/* <AppCharts></AppCharts> */}
                <Box mx='10' mt='10'>
                        <Breadcrumb>
                            <BreadcrumbItem color={mainText}>
                                <BreadcrumbLink 
                                    color={mainText}
                                >
                                    <Text fontSize='2xl' fontWeight={"bold"} marginBottom='10%'>
                                    {mainCategory}</Text>
                                </BreadcrumbLink>
                            </BreadcrumbItem>
                        </Breadcrumb>
                    </Box>

                <MainPanel setWidth='75vw' setHeigth='95vh' >
                        <Outlet />
                    </MainPanel>
                    <Footer />
                </Box>
            </Flex>
               
        </>
    );
}

export default Admin;