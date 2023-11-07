import {
    Flex, Button, Table, Checkbox, Input,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableContainer,
} from '@chakra-ui/react';
import React, { useState } from 'react';

const KeyManage = () => {
    const [arr, setArr] = useState([
        {
            id: 0,
            email: 'member1',
            password: 'password1',
            key: 'key1',
            role: 'User',
            created_time: '2023-11-03 14:30:00',
            due_time: '2023-12-03 14:30:00'
        },
        {
            id: 1,
            email: 'member2',
            password: 'password3',
            key: 'key2',
            role: 'Developer',
            created_time: '2023-11-04 14:30:00',
            due_time: '2023-12-04 14:30:00'
        },
        {
            id: 2,
            email: 'member3',
            password: 'password3',
            key: 'key3',
            role: 'Admin',
            created_time: '2023-11-05 14:30:00',
            due_time: '2023-12-05 14:30:00'
        },
    ]);

    const [newItem, setNewItem] = useState({
        id: '',
        email: '',
        password: '',
        key: '',
        role: '',
        created_time: '',
        due_time: '',
    });

    const [selectedRows, setSelectedRows] = useState([]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setNewItem((prevItem) => ({
            ...prevItem,
            [name]: value,
        }));
    };

    const handleCreateClick = () => {
        console.log('New Item:', newItem);

        setArr((prevArr) => [...prevArr, { ...newItem }]);
        // 새로운 항목 추가 후 입력란 초기화
        setNewItem({
            id: '',
            email: '',
            password: '',
            key: '',
            role: '',
            created_time: '',
            due_time: '',
        });
    };

    const handleDeleteClick = () => {
        const deletedItems = arr.filter((_, index) => selectedRows.includes(index));
        console.log('Deleted Items:', deletedItems);
      
        setArr((prevArr) => prevArr.filter((_, index) => !selectedRows.includes(index)));
        setSelectedRows([]);
      };

    const handleCheckboxChange = (index) => {
        setSelectedRows((prevSelectedRows) => {
            if (prevSelectedRows.includes(index)) {
                return prevSelectedRows.filter((rowIndex) => rowIndex !== index);
            } else {
                return [...prevSelectedRows, index];
            }
        });
    };

    return (
        <>
            <Flex marginY={10}>
                <Button ml="auto" marginRight="5%" colorScheme='blue'
                    onClick={handleDeleteClick}>
                    Delete
                </Button>
            </Flex>
            <TableContainer marginLeft="5%" marginRight="5%">
                <Table variant='simple'>
                    <Thead>
                        <Tr>
                            <Th fontSize="2xl"></Th>
                            <Th fontSize="2xl">Email</Th>
                            <Th fontSize="2xl">PW</Th>
                            <Th fontSize="2xl">Key</Th>
                            <Th fontSize="2xl">Reissue</Th>
                            <Th fontSize="2xl">Role</Th>
                            <Th fontSize="2xl">Created</Th>
                            <Th fontSize="2xl">Expire</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                        {arr.map((item, index) => (
                            <Tr key={index}>
                                <Td>
                                    <Checkbox
                                        isChecked={selectedRows.includes(index)}
                                        onChange={() => handleCheckboxChange(index)}
                                    /></Td>
                                <Td>{item.email}</Td>
                                <Td>{item.password}</Td>
                                <Td>{item.key}</Td>
                                <Td>
                                    <Button>Reissue</Button>
                                </Td>
                                <Td>{item.role}</Td>
                                <Td>{item.created_time}</Td>
                                <Td>{item.due_time}</Td>
                            </Tr>
                        ))}
                        <Tr>
                            <Td></Td>
                            <Td colSpan={2}>ID<Input size='sm' name='email' value={newItem.email} onChange={handleInputChange} /></Td>
                            <Td colSpan={2}>PW<Input size='sm' name='password' value={newItem.password} onChange={handleInputChange} /></Td>
                            <Td>Role<Input size='sm' name='role' value={newItem.role} onChange={handleInputChange} /></Td>
                            <Td>
                                <Input
                                    size="sm"
                                    type="datetime-local"
                                    name='created_time'
                                    value={newItem.created_time}
                                    onChange={handleInputChange}
                                />
                            </Td>
                            <Td>
                                <Button onClick={handleCreateClick}>Create</Button>
                            </Td>
                        </Tr>
                    </Tbody>
                </Table>
            </TableContainer>
        </>
    );
};

export default KeyManage;