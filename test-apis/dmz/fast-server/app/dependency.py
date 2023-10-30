from app.settings import get_settings

from app.elastic.config import ElasticConfig
from app.elastic.util import wait_elasticsearch

import asyncio
import nest_asyncio


settings = get_settings()
config = ElasticConfig(settings.describe_settings())
client = config.get_client()

nest_asyncio.apply()
loop = asyncio.get_event_loop()
loop.run_until_complete(wait_elasticsearch(client))


async def get_movie_index():
    yield settings.MOVIE_INDEX


async def get_drama_index():
    yield settings.DRAMA_INDEX


async def get_people_index():
    yield settings.PEOPLE_INDEX


async def get_field_index():
    yield settings.FIELD_INDEX


async def get_genre_index():
    yield settings.GENRE_INDEX


async def get_keyword_index():
    yield settings.KEYWORD_INDEX


async def get_company_index():
    yield settings.COMPANY_INDEX


async def get_channel_index():
    yield settings.CHANNEL_INDEX


async def get_client():
    try:
        yield client
    finally:
        await client.close()


async def close_client():
    await client.close()
