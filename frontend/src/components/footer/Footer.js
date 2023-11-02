import React from 'react';
import { Flex, Link, List, ListItem, Text } from "@chakra-ui/react";

const Footer = () => {
    return (
        <Flex
            flexDirection={{
                base: "column",
                xl: "row",
            }}
            alignItems={{
                base: "center",
                xl: "start",
            }}
            justifyContent='space-between'
            px='30px'
            pb='20px'>
            <Text
                color='gray.400'
                textAlign={{
                base: "center",
                xl: "start",
                }}
                mb={{ base: "20px", xl: "0px" }}>
                &copy; {1900 + new Date().getYear()},{" "}
                <Text as='span'>
                    Made with ❤️ by {" "}
                </Text>
                <Link
                color='blue.400'
                href='https://www.notion.so/lee-jiseung/Member-8fda5075cd8245da8685aa04474faa5d?pvs=4'
                target='_blank'>
                    Team Hermes
                </Link>
            </Text>
            <List display='flex'>
                <ListItem
                me={{
                    base: "20px",
                    md: "44px",
                }}>
                <Link color='gray.400' href='#'>
                    어디로
                </Link>
                </ListItem>
                <ListItem
                me={{
                    base: "20px",
                    md: "44px",
                }}>
                <Link color='gray.400' href='#'>
                    갈지는
                </Link>
                </ListItem>
                <ListItem
                me={{
                    base: "20px",
                    md: "44px",
                }}>
                <Link
                    color='gray.400'
                    href='#'>
                    아직
                </Link>
                </ListItem>
                <ListItem>
                <Link
                    color='gray.400'
                    href='#'>
                    안정했지롱
                </Link>
                </ListItem>
            </List>
        </Flex>
    );
};

export default Footer;