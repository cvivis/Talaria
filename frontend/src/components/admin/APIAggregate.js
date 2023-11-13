import { Box, Text } from '@chakra-ui/react';
import React from 'react';
import { Flex, Spacer, HStack,Grid } from '@chakra-ui/react'
import Chart1 from "../aggregate/chart1";
import Chart2 from "../aggregate/chart2";
import Chart3 from "../aggregate/chart3";
import Chart4 from "../aggregate/chart4";
import Chart5 from "../aggregate/chart5";
import Chart6 from "../aggregate/chart6";
const Aggregate = () => {
    return (
        <Box borderRadius="20px" mt={2}>
            <Flex gap={10} pr={5} pt={5}>
                <Chart1></Chart1>
                <Chart2></Chart2>
            </Flex>
            <Flex gap={10} pr={5} pt={5}>
                <Chart3></Chart3>
                <Chart4></Chart4>
            </Flex>
            <Flex gap={10} pr={5} pt={5}>
                <Chart5></Chart5>
                <Chart6></Chart6>
            </Flex>
        </Box>
    );
};

export default Aggregate;