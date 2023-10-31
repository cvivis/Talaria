import { Box, CSSReset } from '@chakra-ui/react';
import { useEffect } from 'react';
import { Link } from 'react-router-dom';
import SwaggerEditor, {plugins} from 'swagger-editor';
import 'swagger-editor/dist/swagger-editor.css' // 좌측 에디터 화면

function SwaggerTest() {

    useEffect(() => {
        console.log("시작");

        const editor = SwaggerEditor({
            dom_id: '#swagger-editor',
            layout: 'EditorLayout',
            // plugins: Object.values(plugins),
            // layout: 'BaseLayout',
            // url: '',
            swagger2GeneratorUrl: 'https://generator.swagger.io/api/swagger.json',
            oas3GeneratorUrl: 'https://generator3.swagger.io/openapi.json',
            swagger2ConverterUrl: 'https://converter.swagger.io/api/convert',
        });
        // return () => {
        //     if(editor) {
        //         editor.destroy();
        //     }
        // };
    },[]);

    return (
        <div>
            <div>메인으로 이동하기 <Link to={"/"} >이동</Link></div>
            {/* <CSSReset /> */}
                <Box>
                    <div id="swagger-editor"></div>
                </Box>
        </div>
    );
}

export default SwaggerTest;