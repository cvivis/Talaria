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
  Select
} from "@chakra-ui/react";
import React, { useEffect, useState } from "react";

const UserManagementList = () => {
  const [users, setUsers] = useState([]);
  const [selectedOption, setSelectedOption] = useState("");

  useEffect(() => {
    setUsers([
      {
        user_id: 1,
        email: "abc@gmail.com",
        password: "abc123",
        key: "ait3Sf8mbTS2AZ0bKrB7EOt2fBZKAc4w",
        role: "ADMIN",
        created_time: "2023-11-07",
        expiration_time: "2024-11-07",
      },
      {
        user_id: 2,
        email: "def@gmail.com",
        password: "def123",
        key: "berkljglkdfgjklfdkjgEOt2wehfskdf",
        role: "DEVELOPER",
        created_time: "2023-10-01",
        expiration_time: "2024-10-01",
      },
      {
        user_id: 3,
        email: "ghi@gmail.com",
        password: "ghi123",
        key: "cerjgidfgjkeriudnfxcjkvlkdjlgdfj",
        role: "USER",
        created_time: "2023-12-25",
        expiration_time: "2024-12-25",
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

  const [newItem, setNewItem] = useState({
    user_id: "",
    email: "",
    password: "",
    key: "",
    role: "",
    created_time: "",
    expiration_time: "",
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
    console.log("New Item:", newItem);

    setUsers((prevArr) => [...prevArr, { ...newItem }]);
    // 새로운 항목 추가 후 입력란 초기화
    setNewItem({
      id: "",
      email: "",
      password: "",
      key: "",
      role: "",
      created_time: "",
      due_time: "",
    });
  };

  const handleDeleteClick = () => {
    const deletedItems = users.filter((_, index) =>
      selectedRows.includes(index)
    );
    console.log("Deleted Items:", deletedItems);

    setUsers((prevArr) =>
      prevArr.filter((_, index) => !selectedRows.includes(index))
    );
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

  const handleSelectChange = (event) => {
    // 선택한 옵션의 값을 가져와서 상태를 업데이트합니다.
    setSelectedOption(event.target.value);
    console.log(selectedOption);
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
                  <Button>Reissue</Button>
                </Td>
                <Td>{item.role}</Td>
                <Td>{item.created_time}</Td>
                <Td>{item.due_time}</Td>
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
                <Select value={selectedOption} onChange={handleSelectChange} isRequired>
                  <option value="ADMIN">Administrator</option>
                  <option value="DEVELOPER">Developer</option>
                  <option value="USER">User</option>
                </Select>
              </Td>
              <Td>
                <Input
                  size="sm"
                  type="datetime-local"
                  name="created_time"
                  value={newItem.created_time}
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
