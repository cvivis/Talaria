import React, { useEffect } from 'react';
import SwaggerEditor from 'swagger-editor';
import SwaggerUI from 'swagger-ui-react';
import 'swagger-editor/dist/swagger-editor.css'
import 'swagger-ui-react/swagger-ui.css';

function App() {
  useEffect(() => {
    console.log('hello')
    // Swagger Editor 초기화
    const editor = SwaggerEditor({
      dom_id: '#swagger-editor',
      layout: 'EditorLayout',
      // plugins: Object.values(plugins),
      swagger2GeneratorUrl: 'https://generator.swagger.io/api/swagger.json',
      oas3GeneratorUrl: 'https://generator3.swagger.io/openapi.json',
      swagger2ConverterUrl: 'https://converter.swagger.io/api/convert',
    });

    return () => {
      // 컴포넌트 언마운트 시 Swagger Editor 해제
      if(editor) {
        editor.destroy();
      }
    };
  }, []);

  return (
    <div>
      <h1>API Documentation</h1>

      {/* Swagger Editor */}
      <div id="swagger-editor"></div>

      <hr />
      <br></br>

      <h2>API Specification</h2>
      {/* Swagger UI */}
      <SwaggerUI url="https://petstore.swagger.io/v2/swagger.json" />
    </div>
  );
}

export default App;
