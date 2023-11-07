import AppCharts from '../components/dashboard/StatusLiveChart';
import { Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, Badge, Box, Breadcrumb, BreadcrumbItem, BreadcrumbLink, Flex, LinkBox, LinkOverlay, List, ListIcon, ListItem, Spacer, Text, useColorModeValue } from "@chakra-ui/react";
import Sidebar from "../components/sidebar/Sidebar";
import MainPanel from "../components/layouts/mainPanel/MainPanel";
import Footer from "../components/footer/Footer";
import { useEffect, useState } from "react";
import { Outlet, useLocation, useNavigate } from "react-router-dom";


function Admin() {
    let bgBoxColor = useColorModeValue('blue.500', 'navy.900');
    let mainText = useColorModeValue("white", "gray.200");
    const location = useLocation();
    

    const [mainCategory, setMainCategory] = useState("대시 보드");
    const [secondCategory, setSecondCategory] = useState("");
    const [thirdCategory, setThirdCategory] = useState("");

    useEffect(() => {
        console.log(location.pathname);
        const locationArray = decodeURI(location.pathname).split('/');
        console.log(locationArray);
        for(let i = 0; i<locationArray.length; i++) {
            if(i === 2) {
                setMainCategory(locationArray[i]);
            } else if(i === 3) {
                setSecondCategory(locationArray[i]);
            } 
        }
    },[location,mainCategory]);
    return (
        <>
             <Box h='40vh' w='100%' position='absolute' bg={bgBoxColor} top='0' zIndex="-1" />
             <Flex direction='row' >
                <Sidebar setWidth='15vw' setHeigth='95vh'>
                </Sidebar>
                <Box ml='16vw' >
                {/* <AppCharts></AppCharts> */}
                <Box mx='10' mt='10'>
                        <Breadcrumb>
                            <BreadcrumbItem color={mainText}>
                                <BreadcrumbLink 
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

export default Admin;