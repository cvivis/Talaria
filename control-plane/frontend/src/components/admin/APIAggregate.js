import { Box, Text } from "@chakra-ui/react";
import React from "react";
import { Flex, Spacer, HStack, Grid } from "@chakra-ui/react";
import Chart1 from "../aggregate/chart1";
import Chart2 from "../aggregate/chart2";
import Chart3 from "../aggregate/chart3";
import Chart4 from "../aggregate/chart4";
import Chart5 from "../aggregate/chart5";
import Chart6 from "../aggregate/chart6";
const Aggregate = (props) => {
  return (
    <Box borderRadius="20px" mt={2}>
      <Flex gap={5} pr={5} pt={5}>
        {/* <Text>{props.groupName}</Text> */}
        <Chart1 groupName={props.groupName}></Chart1>
        <Chart4 groupName={props.groupName}></Chart4>
      </Flex>
      <Flex gap={5} pr={5} pt={5}>
        <Chart2 groupName={props.groupName}></Chart2>
        <Chart6 groupName={props.groupName}></Chart6>
      </Flex>
      <Flex gap={5} pr={5} pt={5}>
        <Chart3 groupName={props.groupName}></Chart3>
        <Chart5 groupName={props.groupName}></Chart5>
      </Flex>
    </Box>
  );
};

export default Aggregate;
