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
import React from 'react';


const CreateAPIs = ( { isOpen, onClose} ) => {

  const initialRef = React.useRef(null)
  const finalRef = React.useRef(null)

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
              <Input ref={initialRef} />
            </FormControl>

            <FormControl mt={4} isRequired>
              <FormLabel>Web service URL</FormLabel>
              <Input />
            </FormControl>

            <FormControl>
              <FormLabel>API URL suffix</FormLabel>
              <Input ref={initialRef} />
            </FormControl>
          </ModalBody>

          <ModalFooter>
            <Button colorScheme='blue' mr={3}>
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