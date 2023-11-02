import { Box, Button, Flex, FormControl, FormLabel, HStack, Icon, Image, Input, Link, Switch, Text, useColorModeValue } from "@chakra-ui/react";
import Footer from "../components/footer/Footer";
import image from "../assets/img/BasicImage.png";
import logo from "../assets/img/Talaria-logo-light.png";

function SignIn() {

    const textColor = useColorModeValue("gray.400", "white");
    const bgForm = useColorModeValue("white", "navy.800");
    const titleColor = useColorModeValue("gray.700", "blue.500");

    return (
        <>
            <Box w='100%'>
                <Flex
                    direction='column'
                    alignSelf='center'
                    justifySelf='center'
                    overflow='hidden'
                >
                    <Box
                        position='absolute'
                        minH={{ base: '70vh', md: '50vh' }}
                        maxH={{ base: '70vh', md: '50vh' }}
                        w={{ md: 'calc(100vw - 50px)' }}
                        maxW={{ md: 'calc(100vw - 50px)' }}
                        left='0'
                        right='0'
                        bgRepeat='no-repeat'
                        overflow='hidden'
                        zIndex='-1'
                        top='0'
                        bgImage={image}
                        bgSize='cover'
                        mx={{ md: 'auto' }}
                        mt={{ md: '14px' }}
                        borderRadius={{ base: '0px', md: '20px' }}
                    >
                        <Box w='100vw' h='100vh' bg='blue.500' opacity='0.8'></Box>
                    </Box>
                    <Flex
                        direction='column'
                        textAlign='center'
                        justifyContent='center'
                        align='center'
                        mt='125px'
                        // mb='30px'
                    >
                        <Image boxSize='100px'
                            objectFit='cover'
                            src={logo}
                            alt='Talaria Logo'
                        />
                        <Text fontSize='4xl' color='white' fontWeight='bold'>
                            Talaria
                        </Text>
                        {/* <Text
                            fontSize='md'
                            color='white'
                            fontWeight='normal'
                            mt='10px'
                            mb='26px'
                            w={{ base: '90%', sm: '60%', lg: '40%', xl: '333px' }}
                        >
                            탈라리아에 오신것을 환영합니다 !
                        </Text> */}
                    </Flex>
                    <Flex
                        w="100%"
                        h="100%"
                        alignItems="center"
                        justifyContent="center"
                        mb="60px"
                        mt={{ base: "10px", md: "20px" }}
                    >
                        <Flex
                        zIndex="2"
                        direction="column"
                        w="445px"
                        background="transparent"
                        borderRadius="15px"
                        p="40px"
                        mx={{ base: "100px" }}
                        mb={{ base: "20px", md: "auto" }}
                        bg={bgForm}
                        boxShadow={useColorModeValue(
                            "0px 5px 14px rgba(0, 0, 0, 0.05)",
                            "unset"
                        )}
                        >
                        <FormControl>
                            {/* <Text
                                fontSize='md'
                                color='black'
                                fontWeight='normal'
                                mt='10px'
                                mb='26px'
                                w={{ base: '90%', sm: '60%', lg: '40%', xl: '333px' }}
                            >
                                탈라리아에 오신것을 환영합니다 !
                            </Text> */}
                            <FormLabel ms="4px" fontSize="sm" fontWeight="normal">
                            Id
                            </FormLabel>
                            <Input
                            variant="auth"
                            fontSize="sm"
                            ms="4px"
                            type="text"
                            placeholder="Your Id"
                            mb="24px"
                            size="lg"
                            />
                            <FormLabel ms="4px" fontSize="sm" fontWeight="normal">
                            Password
                            </FormLabel>
                            <Input
                            variant="auth"
                            fontSize="sm"
                            ms="4px"
                            type="password"
                            placeholder="Your password"
                            mb="24px"
                            size="lg"
                            />
                            <Button
                            fontSize="10px"
                            variant="dark"
                            fontWeight="bold"
                            w="100%"
                            h="45"
                            mb="24px"
                            >
                            SIGN IN
                            </Button>
                        </FormControl>
                        <Flex
                            flexDirection="column"
                            justifyContent="center"
                            alignItems="center"
                            maxW="100%"
                            mt="0px"
                        >
                            <Text color={textColor} fontWeight="medium">
                            Do you want a account? {" "}
                            <Link
                                color={titleColor}
                                as="span"
                                ms="5px"
                                href="#"
                                fontWeight="bold"
                            >
                                Account registration
                            </Link>
                            </Text>
                        </Flex>
                        </Flex>
                    </Flex>
                </Flex>
                <Box px='24px' mx='auto' width='1044px' maxW='100%'>
                    <Footer />
                </Box>
            </Box>
        </>
    );
}

export default SignIn;