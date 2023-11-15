import {
  Flex,
  Button,
  Table,
  Checkbox,
  Input,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Select,
  Menu, MenuButton, MenuItem, MenuList, IconButton
} from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import { HamburgerIcon, DeleteIcon, RepeatIcon } from "@chakra-ui/icons";
import CustomAxios from "../axios/CustomAxios";
import { useSelector } from "react-redux";
import MemberCreateModal from "./MemberCreateModal";

const MemberManagement = () => {
  const [users, setUsers] = useState([]);
  const access_token = useSelector((state) => state.userInfo.access_token);
  const [isCreateModalOpen, setIsCreateModalOpen] = useState(false);

  useEffect(() => {
    try {
      CustomAxios.get(`members/admin`, {
        headers: { Authorization: `Bearer ${access_token}` },
      }).then((res) => {
        setUsers(res.data);
      });
    } catch (error) {}
  }, []);

  const handleCreate = (newUser) => {
    setUsers((prevArr) => [...prevArr, newUser]);
    setIsCreateModalOpen(false);
  };

  const handleDelete = (index, id) => {
    try {
      CustomAxios.delete(`members/admin/${id}`, {
        headers: { Authorization: `Bearer ${access_token}` },
      }).then((res) => {
        const newUsers = [...users];
        newUsers.splice(index, 1);
        setUsers(newUsers);
      });
    } catch (error) {}
  };

  const handleReissue = async (index) => {
    try {
      const response = await CustomAxios.post(
        `keys/admin`,
        {
          key_id: users[index].key_id,
        },
        {
          headers: { Authorization: `Bearer ${access_token}` },
        }
      );

      setUsers((prevUsers) => {
        const updatedUsers = [...prevUsers];
        updatedUsers[index].key = response.data.key;
        updatedUsers[index].key_created_date = response.data.key_created_date;
        updatedUsers[index].key_expiration_date =
          response.data.key_expiration_date;
        return updatedUsers;
      });
    } catch (error) {}
  };

  const openCreateModal = () => {
    setIsCreateModalOpen(true);
  }

  const closeCreateModal = () => {
    setIsCreateModalOpen(false);
  };

  return (
    <>
      <TableContainer paddingTop="3%" marginLeft="5%" marginRight="5%">
        <Table variant="simple">
          <Thead>
            <Tr>
              <Th fontSize="xs">Email</Th>
              <Th fontSize="xs">PW</Th>
              <Th fontSize="xs">Key</Th>
              <Th fontSize="xs">Role</Th>
              <Th fontSize="xs">Created</Th>
              <Th fontSize="xs">Expire</Th>
              <Th fontSize="xs"><Button size='sm' colorScheme='facebook' onClick={openCreateModal}>
                    Create
                  </Button></Th>
            </Tr>
          </Thead>
          <Tbody>
            {users.map((item, index) => (
              <Tr key={index}>
                <Td fontSize="xs">{item.email}</Td>
                <Td fontSize="xs">{item.password}</Td>
                <Td fontSize="xs">{item.key}</Td>
                <Td fontSize="xs">{item.role}</Td>
                <Td fontSize="xs">{item.key_created_date}</Td>
                <Td fontSize="xs">{item.key_expiration_date}</Td>
                <Td>
                  <Menu>
                    <MenuButton
                      as={IconButton}
                      aria-label="Optionss"
                      icon={<HamburgerIcon />}
                      variant="outline"
                    />
                    <MenuList>
                      <MenuItem
                        icon={<RepeatIcon />}
                        onClick={() => handleReissue(index)}
                      >
                        Reissue Key 
                      </MenuItem>
                      <MenuItem
                        icon={<DeleteIcon />}
                        onClick={() => handleDelete(index, item.member_id)}
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
      <MemberCreateModal
        isOpen={isCreateModalOpen}
        onClose={closeCreateModal}
        onCreate={handleCreate}
      />
    </>
  );
};

export default MemberManagement;
