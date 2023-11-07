import { useEffect, useState,useRef} from "react";
import { Box,Text } from "@chakra-ui/react";
import * as StompJs from '@stomp/stompjs';
import ReactApexChart from 'react-apexcharts';
import ApexCharts from 'apexcharts';
function Chart6() {

    var data = []

    const [successCount, setSuccessCount] = useState(0);
    const [failCount, setfailCount] = useState(0);
    const [serverFailCount, setserverFailCount] = useState(0);
    // let XAXISRANGE = 1800000;
    const [XAXISRANGE, SetXAXISRANGE] = useState(600000);
    const [options, setOptions] = useState({
      chart: {
        id: 'realtime', // ApexCharts의 메서드 호출 시 필요 (brush chart, syncronized chart, calling exec)
        height: 350, // 그래프 높이 
        type: 'line', // 차트 타입 (line , donut,treemap ... )
        animations: {
          enabled: true,
          easing: 'linear', // 애니메이션 속도 변화률 linear = 등속 
          dynamicAnimation: { 
            enabled : true, // 데이터 바뀔 때 re-rendering
            speed: 1000  // 데이터 바뀔 때 run 속도
          }
        },
        toolbar: { //다운로드같은 메뉴바 
          show: false,
        },
        zoom: {
          enabled: false // 확대 이동 가능 <- realchart에서는 false
        }
      },
      dataLabels: {
        enabled: false //데이터에 라벨링 <- realchart에서는 false
      },
      stroke: {
        curve: 'smooth' // 데이터 꺾는 정도
      },
      markers: {
        size: 0 // 수정 X
      },
      xaxis: {
        type: 'datetime', // category, datetime, numeric
        range: XAXISRANGE //최대, 최소 값을 동적으로 받기위한 용도 ?
      },
      yaxis: {
        max: 10,
        min: 0
      },
      legend: {
        show: false //?? 
      },
    });
    
    function getNewSeries() {
      
      data.push({
        x: new Date(),
        // y: Math.floor(Math.random() * (10 -  0 + 1)) + 0
        y:10,
      })
      console.log(data)
    }
    
    const [series, setSeries] = useState([
      {
        data: data.slice() // 배열 복사 
      }
    ]);
    
    // const ranking = useRef([]);
    const [ranking, setranking] = useState([]);
    // const [errorCount, setErrorCount] = useState([]);
    useEffect(() => {
      console.log("targets",ranking);
    }, [ranking])
    
    const client = useRef({}); 
    const [log,setLog] = useState("");
    const connect = () =>{
        client.current = new StompJs.Client({
            brokerURL: 'ws://localhost:8080/ws/monitoring',
            onConnect:() =>{
               // Do something, all subscribes must be done is this callback
              console.log("연결 SUB");
                subscribeToTopic('/sub/success-time-check', subscribeSuccess);
            },
        });
        client.current.activate();
    }
  
    const disconnect = () => {
      client.current.deactivate(); // 활성화된 연결 끊기 
  };
  
  const subscribeToTopic = (topic,callback) => {
    const subscription = client.current.subscribe(topic, (res) => {
        const payload = JSON.parse(res.body);
        console.log(`Received message for ${topic}:`, payload);
        // 여기에서 payload를 처리
        callback(payload);
      });
  }
  const subscribeSuccess = (payload) => {
    
        // console.log(res)
        setSuccessCount(payload);
        // console.log(payload);

        // setErrorCount(json_body.slice());
        // setranking (json_body.data.slice());
        // console.log(ranking.current+"asdasdas")
        // data.push(json_body);
        // setLog(json_body);
        // console.log(res.body);

    };

    const subscribeFail = () => {
        client.current.subscribe('/sub/fail-time-check', (res) => { // server에게 메세지 받으면
          // console.log(res)
          const json_body = JSON.parse(res.body);
          console.log(json_body);
        //   setErrorCount(json_body.slice());
          // setranking (json_body.data.slice());
          // console.log(ranking.current+"asdasdas")
    ;      // data.push(json_body);
          // setLog(json_body);
          // console.log(res.body);
        });
      };

      const subscribeServerFail = () => {
        client.current.subscribe('/sub/server-fail-time-check', (res) => { // server에게 메세지 받으면
          // console.log(res)
          const json_body = JSON.parse(res.body);
          console.log(json_body);
        //   setErrorCount(json_body.slice());
          // setranking (json_body.data.slice());
          // console.log(ranking.current+"asdasdas")
    ;      // data.push(json_body);
          // setLog(json_body);
          // console.log(res.body);
        });
      };
  
    useEffect(() => {
  //웹소켓 
  
   connect(); // 마운트시 실행
      const interval = setInterval(() => {
        // getNewSeries();
  
        
      ApexCharts.exec('realtime', 'updateSeries', [
          {
            data: data
          }
        ]);
      }, 1000);
  
      return () => {
        clearInterval(interval);
        disconnect();
      };
    }, []);

    return (
        <>
        <Box bg='white' w='40vw' h='50vh' borderRadius="20px"  boxShadow="lg">
        <Text fontWeight='Bold' p={5} >200&400&500 발생 횟수</Text>
            <ReactApexChart options={options} series={series} type="line" height="80%" width="100%"/>
        </Box>
        </>
    );
}
export default Chart6;