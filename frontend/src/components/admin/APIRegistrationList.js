import {
    Button, Table, ButtonGroup,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableContainer,
    Popover, PopoverTrigger, PopoverContent, PopoverArrow, PopoverCloseButton, PopoverHeader, PopoverBody
} from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';

const APIRegistrationList = () => {

    const [response, setResponse] = useState([]);

    useEffect(() => {
        setResponse([
            {
                "dept_name" : "shinhan",
                "group_name" :"account",
                "user_id" : "mineezm9@gmail.com",
                "domain" : "https://h-uick.com",
                "purpose" :"차용증 작성 프로젝트에서 송금 자동화를 구현하는데 사용",
                "date" : "2023/10/23 17:09:23"
            },
            {
                "dept_name" : "shinhan",
                "group_name" :"loaning",
                "user_id" : "ljss98@gmail.com",
                "domain" : "http://localhost:8080",
                "purpose" :"대출을 비교하는 서비스에서 사용",
                "date" : "2023/11/10 17:09:23"
            },
            {
                "dept_name" : "shinhan",
                "group_name" :"exchange",
                "user_id" : "bbaeggome2@gmail.com",
                "domain" : "https://102.2.6.3.4",
                "purpose" :"서비스에서 환율을 표기할때 사용",
                "date" : "2023/09/10 06:08:09 "
            }
        ]);
    }, []);
    

    const handleApproved = (index) => {
        console.log(response[index].group_name + "승인");
        // 여기에 해당 신청 승인하는 요청이 들어가야함
        const newData = [...response];
        newData.splice(index, 1);
        setResponse(newData);
      };
    
    const handleDeclined = (index) => {
        console.log(response[index].group_name + "거절");
        // 여기에 해당 신청 거절하는 요청이 들어가야함
        const newData = [...response];
        newData.splice(index, 1);
        setResponse(newData);
    };

    const truncateText = (text, maxLength) => {
        return text.length > maxLength ? `${text.slice(0, maxLength)}...` : text;
    };

    return (
        <>
            <TableContainer marginLeft="5%" marginRight="5%">
                <Table variant='simple'>
                    <Thead>
                        <Tr>
                            <Th fontSize="xs">Dept Name</Th>
                            <Th fontSize="xs">Group Name</Th>
                            <Th fontSize="xs">User</Th>
                            <Th fontSize="xs">Domain</Th>
                            <Th fontSize="xs">Purpose</Th>
                            <Th fontSize="xs">Process</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                    {
                        response.map((item, idx) => (
                            <Tr key={idx}>
                                <Td fontSize="xs">{item.dept_name}</Td>
                                <Td fontSize="xs">{item.group_name}</Td>
                                <Td fontSize="xs">{item.user_id}</Td>
                                <Td fontSize="xs">{item.domain}</Td>
                                <Td fontSize="xs">{truncateText(item.purpose, 10)}
                                    {item.purpose.length > 10 && (
                                    <Popover>
                                        <PopoverTrigger>
                                            <Button variant='ghost' size='xs' colorScheme='gray'>More</Button>
                                        </PopoverTrigger>
                                        <PopoverContent>
                                            <PopoverArrow />
                                            <PopoverCloseButton />
                                            <PopoverHeader>Purpose</PopoverHeader>
                                            <PopoverBody>{item.purpose}</PopoverBody>
                                            </PopoverContent>
                                    </Popover>
                                    )}</Td>
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
        </>
    );
}

export default APIRegistrationList;

// const styles = StyleSheet.create({

// });