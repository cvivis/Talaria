import {
    Box, Flex, Select, Button, Table,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableContainer,
} from '@chakra-ui/react';
import CreateAPIs from '../modal/CreateAPIs';
import InformationAPIs from '../modal/InformationAPIs';
import React, { useState } from 'react';

const APIList = () => {

    const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);
    const [isInfoModalOpen, setIsInfoModalOpen] = useState(false);

    const openCreateModal = () => {
        setIsCreateModalOpen(true);
    };

    const closeCreateModal = () => {
        setIsCreateModalOpen(false);
    };

    const openInfoModal = () => {
        setIsInfoModalOpen(true);
    };

    const closeInfoModal = () => {
        setIsInfoModalOpen(false);
    };

    return (
        <>
            <Flex justify="space-between" align="center" marginY={10}>
                <Box marginLeft="5%">
                    <Select placeholder='Select option'>
                        <option value='option1'>Option 1</option>
                        <option value='option2'>Option 2</option>
                        <option value='option3'>Option 3</option>
                    </Select>
                </Box>
                <Button colorScheme='blue' variant='ghost' marginLeft="4" size='xs'>Base URL : http://www.wehee.com</Button>
                <Button marginLeft="auto" marginRight="3" colorScheme='blue'
                    onClick={openInfoModal}>Information</Button>
                <Button marginLeft="3" marginRight="5%" colorScheme='blue'
                    onClick={openCreateModal}>Create</Button>
            </Flex>
            <TableContainer marginLeft="5%" marginRight="5%">
                <Table variant='simple'>
                    <Thead>
                        <Tr>
                            <Th fontSize="2xl">Name</Th>
                            <Th fontSize="2xl">URL</Th>
                            <Th fontSize="2xl">METHOD</Th>
                            <Th fontSize="2xl">HEALTH</Th>
                            <Th fontSize="2xl">STATUS</Th>
                        </Tr>
                    </Thead>
                    <Tbody>
                        <Tr>
                            <Td>API1</Td>
                            <Td>/v1/users</Td>
                            <Td>GET</Td>
                            <Td>CHECK</Td>
                            <Td>APPROVED</Td>
                        </Tr>
                        <Tr>
                            <Td>API2</Td>
                            <Td>/test</Td>
                            <Td>POST</Td>
                            <Td>CHECK</Td>
                            <Td>APPROVED</Td>
                        </Tr>
                        <Tr>
                            <Td>API3</Td>
                            <Td>/delete</Td>
                            <Td>DELETE</Td>
                            <Td>CHECK</Td>
                            <Td>PENDING</Td>
                        </Tr>
                    </Tbody>
                </Table>
            </TableContainer>

            <InformationAPIs isOpen={isInfoModalOpen} onClose={closeInfoModal} closeModal={closeInfoModal}></InformationAPIs>
            <CreateAPIs isOpen={isCreateModalOpen} onClose={closeCreateModal}></CreateAPIs>
        </>
    );
}

export default APIList;