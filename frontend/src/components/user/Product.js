import { Badge, Box, Button, Flex, FormControl, FormLabel, Input, Modal, ModalBody, ModalCloseButton, ModalContent, ModalFooter, ModalHeader, ModalOverlay, Spacer, Text, Textarea, useColorModeValue, useDisclosure, useToast } from '@chakra-ui/react';
import React, { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import SwaggerUI from 'swagger-ui-react';
import "swagger-ui-react/swagger-ui.css";
import apiSpec from "../../assets/json/openapi.json";
import instance from '../axios/CustomAxios';
import { useSelector } from 'react-redux';
import Footer from '../footer/Footer';

const Product = () => {

    const params = useParams();
    const mainText = useColorModeValue("white", "gray.200");
    const [subscription, setSubscription] = useState({status: "subscribe"});
    const [product, setProduct] = useState({});
    const [purpose, setPurpose] = useState("");
    const [toUse, setToUse] = useState("");
    const toast = useToast();
    const memberId = useSelector(state => state.userInfo.member_id);

    let handlePurposeChange = (e) => {
        let value = e.target.value
        setPurpose(value);
    }

    let handleToUseChange = (e) => {
        let value = e.target.value
        setToUse(value);
    }
    
    const GetProduct = async(apisName) => {
        try{
            const data = await instance.get('apis/user/product',{
                params: {
                    apisName: apisName.productName,
                }
            });
            setProduct(data.data);
            GetSubscription(data.data.apis_id);
        } catch(error) {
            alert(error);
        }
    };

    const GetSubscription = async(apisId) => {
        try {
            const data = await instance.get('subscriptions/user',{
                params: {
                    apisId: apisId,
                }
            });
            setSubscription(data.data);
        } catch(error) {
            alert(error);
        }
    };

    const ApplySubscription = async() => {

        if(purpose === "") {
            toast({
                title:"ERROR",
                description:"Please write down the purpose !",
                position:"top",
                status:"error",
                variant:"subtle",
                isClosable:"true",
            })
            return ;
        } else if(toUse === "") {
            toast({
                title:"ERROR",
                description:"Please write down where to use it !",
                position:"top",
                status:"error",
                variant:"subtle",
                isClosable:"true",
            })
            return ;
        }

        const data = {
            member_id: memberId,
            apis_id: product.apis_id,
            content: purpose,
            address: toUse,
        };
        
        try {
            await instance.post("subscription/user/apply",data);
            console.log(data);

            setPurpose("");
            setToUse("");
            
            toast({
                title:"SUCCESS",
                description:"Success to apply subscription !",
                position:"top",
                status:"success",
                variant:"subtle",
                colorScheme:"blue",
                isClosable:"true",
            })

            onClose();
        } catch {
            toast({
                title:"ERROR",
                description:"Please Retry !",
                position:"top",
                status:"error",
                variant:"subtle",
                isClosable:"true",
                duration:"7000"
            })
            onClose();
        }
        
    }

    const { isOpen, onOpen, onClose } = useDisclosure();

    const initialRef = useRef(null);
    const finalRef = useRef(null);

    useEffect(() => {
        GetProduct(params);
    },[params,subscription]);

    return (
        <>
            <Flex mb={2}>
                <Text fontSize="2xl" color={mainText} m={0}>{params.productName}</Text>
                <Spacer />
                <Box mr={3}>
                    {
                        {
                            REJECTED:
                                <Badge 
                                    variant='solid' 
                                    colorScheme='pink' 
                                    fontSize='25px' 
                                    borderRadius='5' 
                                    onClick={() => {onOpen()}}
                                    cursor={"pointer"}
                                >
                                    REJECTED
                                </Badge>,
                            PENDING:<Badge variant='solid' colorScheme='yellow' fontSize='25px' borderRadius='5' cursor={"default"}>PENDING</Badge>,
                            SUBSCRIBING:<Badge variant='solid' colorScheme='green' fontSize='25px' borderRadius='5' cursor={"default"}>SUBSCRIBING</Badge>,
                            SUBSCRIBE:
                                <Badge
                                    variant='solid' 
                                    colorScheme='cyan' 
                                    fontSize='25px' 
                                    borderRadius='5' 
                                    onClick={() => {onOpen()}}
                                    cursor={"pointer"}
                                >
                                    SUBSCRIBE
                                </Badge>,
                        }[subscription]
                    }
                </Box>
            </Flex>
            <Box bgColor='white' borderRadius={20} p='1px'>
                <Box>
                    <SwaggerUI spec={apiSpec} />
                    {/* <SwaggerUI spec={product.swaggerContent} /> */}
                </Box>
            </Box>
            <Modal
                initialFocusRef={initialRef}
                finalFocusRef={finalRef}
                isOpen={isOpen}
                onClose={onClose}
            >
                <ModalOverlay />
                <ModalContent>
                <ModalHeader>Apply for subscription</ModalHeader>
                <ModalCloseButton />
                <ModalBody pb={6}>
                    <FormControl>
                    <FormLabel>Purpose of use</FormLabel>
                    <Textarea size="md" isRequired ref={initialRef} placeholder='Purpose of use' onChange={(e) => {handlePurposeChange(e)}} />
                    </FormControl>

                    <FormControl mt={4}>
                    <FormLabel>Where to use it</FormLabel>
                    <Input placeholder='Where to use it' isRequired onChange={(e) => {handleToUseChange(e)}}/>
                    </FormControl>
                </ModalBody>

                <ModalFooter>
                    <Button colorScheme='blue' mr={3} onClick={() => {ApplySubscription()}}>
                    Apply
                    </Button>
                    <Button onClick={onClose}>Cancel</Button>
                </ModalFooter>
                </ModalContent>
            </Modal>
            <Footer />
        </>
    );
};

export default Product;