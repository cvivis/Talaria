from fastapi import FastAPI
from fastapi.encoders import jsonable_encoder
from fastapi.responses import JSONResponse

app = FastAPI()


@app.get('/users/getUsers')
def get_users():
    gen = 9
    group = 'trainee'
    names = ['김예진', '오승기', '박민아', '이지승', '황시은', '이승민']
    ids = ['yejinkim', 'seoungioh', 'minapark', 'jiseonglee', 'sieunwhang', 'seongminlee']
    keys = ['0912111', '0912112', '0912113', '0912114', '0912115', '0912116']

    users = []
    for i, name in enumerate(names):
        user = dict()
        user['gen'] = gen
        user['group'] = group
        user['name'] = name
        user['id'] = ids[i]
        user['key'] = keys[i]
        users.append(user)

    content = jsonable_encoder({'users': users})
    return JSONResponse(content=content)
