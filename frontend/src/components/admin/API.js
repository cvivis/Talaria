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
  const [apisName, setApisName] = useState('');

  useEffect(() => {
    try {
      CustomAxios.get(`apis/admin/${apiId}`, {
        headers: { Authorization: `Bearer ${access_token}` },
      }).then((res) => {
        setApiInfo(res.data);
        setApisName(res.data.name)
      });
    } catch (error) {}
  }, []);
  
  return (
    <>
      <Flex mb={2}>
        <Text fontSize="2xl" color={mainText} m={0}>
          {apisName}
        </Text>
        <Spacer />
      </Flex>
      <Box bgColor="white" borderRadius={20} p="1px">
        <Box>
        <APIaggregate groupName={apiInfo.monitoring_url}></APIaggregate>
        </Box>
        <Box>
          <SwaggerUI spec={apiInfo.swagger_content} />
        </Box>
      </Box>
    </>
  );
};

export default API;
