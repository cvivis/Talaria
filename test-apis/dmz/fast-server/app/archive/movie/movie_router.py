from fastapi import APIRouter, Depends, HTTPException
from elasticsearch import AsyncElasticsearch

from app.dependency import get_movie_index, get_client

from app.archive.exception import NotFoundException
from app.archive.movie.queries import SearchCondition, get_detail_query
from app.archive.movie.responses import list_from, detail_from


router = APIRouter(
    prefix='/fapi/v1/movie'
)


@router.get('/search')
async def search(page: int | None = 0,
                 size: int | None = 10,
                 name: str | None = None,
                 plot: str | None = None,
                 people: str | None = None,
                 genres: str | None = None,
                 keywords: str | None = None,
                 companies: str | None = None,
                 ratings: str | None = None,
                 s_date: str | None = None,
                 e_date: str | None = None,
                 client: AsyncElasticsearch = Depends(get_client),
                 index: str = Depends(get_movie_index)):

    condition = SearchCondition(page=page, size=size,
                                name=name, plot=plot, people=people,
                                genres=genres, keywords=keywords,
                                companies=companies, ratings=ratings,
                                s_date=s_date, e_date=e_date)

    try:
        response = await client.search(index=index, query=condition.get_query(),
                                       from_=condition.from_, size=condition.size)
        return list_from(response)
    except NotFoundException as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get('/{movie_id}')
async def detail(movie_id: int,
                 client: AsyncElasticsearch = Depends(get_client),
                 index: str = Depends(get_movie_index)):
    try:
        response = await client.search(index=index, query=get_detail_query(movie_id))
        return detail_from(response.body)
    except NotFoundException as e:
        raise HTTPException(status_code=500, detail=str(e))
