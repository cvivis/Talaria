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
  NumberInput,
  NumberInputField,
  NumberInputStepper,
  NumberIncrementStepper,
  NumberDecrementStepper,
  Tag,
  HStack,
  CloseButton,
  InputGroup,
  InputRightElement,
  TagLabel,
} from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import CustomAxios from "../axios/CustomAxios";
import { useSelector } from "react-redux";

const APIEditModal = ({ isOpen, onClose, apiInfo, onAccept }) => {
  const initialRef = React.useRef(null);
  const finalRef = React.useRef(null);
  const [tags, setTags] = useState([]);
  const [inputValue, setInputValue] = useState("");
  const [error, setError] = useState(false);
  const access_token = useSelector((state) => state.userInfo.access_token);
  const [quota, setQuota] = useState();

  const isValidCIDR = (input) => {
    // CIDR IP 주소 형식을 검사하는 정규 표현식
    const cidrPattern =
      /^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\/(3[0-2]|[12]?[0-9])$/;
    return cidrPattern.test(input);
  };

  const addTag = () => {
    if (isValidCIDR(inputValue)) {
      setTags([...tags, inputValue]);
      setInputValue("");
      setError(false);
    } else {
      setError(true);
    }
  };

  const removeTag = (idx) => {
    const updatedTags = [...tags];
    updatedTags.splice(idx, 1);
    setTags(updatedTags);
  };

  const handleAccept = (id) => {
    try {
      CustomAxios.patch(
        `apis/admin`,
        {
          apis_id: id,
          quota: quota,
          white_list: tags,
        },
        {
          headers: { Authorization: `Bearer ${access_token}` },
        }
      ).then((res) => {
        const updatedApiInfo = {
          apis_id: id,
          developer_email: apiInfo.developer_email,
          name: apiInfo.name,
          routing_url: apiInfo.routing_url,
          quota: quota,
          status: apiInfo.status,
          white_list: tags,
        };

        onAccept(updatedApiInfo);
      });
    } catch (error) {}
  };

  const handleChangeQuota = (value) => {
    setQuota(value);
  };

  useEffect(() => {
    if (apiInfo.white_list) {
      setTags([...apiInfo.white_list]);
    }
    setQuota(apiInfo.quota);
  }, [apiInfo]);

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
          <ModalHeader>Policy Settings</ModalHeader>
          {/* <ModalCloseButton /> */}
          <ModalBody mt={4}>
            <FormControl isRequired>
              <FormLabel>Dept Name</FormLabel>
              <Input
                ref={initialRef}
                value={apiInfo.developer_email}
                isDisabled
              />
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Group Name</FormLabel>
              <Input value={apiInfo.name} isDisabled/>
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Routing URL</FormLabel>
              <Input defaultValue={apiInfo.routing_url} isDisabled/>
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Quota</FormLabel>
              <NumberInput
                value={quota}
                onChange={handleChangeQuota}
                min={0}
                precision={0}
                step={1}
                isRequired
              >
                <NumberInputField />
                <NumberInputStepper>
                  <NumberIncrementStepper />
                  <NumberDecrementStepper />
                </NumberInputStepper>
              </NumberInput>
            </FormControl>
            <>
              <FormControl mt={4} isRequired>
                <FormLabel>White List</FormLabel>
                <InputGroup>
                  <Input
                    type="text"
                    placeholder="Write Tag (e.g. 127.0.0.1/0)"
                    value={inputValue}
                    onChange={(e) => setInputValue(e.target.value)}
                    isInvalid={error}
                    onKeyPress={(e) => {
                      if (e.key === "Enter") {
                        addTag();
                      }
                    }}
                  />
                  <InputRightElement width="4.5rem">
                    <Button h="1.75rem" size="sm" onClick={addTag}>
                      Add
                    </Button>
                  </InputRightElement>
                </InputGroup>
                {/* <HStack marginTop="2%" spacing={2}> */}
                <div style={{ display: "flex", flexWrap: "wrap" }}>
                  {tags.map((tag, index) => (
                    <Tag
                      key={index}
                      size="md"
                      borderRadius="full"
                      variant="solid"
                      colorScheme="blue"
                      marginTop="1%"
                      marginRight="1%"
                    >
                      <TagLabel>{tag}</TagLabel>
                      <CloseButton size="sm" onClick={() => removeTag(index)} />
                    </Tag>
                  ))}
                  {/* </HStack> */}
                </div>
              </FormControl>
            </>
          </ModalBody>

          <ModalFooter>
            <Button
              colorScheme="blue"
              mr={3}
              onClick={() => handleAccept(apiInfo.apis_id)}
            >
              Accept
            </Button>
            <Button onClick={() => onClose(false)}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default APIEditModal;
