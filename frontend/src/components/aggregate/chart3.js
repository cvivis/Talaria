import { Box, Text } from "@chakra-ui/react";
import { useEffect, useRef, useState } from "react";
import ReactApexChart from "react-apexcharts";
import ApexCharts from "apexcharts";
import axios from "axios";
import {
  Table,
  Thead,
  Tbody,
  Tfoot,
  Tr,
  Th,
  Td,
  TableCaption,
  TableContainer,
} from "@chakra-ui/react";
import { right } from "@popperjs/core";

function Chart3() {
   
  const [clientData , setClientData] = useState([]);
  const [countData , setCountData] = useState([]);
  const [labels , setLabels] = useState([]);
  const [series, setSeries] = useState([]);
  const [options, setOptions] = useState({
    chart: {
      width: 380,
      type: 'donut',
    },
    plotOptions: {
      pie: {
        donut: {
          labels: {
            show: true,
            name: {
              show: true,
              fontSize: '14px',
              fontFamily: 'Helvetica, Arial, sans-serif',
              fontWeight: 600,
              color: undefined,
              offsetY: -10,
            },
            value: {
              show: true,
              fontSize: '16px',
              fontFamily: 'Helvetica, Arial, sans-serif',
              fontWeight: 400,
              color: undefined,
              offsetY: 16,
              formatter: function (val) {
                return val;
              },
            },
            total: {
              show: true,
              label: 'Total',
              color: '#373d3f',
              fontSize: '16px',
              fontFamily: 'Helvetica, Arial, sans-serif',
              fontWeight: 600,
              offsetY: 0,
            },
          },
        },
      },
    },
    colors: ['#2A69AC', '#63B3ED',"#153E75"],
    labels: labels,
    responsive: [{
      breakpoint: 480,
      options: {
        chart: {
          width: 200
        },
        legend: {
          position: 'bottom'
        }
      }
    }]
  },)

  const getData = async () =>{
    const groupName = "/shinhan/banking";
    const url = "http://localhost:8080/group-detail/client-fail-ranking?group-name="+groupName;
    let response = await axios.get(url)
    // setRequestData(response.data);
   console.log(response.data)
   let temp = [];
   let clientTemp =[];
   response.data.map((item)=>{
    temp.push(item.statusCode + " Code");
    clientTemp.push(item.count);
   })
    setLabels(temp);
    setCountData(clientTemp);
    setClientData(response.data);
  }

  useEffect(()=>{
   getData();
  },[])

  useEffect(()=>{
    console.log(countData)
    console.log(labels)
    setSeries(countData.slice()
    )

   },[countData])

   useEffect(()=>{
    console.log("labels: ",labels);
    setOptions((prevOptions) => ({
      ...prevOptions,
      labels: labels,
    }));
   },[labels])
   
    return (
      <>
        <Box
         bg="white" w="36vw" h="80vh" borderRadius="20px" boxShadow="lg"
        >
          <Text fontWeight="Bold" pt={4} pl={4} >4XX 발생 TOP3</Text>
          <ReactApexChart options={options} series={series} type="donut" width="100%" height="50%"/>

          <TableContainer>
          <Table variant="simple" w="100%" h="100%" minH="50px">
            <Thead>
              <Tr>
                <Th>Error Code</Th>
                <Th isNumeric>Count</Th>
                <Th isNumeric>AVG Count</Th>
              </Tr>
            </Thead>
            <Tbody>
              {clientData.map((info, index) => (
                <Tr key={index}>
                  <Td color={"white"} fontWeight="Bold" border="none">
                    <div style={{
                          color:"black",
                            width:"100%",
                            height:"80%",
                            display: "flex",
                            paddingRight:"50%",
                            justifyContent: "center",
                            borderRadius:"7px",
                            alignItems: "center",
                            }} >{info.statusCode}</div>
                  </Td>
                  <Td isNumeric  border="none" >
                    <div style={{ margin: "10% auto",  textAlign:"center",paddingLeft:"50%" }}>{info.count}</div>
                  </Td>
                  <Td isNumeric border="none">
                  <div style={{ margin: "10% auto",  textAlign:"center",paddingLeft:"50%" }}>{info.avgCount}</div>
                  </Td>
                </Tr>
              ))}
            </Tbody>
          </Table>
        </TableContainer>
        </Box>
      </>
    );
}
export default Chart3;