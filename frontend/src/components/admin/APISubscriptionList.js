import {
  Button,
  Table,
  ButtonGroup,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  TableContainer,
  Popover,
  PopoverTrigger,
  PopoverContent,
  PopoverArrow,
  PopoverCloseButton,
  PopoverHeader,
  PopoverBody,
} from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import CustomAxios from "../axios/CustomAxios";
import { useSelector } from "react-redux";

const APISubscriptionList = () => {
  const [response, setResponse] = useState([]);
  const access_token = useSelector((state) => state.userInfo.access_token);

  useEffect(() => {
    try {
      CustomAxios.get(`subscriptions/admin?status=pending`, {
        headers: { Authorization: `Bearer ${access_token}` },
      }).then((res) => {
        setResponse(res.data);
      });
    } catch (error) {}
  }, []);

  const handleApproved = (index, id) => {
    try {
      CustomAxios.patch(
        `subscriptions/admin`,
        {
          subscription_id: id,
          status: "SUBSCRIBING",
        },
        {
          headers: { Authorization: `Bearer ${access_token}` },
        }
      ).then((res) => {
        const newData = [...response];
        newData.splice(index, 1);
        setResponse(newData);
      });
    } catch (error) {}
  };

  const handleDeclined = (index, id) => {
    try {
      CustomAxios.patch(
        `subscriptions/admin`,
        {
          subscription_id: id,
          status: "REJECTED",
        },
        {
          headers: { Authorization: `Bearer ${access_token}` },
        }
      ).then((res) => {
        const newData = [...response];
        newData.splice(index, 1);
        setResponse(newData);
      });
    } catch (error) {}
  };

  const truncateText = (text, maxLength) => {
    return text.length > maxLength ? `${text.slice(0, maxLength)}...` : text;
  };

  return (
    <>
      <TableContainer marginLeft="5%" marginRight="5%">
        <Table variant="simple">
          <Thead>
            <Tr>
              <Th fontSize="xs">Dept Name</Th>
              <Th fontSize="xs">Group Name</Th>
              <Th fontSize="xs">User</Th>
              <Th fontSize="xs">Domain</Th>
              <Th fontSize="xs">Purpose</Th>
              <Th fontSize="xs">Process</Th>
            </Tr>
          </Thead>
          <Tbody>
            {response.map((item, idx) => (
              <Tr key={idx}>
                <Td fontSize="xs">{item.developer_name}</Td>
                <Td fontSize="xs">{item.api_name}</Td>
                <Td fontSize="xs">{item.user_email}</Td>
                <Td fontSize="xs">{item.address}</Td>
                <Td fontSize="xs">
                  {truncateText(item.content, 10)}
                  {item.content.length > 10 && (
                    <Popover>
                      <PopoverTrigger>
                        <Button variant="ghost" size="xs" colorScheme="gray">
                          More
                        </Button>
                      </PopoverTrigger>
                      <PopoverContent>
                        <PopoverArrow />
                        <PopoverCloseButton />
                        <PopoverHeader>Purpose</PopoverHeader>
                        <PopoverBody>{item.content}</PopoverBody>
                      </PopoverContent>
                    </Popover>
                  )}
                </Td>
                <Td fontSize="xs">
                  <ButtonGroup variant="outline" spacing="3" size="xs">
                    <Button
                      colorScheme="blue"
                      onClick={() => handleApproved(idx, item.subscription_id)}
                    >
                      APPROVED
                    </Button>
                    <Button
                      colorScheme="red"
                      onClick={() => handleDeclined(idx, item.subscription_id)}
                    >
                      DECLINED
                    </Button>
                  </ButtonGroup>
                </Td>
              </Tr>
            ))}
          </Tbody>
        </Table>
      </TableContainer>
    </>
  );
};

export default APISubscriptionList;
