import {
    AlertDialog, AlertDialogBody, AlertDialogContent, AlertDialogFooter, AlertDialogHeader, AlertDialogOverlay,
    Button, Box, Flex, Spacer, Text, Textarea,
    useColorModeValue,
    useDisclosure
} from '@chakra-ui/react';
import React, { useEffect, useState, useRef } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import InformationAPIs from '../modal/InformationAPIs';
import CustomAxios from '../axios/CustomAxios';
import { useSelector } from 'react-redux';

const Product = () => {

    const location = useLocation();
    const [apisInfo, setApisInfo] = useState(location.state?.product || 'DefaultProductName');
    const access_token = useSelector(state => state.userInfo.access_token)
    const [isInformationModalOpen, setIsInformationModalOpen] = useState(false);
    const { apiId } = useParams();
    const [isModalOpen, setIsModalOpen] = useState(false);
    const [productName, setProductName] = useState('');
    let params = useParams(); // params 통해서 조회 API 돌릴 예정
    const mainText = useColorModeValue("black", "gray.200");

    const [textareaValue, setTextareaValue] = React.useState('');
    const { isOpen, onOpen, onClose } = useDisclosure();
    const cancelRef = useRef();

    const openInformationModal = () => {
        setIsInformationModalOpen(true);
    };

    const closeInformationModal = () => {
        setIsInformationModalOpen(false);
    };

    const openModal = () => {
        setIsModalOpen(true);
    };

    const closeModal = () => {
        setIsModalOpen(false);
    };

    const RegisterOas = async () => {
        try {
            const response = await CustomAxios.post(
                `apis/developer/oas/` + apisInfo.apis_id,
                {
                    swagger_content: textareaValue,
                },
                {
                    headers: { Authorization: `Bearer ${access_token}` },
                }
            );
        } catch (error) {
            console.error('Error:', error);
        }
        onClose();
    }

    const fetchData = async () => {
        try {
            CustomAxios.get(
                `apis/developer/` + apisInfo.apis_id,
                {
                    headers: { Authorization: `Bearer ${access_token}` },
                }
            )
                .then((res) => {
                    setTextareaValue(JSON.stringify(res.data.swagger_content, null, 4))
                    
                })
        } catch (error) {
            console.error('Error:', error);
        }

    }

    const fetchData2 = async () => {
        try {
            console.log(apiId,'apiId')
            CustomAxios.get(
                `apis/developer/` + apiId,
                {
                    headers: { Authorization: `Bearer ${access_token}` },
                }
            )
                .then((res) => {
                    setTextareaValue(JSON.stringify(res.data.swagger_content, null, 4))
                    setProductName(res.data.name)
                })
        } catch (error) {
            console.error('Error:', error);
        }

    }

    // useEffect(() => {
    //     setApisInfo(location.state?.product || 'DefaultProductName')
    //     fetchData();
    //     console.log('apisInfo', apisInfo)
    // }, [apiId]);

    // useEffect(() => {
    //     console.log('useEffect')
    //     fetchData2();
    // }, []);

    useEffect(() => {
        console.log('useEffect')
        fetchData2();
        console.log('name', productName)
    }, [apiId]);

    return (
        <>
            <Flex mb={2}>
                <Text fontSize="2xl" color={mainText} m={1}>{productName}</Text>
                <Spacer />
                <Box m={1}>
                    <Button variant='solid' colorScheme='pink' fontSize='25px' borderRadius='5'
                        onClick={openInformationModal} mr={3}
                    >INFO</Button>
                    <Button variant='solid' colorScheme='pink' fontSize='25px' borderRadius='5'
                        onClick={() => onOpen()}
                    >UPDATE</Button>
                    {/* <Button variant='solid' colorScheme='pink' fontSize='25px' borderRadius='5'
                        onClick={openModal}
                    >EDIT</Button> */}
                </Box>
            </Flex>
            <Box bgColor='white' borderRadius={20} display="flex" alignItems="center" justifyContent="center">
                {/* <div id='swagger-editor'></div> */}
                <Textarea
                    value={textareaValue}
                    onChange={(e) => setTextareaValue(e.target.value)}
                    placeholder='Insert JSON'
                    size='lg'
                    resize="none"
                    h="500px"
                    m={4}
                    w="94%"
                ></Textarea>
            </Box>

            <InformationAPIs
                isOpen={isInformationModalOpen}
                onClose={closeInformationModal}
                closeModal={closeInformationModal}
                apisInfo={apisInfo}
            >
            </InformationAPIs>

            <AlertDialog
                    isOpen={isOpen}
                    leastDestructiveRef={cancelRef}
                    onClose={onClose}
                >
                    <AlertDialogOverlay>
                    <AlertDialogContent>
                        <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                        Confirm Update
                        </AlertDialogHeader>

                        <AlertDialogBody>
                        Are you sure you want to update?
                        </AlertDialogBody>

                        <AlertDialogFooter>
                        <Button ref={cancelRef} onClick={onClose}>
                            Cancel
                        </Button>
                        <Button colorScheme='red' onClick={RegisterOas} ml={3}>
                            Update
                        </Button>
                        </AlertDialogFooter>
                    </AlertDialogContent>
                    </AlertDialogOverlay>
                </AlertDialog>
        </>
    );
};

export default Product;