import AppCharts from '../components/dashboard/StatusLiveChart';
import { UnorderedList, Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel,  Box, Breadcrumb, BreadcrumbItem, BreadcrumbLink, Flex, ListItem, Text, useColorModeValue, AlertDialog, AlertDialogOverlay, AlertDialogContent, AlertDialogHeader, AlertDialogBody, AlertDialogFooter, Button, useDisclosure } from "@chakra-ui/react";
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
    const [secondCategory, setSecondCategory] = useState("");

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
        setMainCategory("API");
        setSecondCategory("");

        const locationArray = decodeURI(location.pathname).split('/');
        for (let i = 0; i < locationArray.length; i++) {
            if (i === 2) {
                setMainCategory(locationArray[i]);
            } else if (i === 3) {
                setSecondCategory(locationArray[i]);
            }
        }
        console.log('second', mainCategory, secondCategory)

    }, [location, mainCategory]);



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
                                _hover={{ backgroundColor: "white" }}
                                _expanded={{ bg: "white" }} borderRadius={10}
                                boxShadow={mainCategory === "api" ? "0px 5px 14px rgba(0, 0, 0, 0.05)" : ""}
                                color={mainCategory === "api" ? "black" : "gray.400"}
                            >
                                <Box as="span" flex='1' textAlign='left' w='15vw' onClick={(e) => movePage(encodeURI("/admin/api/registration"), e)}>
                                    <Text fontSize={"lg"} fontWeight={"bold"}>API</Text>
                                </Box>
                                <AccordionIcon />
                            </AccordionButton>
                            <AccordionPanel pb={4} ml={4}>
                                <UnorderedList spacing={3}>
                                    <ListItem onClick={(e) => movePage(encodeURI("/admin/api/management"), e)} cursor={"pointer"}
                                        style={{ WebkitUserSelect: "none", MozUserSelect: "none", msUserSelect: "none", userSelect: "none" }}
                                        color={mainCategory === "api" && secondCategory === "management" ? "black" : "gray.400"}
                                    >
                                        MANAGEMENT
                                    </ListItem>
                                    <ListItem onClick={(e) => movePage(encodeURI("/admin/api/registration"), e)} cursor={"pointer"}
                                        style={{ WebkitUserSelect: "none", MozUserSelect: "none", msUserSelect: "none", userSelect: "none" }}
                                        color={mainCategory === "api" && secondCategory === "registration" ? "black" : "gray.400"}
                                    >
                                        REGISTRATION
                                    </ListItem>
                                    <ListItem onClick={(e) => movePage(encodeURI("/admin/api/subscription"), e)} cursor={"pointer"}
                                        style={{ WebkitUserSelect: "none", MozUserSelect: "none", msUserSelect: "none", userSelect: "none" }}
                                        color={mainCategory === "api" && secondCategory === "subscription" ? "black" : "gray.400"}
                                    >
                                        SUBSCRIPTION
                                    </ListItem>
                                </UnorderedList>
                            </AccordionPanel>
                        </AccordionItem>

                        <AccordionItem maxW='13vw'>
                            <AccordionButton
                                _hover={{ backgroundColor: "white" }}
                                _expanded={{ bg: "white" }} borderRadius={10}
                                boxShadow={mainCategory === "member" ? "0px 5px 14px rgba(0, 0, 0, 0.05)" : ""}
                                color={mainCategory === "member" ? "black" : "gray.400"}
                            >
                                <Box as="span" flex='1' textAlign='left' w='15vw' onClick={(e) => movePage(encodeURI("/admin/member/management"), e)}>
                                    <Text fontSize={"lg"} fontWeight={"bold"}>MEMBER</Text>
                                </Box>
                                <AccordionIcon />
                            </AccordionButton>
                            <AccordionPanel pb={4} ml={4}>
                                <UnorderedList spacing={3}>
                                    <ListItem onClick={(e) => movePage(encodeURI("/admin/member/management"), e)} cursor={"pointer"}
                                        style={{ WebkitUserSelect: "none", MozUserSelect: "none", msUserSelect: "none", userSelect: "none" }}
                                        color={mainCategory === "member" && secondCategory === "management" ? "black" : "gray.400"}
                                    >
                                        MANAGEMENT
                                    </ListItem>
                                </UnorderedList>
                            </AccordionPanel>
                        </AccordionItem>
                        <AccordionItem maxW='13vw'>
                            <AccordionButton
                                _hover={{ backgroundColor: "white" }}
                                color="black"
                            >
                                <Box as="span" flex='1' textAlign='left' w='15vw' onClick={() => onOpen()}>
                                    <Text fontSize={"lg"} fontWeight={"bold"}>LogOut</Text>
                                </Box>
                                <LogoutIcon />
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
                                <Button colorScheme='red' onClick={() => { logOut() }} ml={3}>
                                    LogOut
                                </Button>
                            </AlertDialogFooter>
                        </AlertDialogContent>
                    </AlertDialogOverlay>
                </AlertDialog>

                <Box ml='16vw' >
                    {/* <AppCharts></AppCharts> */}
                    <Box mx='10' mt='10' mb='4'>
                        <Breadcrumb>
                            <BreadcrumbItem color={mainText}>
                                <BreadcrumbLink
                                    color={mainText}
                                >
                                    <Text fontSize='2xl' fontWeight={"bold"}>
                                        {mainCategory.toUpperCase()}</Text>
                                </BreadcrumbLink>
                            </BreadcrumbItem>
                            {
                                secondCategory !== "" ?
                                    <BreadcrumbItem isCurrentPage={true} color={mainText}>
                                        <BreadcrumbLink  color={mainText} cursor={"pointer"} _hover={{textDecoration: "underline"}}
                                            onClick={(e) => movePage(
                                                mainCategory !== "" ?
                                                    mainCategory === "api" ?
                                                        "/admin/api/" + JSON.parse(JSON.stringify(secondCategory))
                                                        : mainCategory === "member" ?
                                                            "/admin/member/" + JSON.parse(JSON.stringify(secondCategory))
                                                            : "/admin"
                                                    : "/admin"
                                                , e
                                            )}
                                        >
                                            <Text fontSize='2xl'>
                                                {secondCategory.toUpperCase()}</Text>
                                        </BreadcrumbLink>
                                    </BreadcrumbItem>
                                    :
                                    null
                            }
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