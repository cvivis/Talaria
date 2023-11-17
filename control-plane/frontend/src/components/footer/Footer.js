import React from 'react';
import { Flex, Link, List, ListItem, Text } from "@chakra-ui/react";

const Footer = () => {
    return (
        <Flex
            mt="5"
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
                <Link isExternal color='gray.400' href='https://polyester-winter-cd5.notion.site/Talaria-b3a6d015748747f3b8d6dfa5264583d4'>
                    Document
                </Link>
                </ListItem>
                <ListItem
                me={{
                    base: "20px",
                    md: "44px",
                }}>
                <Link isExternal color='gray.400' href='https://lab.ssafy.com/s09-final/S09P31A107'>
                    GitLab
                </Link>
                </ListItem>
                <ListItem
                me={{
                    base: "20px",
                    md: "44px",
                }}>
                <Link
                    isExternal
                    color='gray.400'
                    href='https://www.canva.com/design/DAF0O39yLpo/bHiDOmBjtdM5RmNLJhOxpw/view?utm_content=DAF0O39yLpo&utm_campaign=designshare&utm_medium=link&utm_source=editor'>
                    Presentation
                </Link>
                </ListItem>
                <ListItem>
                <Link
                    isExternal
                    color='gray.400'
                    href='#'>
                    Opensource License
                </Link>
                </ListItem>
            </List>
        </Flex>
    );
};

export default Footer;