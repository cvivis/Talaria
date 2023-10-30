from fastapi import HTTPException

from app.archive.exception import NotFoundException


def response_from(response: dict, field: str, name: str) -> dict:
    hits = response['hits']['hits']
    result = {field: []}

    try:
        for hit in hits:
            new_row = {'id': hit['_id'], 'name': hit['_source'][name]}
            result[field].append(new_row)
        return result
    except NotFoundException as e:
        raise HTTPException(status_code=500, detail=str(e))
