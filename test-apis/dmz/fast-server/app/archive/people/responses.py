from typing import List


class PeopleDetail:

    people: dict
    str_fields: List[str]
    content_field: str

    def __init__(self, detail: dict):
        self.people = dict()
        self.source = detail['_source']
        self._init_people()

    def _init_people(self):
        self.str_fields = ['name_kr', 'name_en', 'profile_path',
                           'birth', 'death', 'gender', 'field']
        self.content_field = 'content'

        self.people['id'] = self.source['tmdb_id']

        for field in self.str_fields:
            self.people[field] = None

        self.people[self.content_field] = []

    def parse(self, details: bool = False) -> dict:
        for field in self.str_fields:
            if self.source.get(field) == 'unknown':
                continue
            self.people[field] = self.source.get(field)

        self._parse_contents(self.people[self.content_field], self.content_field)

        if details:
            return self.people

        return self._to_dict()

    def _parse_contents(self, target: list, field: str):
        if not self.source.get(field):
            return

        for value in self.source[field]:
            content = dict()
            content['id'] = value.get('tmdb_id')
            content['name'] = value.get('name')
            content['date'] = value.get('date')
            content['poster_path'] = value.get('poster_path')
            content['role'] = value.get('role')
            content['character'] = value.get('character')
            target.append(content)

    def _to_dict(self) -> dict:
        general_fields = ['id', 'name_kr', 'gender', 'birth',
                          'field', 'profile_path']

        result = dict()
        for field in general_fields:
            result[field] = self.people[field]

        result['name'] = result.pop('name_kr')
        return result


def list_from(response: dict) -> dict:
    hits = response['hits']['hits']

    if len(hits) == 0:
        return {'result not found': None}

    result = {'people': []}
    for hit in hits:
        result['people'].append(PeopleDetail(hit).parse())
    return result


def detail_from(response: dict) -> dict:
    hits = response['hits']['hits']

    if len(hits) == 0:
        return {'result not found': None}

    return PeopleDetail(hits[0]).parse(details=True)
