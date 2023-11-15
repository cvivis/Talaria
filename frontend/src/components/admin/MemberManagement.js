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
} from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import CustomAxios from "../axios/CustomAxios";
import { useSelector } from "react-redux";

const MemberManagement = () => {
  const [users, setUsers] = useState([]);
  const access_token = useSelector((state) => state.userInfo.access_token);

  const today = new Date();
  const year = today.getFullYear() + 1;
  const month = String(today.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 1을 더하고, 두 자리로 포맷팅
  const day = String(today.getDate()).padStart(2, "0"); // 날짜를 두 자리로 포맷팅
  const formattedDate = `${year}-${month}-${day}`;

  useEffect(() => {
    try {
      CustomAxios.get(`members/admin`, {
        headers: { Authorization: `Bearer ${access_token}` },
      }).then((res) => {
        setUsers(res.data);
      });
    } catch (error) {}
  }, []);

  const [newItem, setNewItem] = useState({
    member_id: "",
    email: "",
    password: "",
    key: "",
    role: "ADMIN",
    key_created_date: "",
    key_expiration_date: formattedDate,
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
    try {
      // async setRequest()

      CustomAxios.post(
        `members/admin/signup`,
        {
          email: newItem.email,
          password: newItem.password,
          key_expiration_date: newItem.key_expiration_date,
          role: newItem.role,
        },
        {
          headers: { Authorization: `Bearer ${access_token}` },
        }
      ).then((res) => {
        setUsers((prevArr) => [...prevArr, { ...res.data }]);

        setNewItem({
          member_id: "",
          email: "",
          password: "",
          key: "",
          role: "ADMIN",
          key_created_date: "",
          key_expiration_date: formattedDate,
        });
      });
    } catch (error) {}
  };

  const handleDeleteClick = () => {
    const deletedItems = users.filter((_, index) =>
      selectedRows.includes(index)
    );

    const deletedMemberIds = deletedItems.map((item) => item.member_id);

    try {
      CustomAxios.post(
        `members/admin`,
        {
          member_ids: deletedMemberIds,
        },
        {
          headers: { Authorization: `Bearer ${access_token}` },
        }
      ).then((res) => {
        setUsers((prevArr) =>
          prevArr.filter((_, index) => !selectedRows.includes(index))
        );
        setSelectedRows([]);
      });
    } catch (error) {}
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

  const handleReissueClick = async (index) => {
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

  return (
    <>
      <Flex marginY={10}>
        <Button
          mr="auto"
          marginTop="3%"
          marginLeft="5%"
          colorScheme="facebook"
          onClick={handleDeleteClick}
        >
          Delete
        </Button>
      </Flex>
      <TableContainer marginLeft="5%" marginRight="5%">
        <Table variant="simple">
          <Thead>
            <Tr>
              <Th fontSize="xs"></Th>
              <Th fontSize="xs">Email</Th>
              <Th fontSize="xs">PW</Th>
              <Th fontSize="xs">Key</Th>
              <Th fontSize="xs">Reissue</Th>
              <Th fontSize="xs">Role</Th>
              <Th fontSize="xs">Created</Th>
              <Th fontSize="xs">Expire</Th>
            </Tr>
          </Thead>
          <Tbody>
            {users.map((item, index) => (
              <Tr key={index}>
                <Td fontSize="xs">
                  <Checkbox
                    isChecked={selectedRows.includes(index)}
                    onChange={() => handleCheckboxChange(index)}
                  />
                </Td>
                <Td fontSize="xs">{item.email}</Td>
                <Td fontSize="xs">{item.password}</Td>
                <Td fontSize="xs">{item.key}</Td>
                <Td fontSize="xs">
                  <Button size='xs' colorScheme='facebook' onClick={() => handleReissueClick(index)}>
                    Reissue
                  </Button>
                </Td>
                <Td fontSize="xs">{item.role}</Td>
                <Td fontSize="xs">{item.key_created_date}</Td>
                <Td fontSize="xs">{item.key_expiration_date}</Td>
              </Tr>
            ))}
            <Tr>
              <Td fontSize="xs"></Td>
              <Td fontSize="xs">
                <Input
                  size="sm"
                  name="email"
                  value={newItem.email}
                  onChange={handleInputChange}
                  isRequired
                />
              </Td>
              <Td fontSize="xs">
                <Input
                  size="sm"
                  name="password"
                  value={newItem.password}
                  onChange={handleInputChange}
                  isRequired
                />
              </Td>
              <Td fontSize="xs"></Td>
              <Td fontSize="xs">
                <Select
                  name="role"
                  value={newItem.role}
                  onChange={handleInputChange}
                  isRequired
                >
                  <option value="ADMIN">Administrator</option>
                  <option value="DEVELOPER">Developer</option>
                  <option value="USER">User</option>
                </Select>
              </Td>
              <Td fontSize="xs">
                <Input
                  size="sm"
                  type="date"
                  name="key_expiration_date"
                  value={newItem.key_expiration_date}
                  onChange={handleInputChange}
                  isRequired
                />
              </Td>
              <Td fontSize="xs">
                <Button onClick={handleCreateClick}>Create</Button>
              </Td>
            </Tr>
          </Tbody>
        </Table>
      </TableContainer>
    </>
  );
};

export default MemberManagement;
