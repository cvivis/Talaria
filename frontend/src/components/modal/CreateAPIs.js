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
} from '@chakra-ui/react';
import React, { useState } from 'react';
import CustomAxios from '../axios/CustomAxios';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

const CreateAPIs = ({ isOpen, onClose }) => {

  const initialRef = React.useRef(null)
  const finalRef = React.useRef(null)

  const [name, setName] = useState("");
  const [url, setUrl] = useState("");
  const access_token = useSelector(state => state.userInfo.access_token)

  const HandleCreate = () => {

    const fetchData = () => {
      try {
        CustomAxios.post(
          `apis/developer`,
          {
            name: name,
            web_server_url: url,
          },
          {
            headers: { Authorization: `Bearer ${access_token}` },
          }
        )
          .then((res) => {
            goProductPage({
              apis_id: res.data.apis_id,
              name: name,
              web_server_url: url
            });
          })
      } catch (error) {
        console.error('Error:', error);
      }

    }
    fetchData();
  };

  const navigate = useNavigate();
  const goProductPage = (product) => {
    return navigate("/developer/API Products/" + product.apis_id, { state: { product } });
  }

  return (
    <>
      <Modal
        size='2xl'
        initialFocusRef={initialRef}
        finalFocusRef={finalRef}
        isOpen={isOpen}
        onClose={onClose}
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Create an API Group</ModalHeader>
          {/* <ModalCloseButton /> */}
          <ModalBody pb={6}>
            <FormControl isRequired>
              <FormLabel>Name</FormLabel>
              <Input ref={initialRef} onChange={(e) => setName(e.target.value)} />
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Web server URL</FormLabel>
              <Input onChange={(e) => setUrl(e.target.value)} />
            </FormControl>

            {/* <FormControl>
              <FormLabel>API URL suffix</FormLabel>
              <Input ref={initialRef} />
            </FormControl> */}
          </ModalBody>

          <ModalFooter>
            <Button colorScheme='blue' mr={3} onClick={HandleCreate}>
              Next
            </Button>
            <Button onClick={onClose}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>
    </>
  )
}

export default CreateAPIs;