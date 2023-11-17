from fastapi import FastAPI

from app.api import router

app = FastAPI()
app.include_router(router.router)


@app.get('/test1')
def read_root():
    return {'message': 'Hello Test1'}
