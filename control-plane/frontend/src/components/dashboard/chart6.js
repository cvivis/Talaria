import { useEffect, useState, useRef } from "react";
import { Box, Text } from "@chakra-ui/react";
import * as StompJs from "@stomp/stompjs";
import ReactApexChart from "react-apexcharts";
import ApexCharts from "apexcharts";
function Chart6() {
  var data = [];

  const [successCount, setSuccessCount] = useState([]);
  const [failCount, setFailCount] = useState([]);
  const [serverFailCount, setServerFailCount] = useState([]);
  const [maxY, setMaxY] = useState(0);
  // let XAXISRANGE = 1800000;
  const [XAXISRANGE, SetXAXISRANGE] = useState(60000);
  const [options, setOptions] = useState({
    chart: {
      id: "realtime", // ApexCharts의 메서드 호출 시 필요 (brush chart, syncronized chart, calling exec)
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
    // colors: ["blue", "orange", "red"],
    dataLabels: {
      enabled: false, //데이터에 라벨링 <- realchart에서는 false
    },
    legend: {
      position: "top",
      horizontalAlign: "right",
      floating: true,
      offsetY: -25,
      offsetX: -5,
    },
    stroke: {
      curve: "smooth", // 데이터 꺾는 정도
    },
    markers: {
      size: 0, // 수정 X
      // colors: ["blue", "orange", "red"],
    },
    xaxis: {
      type: "datetime", // category, datetime, numeric
      range: XAXISRANGE, //최대, 최소 값을 동적으로 받기위한 용도 ?
    },
    yaxis: {
      // max: 40,
      min: 0,
      // decimalsInFloat: 4,
      // labels: {
      //   offsetX: -10,
      // },
      // min: 0,
      // forceNiceScale: true,
      // tickAmount: 5,
    },
    legend: {
      position: "top", // 상단에 표시
      horizontalAlign: "right", // 오른쪽 정렬
      // show: false, //??
    },
    // labels: {
    //   show: true,
    //   align: "right",
    // },
  });

  const client = useRef({});
  const connect = () => {
    client.current = new StompJs.Client({
      brokerURL: `wss://${process.env.REACT_APP_DATA_PLANE_DOMAIN}/ws/monitoring`,
      // brokerURL: "wss://api.talaria.kr/ws/monitoring",
      onConnect: () => {
        // Do something, all subscribes must be done is this callback
        console.log("연결 SUB");
        subscribeToTopic("/sub/success-time-check", subscribeSuccess);
        subscribeToTopic("/sub/fail-time-check", subscribeFail);
        subscribeToTopic("/sub/server-fail-time-check", subscribeServerFail);
      },
    });
    client.current.activate();
  };

  const disconnect = () => {
    client.current.deactivate(); // 활성화된 연결 끊기
  };

  useEffect(() => {
    setSeries((prevSeries) => [
      {
        name: "success",
        data: successCount.slice(),
      },
      {
        name: "fail",
        data: failCount.slice(),
      },
      {
        name: "serverFail",
        data: serverFailCount.slice(),
      },
    ]);

    // let max = maxY;
    // for (let i = 0; i < successCount.length; i++) {
    //   if (successCount[i] > max) {
    //     max = successCount[i];
    //   }
    // }
    // for (let i = 0; i < failCount.length; i++) {
    //   if (failCount[i] > max) {
    //     max = failCount[i];
    //   }
    // }
    // for (let i = 0; i < serverFailCount.length; i++) {
    //   if (serverFailCount[i] > max) {
    //     max = serverFailCount[i];
    //   }
    // }
    // setMaxY(max);
    // console.log("serires : ", series);
  }, [successCount, failCount, serverFailCount]);

  // useEffect(() => {
  //   setOptions((prevOptions) => ({
  //     ...prevOptions,
  //     yaxis: {
  //       ...prevOptions.yaxis,
  //       max: maxY + 10,
  //     },
  //   }));
  // }, [maxY]);

  const subscribeToTopic = (topic, callback) => {
    const subscription = client.current.subscribe(topic, (res) => {
      const payload = JSON.parse(res.body);
      // console.log(`Received message for ${topic}:`, payload);
      // 여기에서 payload를 처리
      callback(payload);
    });
  };
  const subscribeSuccess = (payload) => {
    console.log("payload: ", payload);
    setSuccessCount((prevSuccessCount) => [
      ...prevSuccessCount,
      { x: new Date(payload.date), y: payload.time },
    ]);
  };

  const subscribeFail = (payload) => {
    setFailCount((prevFailCount) => [
      ...prevFailCount,
      { x: new Date(payload.date), y: payload.time },
    ]);
  };

  const subscribeServerFail = (payload) => {
    setServerFailCount((prevServerFailCount) => [
      ...prevServerFailCount,
      { x: new Date(payload.date), y: payload.time },
    ]);
  };

  const [series, setSeries] = useState([
    {
      name: "success",
      data: successCount.slice(), // 배열 복사
    },
    {
      name: "fail",
      data: failCount.slice(), // 배열 복사
    },
    {
      name: "serverFail",
      data: serverFailCount.slice(), // 배열 복사
    },
  ]);

  useEffect(() => {
    //웹소켓

    connect(); // 마운트시 실행
    // const interval = setInterval(() => {
    //   setSeries();
    //   ApexCharts.exec("realtime", "updateSeries", [
    //     {
    //       data: averageTimeData,
    //     },
    //   ]);
    // }, 1000);

    return () => {
      // clearInterval(interval);
      disconnect();
    };
  }, []);

  return (
    <>
      <Box bg="white" w="40vw" h="55vh" borderRadius="20px" boxShadow="lg">
        <Text fontWeight="Bold" p={5}>
          Occurrence Counts Of Status Codes
        </Text>
        <ReactApexChart options={options} series={series} type="line" height="80%" width="100%" />
      </Box>
    </>
  );
}
export default Chart6;
