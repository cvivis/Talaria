from fastapi import APIRouter, Depends, HTTPException
from elasticsearch import AsyncElasticsearch

from app.dependency import get_field_index, get_people_index, \
    get_genre_index, get_keyword_index, get_company_index, \
    get_channel_index, get_client

from app.archive.autocomplete.queries import *
from app.archive.autocomplete.responses import response_from


router = APIRouter(
    prefix='/fapi/v1/auto'
)


@router.get('/fields')
async def fields(client: AsyncElasticsearch = Depends(get_client),
                 index: str = Depends(get_field_index)):
    try:
        response = await client.search(index=index, query=get_field_query())
        return response_from(response, 'fields', 'name_kr')
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get('/people/{name}')
async def people(name: str,
                 client: AsyncElasticsearch = Depends(get_client),
                 index: str = Depends(get_people_index)):
    try:
        response = await client.search(index=index, query=get_people_query(name))
        return response_from(response, 'people', 'name_kr')
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get('/{video_type}/genres/{genre}')
async def genres(video_type: VideoType, genre: str,
                 client: AsyncElasticsearch = Depends(get_client),
                 index: str = Depends(get_genre_index)):
    try:
        query = get_match_query(genre, video_type)
        response = await client.search(index=index, query=query)
        return response_from(response, 'genres', 'name')
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get('/{video_type}/keywords/{keyword}')
async def keywords(video_type: VideoType, keyword: str,
                   client: AsyncElasticsearch = Depends(get_client),
                   index: str = Depends(get_keyword_index)):
    try:
        query = get_match_query(keyword, video_type)
        response = await client.search(index=index, query=query)
        return response_from(response, 'keywords', 'name')
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get('/{video_type}/companies/{company}')
async def companies(video_type: VideoType, company: str,
                    client: AsyncElasticsearch = Depends(get_client),
                    index: str = Depends(get_company_index)):
    try:
        query = get_match_query(company, video_type)
        response = await client.search(index=index, query=query)
        return response_from(response, 'companies', 'name')
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))


@router.get('/channels/{channel}')
async def channels(channel: str,
                   client: AsyncElasticsearch = Depends(get_client),
                   index: str = Depends(get_channel_index)):
    try:
        query = get_channel_query(channel)
        response = await client.search(index=index, query=query)
        return response_from(response, 'channels', 'name')
    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))
