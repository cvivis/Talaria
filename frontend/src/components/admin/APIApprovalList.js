import {
    Button, Table, ButtonGroup,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableContainer,
} from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import APIEditModal from "./APIEditModal";

const APIApprovalList = () => {

    const [response, setResponse] = useState([]);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [apis, setApis] = useState({});
    const [index, setIndex] = useState(0);

    useEffect(() => {
        setResponse([
            {
                "dept_name" : "shinhan",
                "group_name" :"account",
                "web_server_url" : "https://h-uick.com",
                "white_list" : []
            },
            {
                "dept_name" : "shinhan",
                "group_name" :"loaning",
                "web_server_url" : "http://localhost:8080",
                "white_list" : []
            },
            {
                "dept_name" : "shinhan",
                "group_name" :"exchange",
                "web_server_url" : "https://102.2.6.3.4",
                "white_list" : []
            }
        ]);
    }, []);
    

    const handleApproved = (idx) => {
        console.log(response[idx].group_name + "승인");
        // 여기에 해당 신청 승인하는 요청이 들어가야함
        openEditModal(idx);
      };
    
    const handleDeclined = (index) => {
        console.log(response[index].group_name + "거절");
        // 여기에 해당 신청 거절하는 요청이 들어가야함
        const newData = [...response];
        newData.splice(index, 1);
        setResponse(newData);
    };

    const openEditModal = (idx) => {
        setIndex(idx);
        setApis(response[idx]);
        setIsEditModalOpen(true);
    };
    
    const closeEditModal = (result) => {

        console.log("result , ", result);

        if(result === true) {
            const newData = [...response];
            newData.splice(index, 1);
            setResponse(newData);
        }
        //
        setIsEditModalOpen(false);
    };

    return (
        <>
            <TableContainer marginLeft="5%" marginRight="5%">
                <Table variant='simple'>
                    <Thead>
                        <Tr>
                            <Th fontSize="xs">Dept Name</Th>
                            <Th fontSize="xs">Group Name</Th>
                            <Th fontSize="xs">Web Server URL</Th>
                            <Th fontSize="xs">Process</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                    {
                        response.map((item, idx) => (
                            <Tr key={idx}>
                                <Td fontSize="xs">{item.dept_name}</Td>
                                <Td fontSize="xs">{item.group_name}</Td>
                                <Td fontSize="xs">{item.web_server_url}</Td>
                                <Td fontSize="xs">
                                    <ButtonGroup variant='outline' spacing='3' size='xs'>
                                        <Button colorScheme='blue' onClick={() => handleApproved(idx)}>APPROVED</Button>
                                        <Button colorScheme='red' onClick={() => handleDeclined(idx)}>DECLINED</Button>
                                    </ButtonGroup>
                                </Td>
                            </Tr>
                        ))
                    }
                    </Tbody>
                </Table>
            </TableContainer>
            <APIEditModal isOpen={isEditModalOpen} onClose={closeEditModal} apiInfo={apis}/>
        </>
    );
}

export default APIApprovalList;

// const styles = StyleSheet.create({

// });