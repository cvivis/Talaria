import { Box, Text, propNames } from "@chakra-ui/react";
import { useEffect, useRef, useState } from "react";
import ReactApexChart from "react-apexcharts";
import axios from "axios";
function Chart1(props) {
  const [requestData, setRequestData] = useState([]);
  const [categories, setCategories] = useState([]);
  const [groupName,setGroupName] = useState([props.groupName]);
  const [series, setSeries] = useState([
    { x: "Fri Nov 10 2023 00:00:00 GMT+0900 (한국 표준시)", y: 8 },
  ]);
  const [options, setOptions] = useState({
    chart: {
      type: "bar",
    },
    plotOptions: {
      bar: {
        horizontal: false,
        colors: {
          ranges: [
            {
              from: 0,
              to: 5,
              color: "#0c4da2", // 범위에 따른 색상 설정
            },
            {
              from: 5,
              to: 20,
              color: "#164085", // 범위에 따른 색상 설정
            },
            // 추가적인 범위 및 색상 설정 가능
          ],
        },
        barWidth: "20%",
      },
    },
    xaxis: {
      type: "category", // 시간값으로 사용할 것임을 명시
      categories: categories,
    },
  });

  const getData = async () => {
    // const groupName = "/shinhan/banking";
    const groupName = props.groupName;
    // console.log("props: ",props.groupName);
    const url = "https://api.talaria.kr/group-detail/request-count?group-name=" + groupName;
    let response = await axios.get(url);
    // setRequestData(response.data);
    setRequestData((prevRequestData) => [
      ...prevRequestData,
      ...response.data.map((item) => ({
        x: new Date(item.date).toISOString().split("T")[0],
        y: item.count,
      })),
    ]);
    setCategories((prev) => [...prev, ...requestData.map((item) => item.x)]);
  };

  useEffect(() => {
    getData();
  }, [props.groupName]);

  useEffect(() => {
    // console.log("chart1 : ",requestData)
    setSeries([
      {
        name: "usage",
        data: requestData.slice(),
      },
    ]);
  }, [requestData]);

  return (
    <>
      <Box bg="white" w="40vw" h="40vh" borderRadius="20px" boxShadow="lg">
        <Text pt={4} pl={4} fontWeight="Bold">
          일일 호출횟수{" "}
        </Text>
        <ReactApexChart options={options} series={series} type="bar" width="95%" height="80%" />
      </Box>
    </>
  );
}
export default Chart1;
