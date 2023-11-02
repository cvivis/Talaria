import { Box } from '@chakra-ui/react';
import { useEffect } from 'react';
import { Link } from 'react-router-dom';
import SwaggerEditor from 'swagger-editor';
import 'swagger-ui-react/swagger-ui.css';

const SwaggerUIComponent = () => {
    const yaml = require('js-yaml');
    useEffect(() => {
        console.log("시작");

        const editor = SwaggerEditor({
            dom_id: '#swagger-ui',
            layout: 'BaseLayout',
            swagger2GeneratorUrl: 'https://generator.swagger.io/api/swagger.json',
            oas3GeneratorUrl: 'https://generator3.swagger.io/openapi.json',
            swagger2ConverterUrl: 'https://converter.swagger.io/api/convert',
        });

        localStorage.setItem('swagger-editor-content', 
`openapi: 3.0.3
info:
  title: Swagger Petstore - OpenAPI 3.0
  description: asdf
  termsOfService: http://swagger.io/terms/
  contact:
    email: apiteam@swagger.io
  license:
    name: Apache 2.0
    url: http://www.apache.org/licenses/LICENSE-2.0.html
  version: 1.0.11
externalDocs:
  description: Find out more about Swagger
  url: http://swagger.io
servers:
  - url: https://petstore3.swagger.io/api/v3
tags:
  - name: pet
    description: Everything about your Pets
    externalDocs:
      description: Find out more
      url: http://swagger.io
paths:
  /pet/{petId}:
    post:
      tags:
        - pet
      summary: Updates a pet in the store with form data
      description: ''
      operationId: updatePetWithForm
      parameters:
        - name: petId
          in: path
          description: ID of pet that needs to be updated
          required: true
          schema:
            type: integer
            format: int64
        - name: name
          in: query
          description: Name of pet that needs to be updated
          schema:
            type: string
        - name: status
          in: query
          description: Status of pet that needs to be updated
          schema:
            type: string
      responses:
        '405':
          description: Invalid input
      security:
        - petstore_auth:
            - write:pets
            - read:pets
components:
  securitySchemes:
    petstore_auth:
      type: oauth2
      flows:
        implicit:
          authorizationUrl: https://petstore3.swagger.io/oauth/authorize
          scopes:
            write:pets: modify pets in your account
            read:pets: read your pets`)

        const stringValue = localStorage.getItem('swagger-editor-content');
        console.log('String Value:', stringValue);      

    },[]);

    return (
        <div>
            <div>메인으로 이동하기 <Link to={"/d"} >이동</Link></div>
            {/* <CSSReset /> */}
                <Box>
                    <div id="swagger-ui"></div>
                </Box>
            
        </div>
    );
}

export default SwaggerUIComponent;