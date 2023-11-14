import { useEffect, useState, useRef } from "react";
import { Box, Text } from "@chakra-ui/react";
import axios from "axios";
import ReactApexChart from "react-apexcharts";
function Chart2() {
  const [requestData, setRequestData] = useState([]);
  const [categories, setCategories] = useState([]);
  const [series, setSeries] = useState([
    { x: "Fri Nov 10 2023 00:00:00 GMT+0900 (한국 표준시)", y: 8 },
  ]);
  const [options, setOptions] = useState({
    chart: {
      height: 350,
      type: "line",
    },
    colors: ["#2D3748", "#2D3748"],
    // dataLabels: {
    //   enabled: true,
    //   style: {
    //     fontSize: '12px',
    //     colors: ["#2D3748"],
    //     borderRadius: 1, // 둥글게 만들기 위한 설정
    //   },
    // },

    stroke: {
      curve: "smooth",
    },
    yaxis: {
      min: 0,
      max: 40,
    },
    legend: {
      position: "top",
      horizontalAlign: "right",
      floating: true,
      offsetY: -25,
      offsetX: -5,
    },
  });

  const getData = async () => {
    const groupName = "/shinhan/banking";
    const url = "http://localhost:8080/group-detail/success?group-name=" + groupName;
    let response = await axios.get(url);
    // setRequestData(response.data);
    setRequestData((prevRequestData) => [
      ...prevRequestData,
      ...response.data.map((item) => ({
        x:
          new Date(item.date).toLocaleString().split(" ")[2].replace(".", "일") +
          " " +
          new Date(item.date).toLocaleString().split(" ")[3] +
          " " +
          new Date(item.date).toLocaleString().split(" ")[4],
        y: item.count,
      })),
    ]);
    setCategories((prev) => [...prev, ...requestData.map((item) => item.x)]);
  };

  useEffect(() => {
    getData();
  }, []);

  useEffect(() => {
    console.log(requestData);
    setSeries([
      {
        name: "usage",
        data: requestData.slice(),
      },
    ]);
  }, [requestData]);

  return (
    <>
      <Box bg="white" w="40vw" h="40vh" borderRadius="20px" boxShadow="lg" pl={5}>
        <Text fontWeight="Bold" pt={4} pl={4}>
          200 시간별 호출 횟수
        </Text>
        <ReactApexChart options={options} series={series} type="line" height="80%" width="100%" />
      </Box>
    </>
  );
}
export default Chart2;
