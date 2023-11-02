from typing import List


class MovieDetail:

    movie: dict
    str_fields: List[str]
    int_fields: List[str]
    list_fields: List[str]
    people_fields: List[str]
    source: dict

    def __init__(self, detail: dict):
        self.movie = dict()
        self.source = detail['_source']
        self._init_movie()

    def _init_movie(self):
        self.str_fields = ['name_kr', 'name_en', 'poster_path',
                           'released_date', 'rating', 'plot']
        self.int_fields = ['box_office', 'running_time']
        self.list_fields = ['genre', 'keyword', 'company']
        self.people_fields = ['actor', 'crew']

        self.movie['id'] = self.source['tmdb_id']

        for field in self.str_fields + self.int_fields:
            self.movie[field] = None

        for field in self.list_fields + self.people_fields:
            self.movie[field] = []

    def parse(self, details: bool = False) -> dict:
        for field in self.str_fields:
            if self.source.get(field) == 'unknown':
                continue
            self.movie[field] = self.source.get(field)

        for field in self.int_fields:
            if self.source.get(field) == -1:
                continue
            self.movie[field] = self.source.get(field)

        for field in self.list_fields:
            self._parse_iteratively(self.movie[field], field)

        for field in self.people_fields:
            self._parse_people(self.movie[field], field)

        if details:
            return self.movie

        return self._to_dict()

    def _parse_iteratively(self, target: list, field: str):
        if not self.source.get(field):
            return

        for value in self.source[field]:
            target.append(value.get('name'))

    def _parse_people(self, target: list, field: str):
        if not self.source.get(field):
            return

        for value in self.source[field]:
            person = dict()
            person['id'] = value.get('tmdb_id')
            person['name'] = value.get('name')
            person['role'] = value.get('role')
            person['profile_path'] = value.get('profile_path')
            target.append(person)

    def _to_dict(self) -> dict:
        result = dict()

        general_fields = ['id', 'name_kr', 'poster_path', 'genre']
        date_field = 'released_date'
        people_fields = ['actor', 'crew']

        for field in general_fields:
            result[field] = self.movie[field]

        result['name'] = result.pop('name_kr')

        d_value = self.movie[date_field]
        result[date_field] = f'{d_value.split("-")[0]}년' if d_value else None

        for field in people_fields:
            result[field] = [elem['name'] for elem in self.movie[field]]

        return result


def list_from(response: dict) -> dict:
    hits = response['hits']['hits']

    if len(hits) == 0:
        return {'result not found': None}

    result = {'movies': []}
    for hit in hits:
        result['movies'].append(MovieDetail(hit).parse())
    return result


def detail_from(response: dict) -> dict:
    hits = response['hits']['hits']

    if len(hits) == 0:
        return {'result not found': None}

    return MovieDetail(hits[0]).parse(details=True)
