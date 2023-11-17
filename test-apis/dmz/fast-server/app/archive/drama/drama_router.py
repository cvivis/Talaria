from fastapi import APIRouter, Depends, HTTPException
from elasticsearch import AsyncElasticsearch

from app.dependency import get_drama_index, get_client

from app.archive.drama.queries import SearchCondition, get_detail_query
from app.archive.drama.responses import list_from, detail_from

router = APIRouter(
    prefix='/fapi/v1/drama'
)


@router.get('/search')
async def search(page: int | None = 0,
                 size: int | None = 10,
                 name: str | None = None,
                 plot: str | None = None,
                 people: str | None = None,
                 channels: str | None = None,
                 genres: str | None = None,
                 keywords: str | None = None,
                 companies: str | None = None,
                 ratings: str | None = None,
                 s_date: str | None = None,
                 e_date: str | None = None,
                 client: AsyncElasticsearch = Depends(get_client),
                 index: str = Depends(get_drama_index)):

    condition = SearchCondition(page=page, size=size,
                                name=name, plot=plot, people=people,
                                channels=channels, genres=genres, keywords=keywords,
                                companies=companies, ratings=ratings,
                                s_date=s_date, e_date=e_date)

    try:
        response = await client.search(index=index, query=condition.get_query(),
                                       from_=condition.from_, size=condition.size)
        return list_from(response)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get('/{drama_id}')
async def detail(drama_id: int,
                 client: AsyncElasticsearch = Depends(get_client),
                 index: str = Depends(get_drama_index)):
    try:
        response = await client.search(index=index, query=get_detail_query(drama_id))
        return detail_from(response.body)
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
