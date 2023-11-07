import React, { useEffect, useRef, useState } from 'react';
import { Box,Text } from "@chakra-ui/react";
import * as StompJs from '@stomp/stompjs';
import {Table,Thead,Tbody,Tfoot,Tr,Th,Td,TableCaption,TableContainer,} from '@chakra-ui/react'
function Chart4() {
    const client = useRef({}); 
    const [data,setData] = useState([]);
    const connect = () =>{
        client.current = new StompJs.Client({
            brokerURL: 'ws://localhost:8080/ws/monitoring',
            onConnect:() =>{
               // Do something, all subscribes must be done is this callback
              console.log("연결 SUB");
                subscribe();
            },
        });
        client.current.activate();
    }
  
    const disconnect = () => {
        client.current.deactivate(); // 활성화된 연결 끊기 
    };
  
    const subscribe = () => {
      client.current.subscribe('/sub/usage-ranking', (res) => { // server에게 메세지 받으면
        // console.log(res)
        const json_body = JSON.parse(res.body);
        setData(json_body.data.slice());
        // console.log(data.map((info,rank)=>info.url));
        });
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
        <Box bg='white' w='40vw' h='55vh' borderRadius="20px"  boxShadow="lg">
            <Text fontWeight='Bold' p={5} >API TOP5</Text>
            <TableContainer>
            <Table variant='simple' w="100%" h="100%" minH="50px">
                <Thead>
                <Tr>
                    <Th>Ranking</Th>
                    <Th>URL</Th>
                    <Th>METHOD</Th>
                    <Th isNumeric>USAGE</Th>
                </Tr>
                </Thead>
                <Tbody>
                    {data.map((info,rank)=>(
                    <Tr key={rank}>
                        <Td>{info.ranking}</Td>
                        <Td>{info.url}</Td>
                        <Td>{info.method}</Td>
                        <Td isNumeric>{info.usage}</Td>
                    </Tr>
                    ))} 
                </Tbody>
            </Table>
            </TableContainer>
        </Box>
        </>
    );
}
export default Chart4;