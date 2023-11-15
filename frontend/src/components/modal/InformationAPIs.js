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
import React, { useState, useEffect } from 'react';
import APIAlert from '../alert/APIAlert';
import CustomAxios from '../axios/CustomAxios';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';

const InformationAPIs = ({ isOpen, onClose, closeModal, apisInfo }) => {

  const initialRef = React.useRef(null)
  const finalRef = React.useRef(null)

  const [isAlertOpen, setIsAlertOpen] = useState(false);
  const [alertType, setAlertType] = useState('delete');

  const [name, setName] = useState(apisInfo.name);
  const [url, setUrl] = useState(apisInfo.web_server_url);
  const access_token = useSelector(state => state.userInfo.access_token)
  const navigate = useNavigate();

  const openAlert = (type) => {
    setAlertType(type);
    setIsAlertOpen(true);
  };

  const closeAlert = () => {
    setIsAlertOpen(false);
  };

  const handleCloseAlertAndModal = async () => {
    if (alertType === 'update') {
      // 실제로는 업데이트에 관련된 API 호출이나 로직을 추가해야 합니다.
      try {  
        const response = await CustomAxios.patch(
          `apis/developer/` + apisInfo.apis_id,
          {
            name: name,
            web_server_url: url,
          },
          {
            headers: { Authorization: `Bearer ${access_token}` },
          }
        );
    
      } catch (error) {
        console.error('Error:', error);
      }
    } else {
      // 실제로는 삭제에 관련된 API 호출이나 로직을 추가해야 합니다.
      try {  
        const response = await CustomAxios.delete(
          `apis/developer/` + apisInfo.apis_id,
          {
            headers: { Authorization: `Bearer ${access_token}` },
          }
        );
    
      } catch (error) {
        console.error('Error:', error);
      }
    }

    closeAlert();
    closeModal();

    return navigate("/developer");
  };

  useEffect(() => {
  }, []);

  return (
    <>
      <Modal
        size='xl'
        initialFocusRef={initialRef}
        finalFocusRef={finalRef}
        isOpen={isOpen}
        onClose={onClose}
      >
        <ModalOverlay />
        <ModalContent>
          <ModalHeader>Information about API Group</ModalHeader>
          {/* <ModalCloseButton /> */}
          <ModalBody pb={6}>
            <FormControl isRequired>
              <FormLabel>Name</FormLabel>
              <Input ref={initialRef} defaultValue={apisInfo.name} onChange={(e) => setName(e.target.value)}/>
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Web service URL</FormLabel>
              <Input defaultValue={apisInfo.web_server_url} onChange={(e) => setUrl(e.target.value)}/>
            </FormControl>

            {/* <FormControl>
              <FormLabel>API URL suffix</FormLabel>
              <Input ref={initialRef} />
            </FormControl> */}
          </ModalBody>

          <ModalFooter>
            <Button colorScheme='red' mr={3} onClick={() => openAlert('delete')}>
              Delete
            </Button>
            <Button colorScheme='blue' mr={3} onClick={() => openAlert('update')}>
              Update
            </Button>
            <Button onClick={onClose}>Cancel</Button>
          </ModalFooter>
        </ModalContent>
      </Modal>

      <APIAlert
        isOpen={isAlertOpen}
        onClose={closeAlert}
        closeAlertAndModal={handleCloseAlertAndModal}
        alertType={alertType}  
      ></APIAlert>
    </>
  )
}

export default InformationAPIs;