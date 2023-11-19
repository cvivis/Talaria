from fastapi import FastAPI
from fastapi.encoders import jsonable_encoder
from fastapi.responses import JSONResponse

app = FastAPI()


@app.get('/test1/api')
def read_root():
    return {'message': 'Hello Test1'}


@app.get('/test1/api/name')
def name():
    content = jsonable_encoder({'name': 'hermes'})
    return JSONResponse(content=content)


@app.get('/test1/api/service')
def service():
    content = jsonable_encoder({'service': 'talaria'})
    return JSONResponse(content=content)
