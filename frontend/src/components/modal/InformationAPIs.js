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
import APIAlert from '../alert/APIAlert';

const InformationAPIs = ({ isOpen, onClose, closeModal }) => {

  const initialRef = React.useRef(null)
  const finalRef = React.useRef(null)

  const [isAlertOpen, setIsAlertOpen] = useState(false);
  const [alertType, setAlertType] = useState('delete');

  const openAlert = (type) => {
    setAlertType(type);
    setIsAlertOpen(true);
  };

  const closeAlert = () => {
    setIsAlertOpen(false);
  };

  const handleCloseAlertAndModal = () => {
    if (alertType === 'update') {
      console.log('Sending update request...');
      // 실제로는 업데이트에 관련된 API 호출이나 로직을 추가해야 합니다.
    } else {
      console.log('Sending delete request...');
      // 실제로는 삭제에 관련된 API 호출이나 로직을 추가해야 합니다.
    }
    closeAlert();
    closeModal();
  };

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
              <Input ref={initialRef} />
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Web service URL</FormLabel>
              <Input />
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