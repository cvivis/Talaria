import {
  Box,
  Flex,
  Spacer,
  Text,
  useColorModeValue,
} from "@chakra-ui/react";
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import SwaggerUI from "swagger-ui-react";
import "swagger-ui-react/swagger-ui.css";
import CustomAxios from "../axios/CustomAxios";
import { useSelector } from "react-redux";

const API = () => {
  const mainText = useColorModeValue("white", "gray.200");
  const { apiId } = useParams();
  const params = useParams();
  const access_token = useSelector((state) => state.userInfo.access_token);
  const [apiInfo, setApiInfo] = useState({});

  useEffect(() => {
    try {
      CustomAxios.get(`apis/admin/${apiId}`, {
        headers: { Authorization: `Bearer ${access_token}` },
      }).then((res) => {
        setApiInfo(res.data);
      });
    } catch (error) {}
  }, []);

  return (
    <>
      <Flex mb={2}>
        <Text fontSize="2xl" color={mainText} m={0}>
          {params.productName}
        </Text>
        <Spacer />
      </Flex>
      <Box bgColor="white" borderRadius={20} p="1px">
        <Box>
          <SwaggerUI spec={apiInfo.swagger_content} />
        </Box>
      </Box>
    </>
  );
};

export default API;
