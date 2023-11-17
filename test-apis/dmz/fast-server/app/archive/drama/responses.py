from typing import List


class DramaDetail:

    drama: dict
    str_fields: List[str]
    list_fields: List[str]
    episode_field: str
    people_fields: List[str]
    source: dict

    def __init__(self, detail: dict):
        self.drama = dict()
        self.source = detail['_source']
        self._init_drama()

    def _init_drama(self):
        self.str_fields = ['name_kr', 'name_en', 'poster_path',
                           'rating', 'plot', 'channel', 'total_episodes',
                           'start_date', 'end_date']
        self.list_fields = ['genre', 'keyword', 'company']
        self.episode_field = 'episode'
        self.people_fields = ['actor', 'crew']

        self.drama['id'] = self.source['tmdb_id']

        for field in self.str_fields:
            self.drama[field] = None

        self.drama[self.episode_field] = []

        for field in self.list_fields + self.people_fields:
            self.drama[field] = []

    def parse(self, details: bool = False) -> dict:
        for field in self.str_fields:
            self.drama[field] = self.source.get(field)

        for field in self.list_fields:
            self._parse_iteratively(self.drama[field], field)

        self._parse_episodes(self.drama[self.episode_field], self.episode_field)

        for field in self.people_fields:
            self._parse_people(self.drama[field], field)

        if details:
            return self.drama

        return self._to_dict()

    def _parse_iteratively(self, target: list, field: str):
        if not self.source.get(field):
            return

        for value in self.source[field]:
            target.append(value.get('name'))

    def _parse_episodes(self, target: list, field: str):
        if not self.source.get(field):
            return

        for value in self.source[field]:
            episode = dict()
            episode['nth'] = value.get('nth')
            episode['overview'] = value.get('overview')
            target.append(episode)

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
        date_field = 'start_date'
        people_fields = ['actor', 'crew']

        for field in general_fields:
            result[field] = self.drama[field]

        result['name'] = result.pop('name_kr')

        d_value = self.drama[date_field]
        result[date_field] = f'{d_value.split("-")[0]}년' if d_value else None
        result['released_date'] = result.pop('start_date')

        for field in people_fields:
            result[field] = [elem['name'] for elem in self.drama[field]]

        return result


def list_from(response: dict) -> dict:
    hits = response['hits']['hits']

    if len(hits) == 0:
        return {'result not found': None}

    result = {'dramas': []}
    for hit in hits:
        result['dramas'].append(DramaDetail(hit).parse())
    return result


def detail_from(response: dict) -> dict:
    hits = response['hits']['hits']

    if len(hits) == 0:
        return {'result not found': None}

    return DramaDetail(hits[0]).parse(details=True)
