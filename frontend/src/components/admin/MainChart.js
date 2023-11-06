import { Box, Text } from '@chakra-ui/react';
import React from 'react';
import { Flex, Spacer, HStack,Grid } from '@chakra-ui/react'
import Chart1 from "../dashboard/chart1";
import Chart2 from "../dashboard/chart2";
import Chart3 from "../dashboard/chart3";
import Chart4 from "../dashboard/chart4";
import Chart5 from "../dashboard/chart5";
import Chart6 from "../dashboard/chart6";

const MainChart = () => {
    return (
        <Box bgColor={"white"}>
            <Text>메인 대시보드</Text>
            <Flex gap={10} mt={5}>
                <Chart1></Chart1>
                <Chart2></Chart2>
                <Chart3></Chart3>
            </Flex>
            <Flex gap={5} mt={5}>
                <Chart4></Chart4>
                <Chart5></Chart5>
                <Chart6></Chart6>
            </Flex>
        </Box>
    );
};

export default MainChart;