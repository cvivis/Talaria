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
    const apisInfo = location.state?.product || 'DefaultProductName';
    const access_token = useSelector(state => state.userInfo.access_token)
    console.log(`asdf`, apisInfo)
    const [isInformationModalOpen, setIsInformationModalOpen] = useState(false);
    const [isModalOpen, setIsModalOpen] = useState(false);

    let params = useParams(); // params 통해서 조회 API 돌릴 예정
    const mainText = useColorModeValue("white", "gray.200");

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
            console.log(`get`, textareaValue)
            const response = await CustomAxios.post(
                `apis/developer/oas/` + apisInfo.apis_id,
                {
                    swagger_content: textareaValue,
                },
                {
                    headers: { Authorization: `Bearer ${access_token}` },
                }
            );

            console.log(response);
        } catch (error) {
            console.error('Error:', error);
        }
        onClose();
    }

    useEffect(() => {
        // const editor = SwaggerEditor({
        //     dom_id: '#swagger-editor',
        //     layout: 'EditorLayout',
        //     swagger2GeneratorUrl: 'https://generator.swagger.io/api/swagger.json',
        //     oas3GeneratorUrl: 'https://generator3.swagger.io/openapi.json',
        //     swagger2ConverterUrl: 'https://converter.swagger.io/api/convert',
        // });

        const fetchData = async () => {
            try {
                console.log(`access_token`, access_token)
                CustomAxios.get(
                    `apis/developer/` + apisInfo.apis_id,
                    {
                        headers: { Authorization: `Bearer ${access_token}` },
                    }
                )
                    .then((res) => {
                        console.log(`oas`, res);
                        console.log(`set`, res.data.swagger_content);

                        setTextareaValue(JSON.stringify(res.data.swagger_content, null, 4))
                    })
            } catch (error) {
                console.error('Error:', error);
            }

        }

        fetchData();

    }, []);

    return (
        <>
            <Flex mb={2}>
                <Text fontSize="2xl" color={mainText} m={0}>{params.productName}</Text>
                <Spacer />
                <Box m={3}>
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