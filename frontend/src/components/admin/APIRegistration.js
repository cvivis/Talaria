import {
    Button,
    Table,
    ButtonGroup,
    Thead,
    Tbody,
    Tr,
    Th,
    Td,
    TableContainer,
    Box
  } from "@chakra-ui/react";
  import React, { useEffect, useState } from "react";
  import APIEditModal from "./APIEditModal";
  import CustomAxios from "../axios/CustomAxios";
  import { useSelector } from "react-redux";
  
  const APIRegistration = () => {
    const [response, setResponse] = useState([]);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [apis, setApis] = useState({});
    const [index, setIndex] = useState(0);
    const access_token = useSelector((state) => state.userInfo.access_token);
  
    useEffect(() => {
      try {
        CustomAxios.get(`apis/admin?status=pending`, {
          headers: { Authorization: `Bearer ${access_token}` },
        }).then((res) => {
          setResponse(res.data);
        });
      } catch (error) {}
    }, []);
  
    const handleApproved = (idx, id) => {
      try {
        CustomAxios.patch(
          `apis/admin`,
          {
            apis_id: id,
            status: "APPROVED_ON",
          },
          {
            headers: { Authorization: `Bearer ${access_token}` },
          }
        ).then((res) => {
          openEditModal(idx);
        });
      } catch (error) {}
    };
  
    const handleDeclined = (index, id) => {
      try {
        CustomAxios.patch(
          `apis/admin`,
          {
            apis_id: id,
            status: "REJECTED",
          },
          {
            headers: { Authorization: `Bearer ${access_token}` },
          }
        ).then((res) => {
          const newData = [...response];
          newData.splice(index, 1);
          setResponse(newData);
        });
      } catch (error) {}
    };
  
    const openEditModal = (idx) => {
      setIndex(idx);
      setApis(response[idx]);
      setIsEditModalOpen(true);
    };
  
    const closeEditModal = (result) => {
      if (result === true) {
        const newData = [...response];
        newData.splice(index, 1);
        setResponse(newData);
      }
      setIsEditModalOpen(false);
    };

    const handleAccept = (updatedApiInfo) => {
      setIsEditModalOpen(false);
    };
  
    return (
      <Box bgColor='white' borderRadius={20} height="100%" minHeight="100%">
        <TableContainer marginLeft="5%" marginRight="5%">
          <Table variant="simple">
            <Thead>
              <Tr>
                <Th fontSize="xs">Dept Name</Th>
                <Th fontSize="xs">Group Name</Th>
                <Th fontSize="xs">Web Server URL</Th>
                <Th fontSize="xs">Process</Th>
              </Tr>
            </Thead>
            <Tbody>
              {response.map((item, idx) => (
                <Tr key={idx}>
                  <Td fontSize="xs">{item.developer_email}</Td>
                  <Td fontSize="xs">{item.name}</Td>
                  <Td fontSize="xs">{item.web_server_url}</Td>
                  <Td fontSize="xs">
                    <ButtonGroup variant="outline" spacing="3" size="xs">
                      <Button
                        colorScheme="blue"
                        onClick={() => handleApproved(idx, item.apis_id)}
                      >
                        APPROVED
                      </Button>
                      <Button
                        colorScheme="red"
                        onClick={() => handleDeclined(idx, item.apis_id)}
                      >
                        DECLINED
                      </Button>
                    </ButtonGroup>
                  </Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        </TableContainer>
        <APIEditModal
          isOpen={isEditModalOpen}
          onClose={closeEditModal}
          apiInfo={apis}
          onAccept={handleAccept}
        />
        </Box>
    );
  };
  
  export default APIRegistration;
  