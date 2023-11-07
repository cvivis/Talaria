import React, { useEffect, useRef, useState } from "react";
import { Box, Center, Text } from "@chakra-ui/react";
import * as StompJs from "@stomp/stompjs";
import {
  Table,
  Thead,
  Tbody,
  Tfoot,
  Tr,
  Th,
  Td,
  TableCaption,
  TableContainer,
} from "@chakra-ui/react";
function Chart5() {
  const client = useRef({});
  const [data, setData] = useState([]);
  const connect = () => {
    client.current = new StompJs.Client({
      brokerURL: "ws://localhost:8080/ws/monitoring",
      onConnect: () => {
        // Do something, all subscribes must be done is this callback
        console.log("연결 SUB");
        subscribe();
      },
    });
    client.current.activate();
  };

  const disconnect = () => {
    client.current.deactivate(); // 활성화된 연결 끊기
  };

  const subscribe = () => {
    client.current.subscribe("/sub/error-count", (res) => {
      // server에게 메세지 받으면
      const json_body = JSON.parse(res.body);
      setData(json_body.slice());
      console.log(data.map((info, rank) => info.errorType));
    });
  };

  const getTextColor = (type) => {
    if (type === "EMERG") {
      return "Blue";
    } else if (type === "CRIT") {
      return "Green";
    } else if (type === "ERROR") {
      return "Red";
    } else {
      return "Orange";
    }
  };

  useEffect(() => {
    connect();
    // const interval = setInterval(() => {

    // }, 1000);

    return () => {
      //   clearInterval(interval);
      disconnect();
    };
  }, []);

  return (
    <>
      <Box bg="white" w="36vw" h="55vh" borderRadius="20px" boxShadow="lg">
        <Text fontWeight="Bold" p={5}>
          Error Count
        </Text>
        <TableContainer>
          <Table variant="simple" w="100%" h="100%" minH="50px">
            <Thead>
              <Tr>
                <Th>Error Type</Th>
                <Th isNumeric>Count</Th>
              </Tr>
            </Thead>
            <Tbody>
              {data.map((info, index) => (
                <Tr key={index}>
                  <Td color={getTextColor(info.errorType)} fontWeight="Bold" border="none">
                    {info.errorType}
                  </Td>
                  <Td isNumeric style={{ paddingRight: "13%" }} border="none">
                    <div style={{ margin: "15% auto" }}>{info.count}</div>
                  </Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        </TableContainer>
      </Box>
    </>
  );
}
export default Chart5;
