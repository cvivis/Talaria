import {
    AlertDialog,
    AlertDialogBody,
    AlertDialogFooter,
    AlertDialogHeader,
    AlertDialogContent,
    AlertDialogOverlay,
    Button,
} from '@chakra-ui/react';
import React from 'react'

const APIAlert = ({ isOpen, onClose, closeAlertAndModal, alertType }) => {
    // const { isOpen, onOpen, onClose } = useDisclosure()
    const cancelRef = React.useRef()

    return (
      <>
        <AlertDialog
          size='xl'
          isOpen={isOpen}
          leastDestructiveRef={cancelRef}
          onClose={onClose}
        >
          <AlertDialogOverlay>
            <AlertDialogContent>
              <AlertDialogHeader fontSize='lg' fontWeight='bold'>
                Confirmation
              </AlertDialogHeader>
  
              <AlertDialogBody>
                Are you sure you want to {alertType === 'update' ? 'update' : 'delete'} API group "API_GROUP_NAME"?
              </AlertDialogBody>
  
              <AlertDialogFooter>
                <Button colorScheme='blue' onClick={closeAlertAndModal}>
                  Yes
                </Button>
                <Button ref={cancelRef} onClick={onClose} ml={3}>
                  Cancel
                </Button>
              </AlertDialogFooter>
            </AlertDialogContent>
          </AlertDialogOverlay>
        </AlertDialog>
      </>
    )
  }

export default APIAlert;