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
  Switch,
  Menu,
  MenuButton,
  MenuList,
  MenuItem,
  IconButton,
} from "@chakra-ui/react";
import { HamburgerIcon, EditIcon, DeleteIcon } from "@chakra-ui/icons";
import React, { useEffect, useState } from "react";
import APIEditModal from "./APIEditModal";

const APIManagementList = () => {
  const [response, setResponse] = useState([]);
  const [isEditModalOpen, setIsEditModalOpen] = useState(false);
  const [apis, setApis] = useState({});

  useEffect(() => {
    setResponse([
      {
        id: 1,
        dept_name: "shinhan",
        group_name: "account",
        quota: 40,
        status: "APPROVED_ON",
        routing_url: "https://talaria.ssafy.com/bank/v1/account-holder/create",
        white_list: ["1.1.1.1/1", "1.1.1.1/1"]
      },
      {
        id: 2,
        dept_name: "shinhan",
        group_name: "loaning",
        quota: 50,
        status: "APPROVED_ON",
        routing_url: "https://talaria.ssafy.com/bank/v1/loaning",
        white_list: ["2.2.2.2/2", "2.2.2.2/2"]
      },
      {
        id: 3,
        dept_name: "shinhan",
        group_name: "exchange",
        quota: 60,
        status: "APPROVED_OFF",
        routing_url: "https://talaria.ssafy.com/bank/v1/exchange",
        white_list: ["3.3.3.3/3", "3.3.3.3/3"]
      },
    ]);

    // const modifiedData = response.map((item) => {
    //     // 예: 데이터의 특정 속성을 수정
    //     if(item.status === "APPROVED_ON")
    //         item.status = true;
    //     else
    //         item.status = false;
    //     return item;
    //   });
    // setResponse(modifiedData);
  }, []);

  const handleSwitchChange = (idx) => {
    const newResponse = [...response];
    newResponse[idx].status =
      newResponse[idx].status === "APPROVED_ON"
        ? "APPROVED_OFF"
        : "APPROVED_ON";
    setResponse(newResponse);
    if (response[idx].status === "APPROVED_ON") {
      // patch 요청
    } else {
      // patch 요청
    }
    console.log(idx, " : ", response[idx].status);
  };

  const handleEdit = (idx) => {
    console.log(idx, "handleEdit");
    
    setApis(response[idx]);
    setIsEditModalOpen(true);
    // 편집하는 팝업 띄우기
  };

  const handleDelete = (idx) => {
    console.log(idx, "handleDelete");
    // 삭제 요청 보내기
  };

  const closeEditModal = () => {
    setIsEditModalOpen(false);
  };

  return (
    <>
      <TableContainer marginLeft="5%" marginRight="5%">
        <Table variant="simple">
          <Thead>
            <Tr>
              <Th fontSize="xs">Dept Name</Th>
              <Th fontSize="xs">Group Name</Th>
              <Th fontSize="xs">Quota Per Second (QPS)</Th>
              <Th fontSize="xs">Available</Th>
              <Th fontSize="xs"></Th>
            </Tr>
          </Thead>
          <Tbody>
            {response.map((item, idx) => (
              <Tr key={idx}>
                <Td fontSize="xs">{item.dept_name}</Td>
                <Td fontSize="xs">{item.group_name}</Td>
                <Td fontSize="xs">{item.quota}</Td>
                <Td>
                  <Switch
                    onChange={() => handleSwitchChange(idx)}
                    isChecked={item.status === "APPROVED_ON"}
                  ></Switch>
                </Td>
                <Td>
                  <Menu>
                    <MenuButton
                      as={IconButton}
                      aria-label="Options"
                      icon={<HamburgerIcon />}
                      variant="outline"
                    />
                    <MenuList>
                      <MenuItem
                        icon={<EditIcon />}
                        onClick={() => handleEdit(idx)}
                      >
                        Edit
                      </MenuItem>
                      <MenuItem
                        icon={<DeleteIcon />}
                        onClick={() => handleDelete(idx)}
                      >
                        Delete
                      </MenuItem>
                    </MenuList>
                  </Menu>
                </Td>
              </Tr>
            ))}
          </Tbody>
        </Table>
      </TableContainer>
      <APIEditModal isOpen={isEditModalOpen} onClose={closeEditModal} apiInfo={apis}/>
    </>
  );
};

export default APIManagementList;

// const styles = StyleSheet.create({

// });
