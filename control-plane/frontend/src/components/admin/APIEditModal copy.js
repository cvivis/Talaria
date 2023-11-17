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
  TagCloseButton 
} from "@chakra-ui/react";
import React, { useEffect, useState } from "react";

const APIEditModal = ({ isOpen, onClose, apiInfo }) => {
  const initialRef = React.useRef(null);
  const finalRef = React.useRef(null);
  const [tags, setTags] = useState([]);
  const [inputValue, setInputValue] = useState("");
  const [error, setError] = useState(false);

  const isValidCIDR = (input) => {
    // CIDR IP 주소 형식을 검사하는 정규 표현식
    const cidrPattern = /^(\d{1,3}\.){3}\d{1,3}\/\d{1,2}$/;
    return cidrPattern.test(input);
  };

  const changeTag = (e) => {
    setInputValue(e.target.value);
    if (!isValidCIDR(e.target.value)) {
      // 올바르지 않은 형식일 때 경고 표시
      setError(true);
      console.log("error")
      // e.target.setCustomValidity('올바른 CIDR IP 주소 형식이 아닙니다.');
    } else {
      // 유효한 형식이면 경고 해제
      setError(false);
      console.log("not error")
      // e.target.setCustomValidity('');
    }
  };

  const addTag = () => {
    if (isValidCIDR(inputValue)) {
      setTags([...tags, inputValue]);
      setInputValue('');
    }
  };

  const removeTag = (tagToRemove) => {
    const updatedTags = tags.filter((tag) => tag !== tagToRemove);
    setTags(updatedTags);
  };

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
          <ModalBody pb={6}>
            <FormControl isRequired>
              <FormLabel>Dept Name</FormLabel>
              <Input ref={initialRef} value={apiInfo.dept_name} isReadOnly />
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Group Name</FormLabel>
              <Input value={apiInfo.group_name} isReadOnly />
            </FormControl>

            <FormControl isRequired>
              <FormLabel>Quota</FormLabel>
              <NumberInput
                defaultValue={apiInfo.quota}
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

            <FormControl isRequired>
              <FormLabel>Whitelist</FormLabel>
              <Input ref={initialRef} />
            </FormControl>
            <>
              <InputGroup>
                <Input
                  type="text"
                  placeholder="태그 입력"
                  value={inputValue}
                  isInvalid={error}
                  onChange={(e) => {
                    changeTag(e)
                  }}
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
              <HStack spacing={4}>
                {tags.map((tag, index) => (
                  <Tag key={index} size="md" borderRadius='full'
                  variant='solid'
                  colorScheme='green'>
                    <TagLabel>{tag}</TagLabel>
                    <TagCloseButton size="sm" onClick={() => removeTag(tag)} />
                  </Tag>
                ))}
              </HStack>
            </>
          </ModalBody>

          <ModalFooter>
            <Button colorScheme="blue" mr={3}>
              Accept
            </Button>
            <Button onClick={onClose}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  );
};

export default APIEditModal;
