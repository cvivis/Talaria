import { Box, Text } from "@chakra-ui/react";
import { useEffect, useRef, useState } from "react";
import ReactApexChart from "react-apexcharts";
import ApexCharts from "apexcharts";
import * as StompJs from "@stomp/stompjs";

function Chart2() {
  /*stomp 관련 */
  const client = useRef({});
  const [memoryData, setMemoryData] = useState([]);
  const connect = () => {
    client.current = new StompJs.Client({
      // brokerURL: "ws://localhost:8080/ws/monitoring",
      brokerURL: "wss://api.talaria.kr/ws/monitoring",
      onConnect: () => {
        // Do something, all subscribes must be done is this callback
        console.log("연결 SUB");
        subscribe();
      },
    });
    client.current.activate();
  };

  const disconnect = () => {
    client.current.deactivate(); // 활성화된 연결 끊기
  };

  const subscribe = () => {
    client.current.subscribe("/sub/memory-check", (res) => {
      // server에게 메세지 받으면
      const json_body = JSON.parse(res.body);
      console.log(res.body);
      // setMemoryData((prevItems) => [...prevItems, {x: new Date(json_body.date),
      // y: json_body.memoryUsage,}])
      memoryData.push({
        x: new Date(json_body.date),
        y: json_body.memoryUsage,
      });
      console.log(memoryData);
    });
  };

  // var lastDate = 0;
  // var TICKINTERVAL = 86400000
  const [XAXISRANGE, SetXAXISRANGE] = useState(60000);
  const [options, setOptions] = useState({
    chart: {
      id: "realtime-memory", // ApexCharts의 메서드 호출 시 필요 (brush chart, syncronized chart, calling exec)
      height: 350, // 그래프 높이
      type: "line", // 차트 타입 (line , donut,treemap ... )
      animations: {
        enabled: true,
        easing: "linear", // 애니메이션 속도 변화률 linear = 등속
        dynamicAnimation: {
          enabled: true, // 데이터 바뀔 때 re-rendering
          speed: 1000, // 데이터 바뀔 때 run 속도
        },
      },
      toolbar: {
        //다운로드같은 메뉴바
        show: false,
      },
      zoom: {
        enabled: false, // 확대 이동 가능 <- realchart에서는 false
      },
    },
    dataLabels: {
      enabled: false, //데이터에 라벨링 <- realchart에서는 false
    },
    stroke: {
      curve: "smooth", // 데이터 꺾는 정도
    },
    markers: {
      size: 0, // 수정 X
    },
    xaxis: {
      type: "datetime", // category, datetime, numeric
      range: XAXISRANGE, //최대, 최소 값을 동적으로 받기위한 용도 ?
    },
    yaxis: {
      min: 0,
      decimalsInFloat: 2,
      labels: {
        offsetX: -10,
      },
    },
    legend: {
      show: false, //??
    },
    plotOption: {
      boxPlot: {
        colors: {
          upper: "red",
          lower: "blue",
        },
      },
    },
  });

  const [series, setSeries] = useState([
    {
      data: memoryData.slice(), // 배열 복사
    },
  ]);

  useEffect(() => {
    connect();
    const interval = setInterval(() => {
      setSeries();
      ApexCharts.exec("realtime-memory", "updateSeries", [
        {
          data: memoryData,
        },
      ]);
    }, 1000);

    return () => {
      clearInterval(interval);
      disconnect();
    };
  }, []);

  return (
    <>
      <Box
        id="chart-memory"
        bg="white"
        w="36vw"
        h="40vh"
        borderRadius="20px"
        boxShadow="lg"
        py="10px"
      >
        <Text p={2}>MEMORY</Text>
        <ReactApexChart options={options} series={series} type="line" width="100%" height="90%" />
      </Box>
    </>
  );
}
export default Chart2;
