import {
  Button,
  FormControl,
  FormLabel,
  Input,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  Select,
} from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import CustomAxios from "../axios/CustomAxios";
import { useSelector } from "react-redux";

const MemberCreateModal = ({ isOpen, onClose, onCreate }) => {
  const initialRef = React.useRef(null);
  const finalRef = React.useRef(null);
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [role, setRole] = useState("");
  const [keyExpirationDate, setKeyExpirationDate] = useState();
  const access_token = useSelector((state) => state.userInfo.access_token);

  const today = new Date();
  const year = today.getFullYear();
  const month = String(today.getMonth() + 1).padStart(2, "0"); // 월은 0부터 시작하므로 1을 더하고, 두 자리로 포맷팅
  const day = String(today.getDate()).padStart(2, "0"); // 날짜를 두 자리로 포맷팅
  const formattedDate = `${year}-${month}-${day}`;

  const [newUser, setNewUser] = useState({
    member_id: "",
    email: "",
    password: "",
    key: "",
    role: "ADMIN",
    key_created_date: "",
    key_expiration_date: formattedDate,
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNewUser ((prevItem) => ({
      ...prevItem,
      [name]: value,
    }));
  };

  const handleCreate = () => {
    try {
      CustomAxios.post(
        `members/admin/signup`,
        {
          email: newUser.email,
          password: newUser.password,
          key_expiration_date: newUser.key_expiration_date,
          role: newUser.role,
        },
        {
          headers: { Authorization: `Bearer ${access_token}` },
        }
      ).then((res) => {
        onCreate(res.data);
      });
    } catch (error) {}
  };

  useEffect(() => {

  }, []);

  return (
    <>
      <Modal
        size="2xl"
        initialFocusRef={initialRef}
        finalFocusRef={finalRef}
        isOpen={isOpen}
        onClose={onClose}
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Create Member</ModalHeader>
          {/* <ModalCloseButton /> */}
          <ModalBody mt={4}>
            <FormControl isRequired>
              <FormLabel>Member Email</FormLabel>
              <Input
                name="email"
                value={newUser.email}
                onChange={handleInputChange}
              />
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Password</FormLabel>
              <Input
                name="password"
                value={newUser.password}
                onChange={handleInputChange}
              />
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Role</FormLabel>
              <Select
                  name="role"
                  value={newUser.role}
                  onChange={handleInputChange}
                  isRequired
                >
                  <option value="ADMIN">Administrator</option>
                  <option value="DEVELOPER">Developer</option>
                  <option value="USER">User</option>
                </Select>
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Key Expiration Date</FormLabel>
              <Input
                  size="sm"
                  type="date"
                  name="key_expiration_date"
                  value={newUser.key_expiration_date}
                  onChange={handleInputChange}
                  isRequired
                />
            </FormControl>
          </ModalBody>

          <ModalFooter>
            <Button
              colorScheme="blue"
              mr={3}
              onClick={handleCreate}
            >
              Create
            </Button>
            <Button onClick={() => onClose(false)}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default MemberCreateModal;
