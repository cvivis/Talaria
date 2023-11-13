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

const UserManagementList = () => {
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
          ml="auto"
          marginRight="5%"
          colorScheme="blue"
          onClick={handleDeleteClick}
        >
          Delete
        </Button>
      </Flex>
      <TableContainer marginLeft="5%" marginRight="5%">
        <Table variant="simple">
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
            {users.map((item, index) => (
              <Tr key={index}>
                <Td>
                  <Checkbox
                    isChecked={selectedRows.includes(index)}
                    onChange={() => handleCheckboxChange(index)}
                  />
                </Td>
                <Td>{item.email}</Td>
                <Td>{item.password}</Td>
                <Td>{item.key}</Td>
                <Td>
                  <Button onClick={() => handleReissueClick(index)}>
                    Reissue
                  </Button>
                </Td>
                <Td>{item.role}</Td>
                <Td>{item.key_created_date}</Td>
                <Td>{item.key_expiration_date}</Td>
              </Tr>
            ))}
            <Tr>
              <Td></Td>
              <Td colSpan={2}>
                <Input
                  size="sm"
                  name="email"
                  value={newItem.email}
                  onChange={handleInputChange}
                  isRequired
                />
              </Td>
              <Td colSpan={2}>
                <Input
                  size="sm"
                  name="password"
                  value={newItem.password}
                  onChange={handleInputChange}
                  isRequired
                />
              </Td>
              <Td colSpan={2}></Td>
              <Td>
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
              <Td>
                <Input
                  size="sm"
                  type="date"
                  name="key_expiration_date"
                  value={newItem.key_expiration_date}
                  onChange={handleInputChange}
                  isRequired
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

export default UserManagementList;
