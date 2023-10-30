from fastapi import APIRouter
from fastapi.encoders import jsonable_encoder
from fastapi.responses import JSONResponse

router = APIRouter(
    prefix='/test1/api'
)


@router.get('/name')
def name():
    content = jsonable_encoder({'name': 'hermes'})
    return JSONResponse(content=content)


@router.get('/service')
def service():
    content = jsonable_encoder({'service': 'talaria'})
    return JSONResponse(content=content)
