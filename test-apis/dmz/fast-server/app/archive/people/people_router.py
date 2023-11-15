from fastapi import APIRouter, Depends, HTTPException
from elasticsearch import AsyncElasticsearch

from app.dependency import get_people_index, get_client

from app.archive.people.queries import SearchCondition, get_detail_query
from app.archive.people.responses import list_from, detail_from

router = APIRouter(
    prefix='/fapi/v1/people'
)


@router.get('/search')
async def search(page: int | None = 0,
                 size: int | None = 10,
                 name: str | None = None,
                 fields: str | None = None,
                 genders: str | None = None,
                 ages: str | None = None,
                 client: AsyncElasticsearch = Depends(get_client),
                 index: str = Depends(get_people_index)):

    condition = SearchCondition(page=page, size=size,
                                name=name, fields=fields,
                                genders=genders, ages=ages)

    try:
        response = await client.search(index=index, query=condition.get_query(),
                                     from_=condition.from_, size=condition.size)
        return list_from(response)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get('/{person_id}')
async def detail(person_id: int,
                 client: AsyncElasticsearch = Depends(get_client),
                 index: str = Depends(get_people_index)):
    try:
        response = await client.search(index=index, query=get_detail_query(person_id))
        return detail_from(response)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
