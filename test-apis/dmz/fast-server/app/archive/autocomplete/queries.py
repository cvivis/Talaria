from enum import Enum


class VideoType(str, Enum):
    MOVIE = 'movie'
    DRAMA = 'drama'


def get_field_query() -> dict:
    return {'match_all': {}}


def get_people_query(name: str) -> dict:
    query = dict()
    query['bool'] = dict()
    query['bool']['must'] = []

    column = 'name_en' if name.isascii() else 'name_kr'
    prefix = {'prefix': {column: name}}
    query['bool']['must'].append(prefix)

    return query


def get_match_query(name: str, video_type: VideoType) -> dict:
    query = dict()
    query['bool'] = dict()
    query['bool']['must'] = []

    prefix = {'prefix': {'name': name}}
    query['bool']['must'].append(prefix)

    match = {'match': {video_type.value: True}}
    query['bool']['must'].append(match)

    return query


def get_channel_query(channel: str) -> dict:
    query = dict()
    query['bool'] = dict()
    query['bool']['must'] = []

    prefix = {'prefix': {'name': channel}}
    query['bool']['must'].append(prefix)

    return query
