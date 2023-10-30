from datetime import datetime
from dateutil.relativedelta import relativedelta
from pydantic import BaseModel
from typing import List, Any


class SearchCondition(BaseModel):
    page: int | None = 0
    size: int | None = 5

    name: str | None = None
    fields: List[str] | None = None
    genders: List[str] | None = None
    ages: List[int] | None = None

    from_: int = 0

    def __init__(self, page: int | None = 0, size: int | None = 10,
                 name: str | None = None, fields: str | None = None,
                 genders: str | None = None, ages: str | None = None, **data: Any):

        super().__init__(**data)
        self.page = page
        self.size = size
        self.name = name
        self.fields = None if not fields else fields.split(',')
        self.genders = None if not genders else genders.split(',')
        ages_str = None if not ages else ages.split(',')
        self.ages = None if not ages_str else [int(age) for age in ages_str]

    def _initialize(self):
        self.from_ = self.page * self.size

    def get_query(self) -> dict:
        query = dict()
        query['bool'] = dict()
        query['bool']['should'] = []
        query['bool']['filter'] = []

        if self.name:
            column = 'name_en' if self.name.isascii() else 'name_kr'
            wildcard = {'wildcard': {column: f'*{self.name}*'}}
            query['bool']['should'].append(wildcard)

        if self.fields:
            for field in self.fields:
                match = {'match': {'field.name': field}}
                query['bool']['should'].append(match)

        if self.genders:
            for gender in self.fields:
                match = {'match': {'gender': gender}}
                query['bool']['should'].append(match)

        if self.ages:
            now = datetime.now()
            for age in self.ages:
                if age == -1:
                    exists = {'exists': {'field': 'death'}}
                    query['bool']['should'].append(exists)
                    continue

                range = dict()
                birth = dict()

                s_date = now + relativedelta(years=-age)
                e_date = now + relativedelta(years=-(age - 10))

                if age == 70:
                    birth['lte'] = s_date.strftime('%Y-%m-%d')
                else:
                    birth['gte'] = s_date.strftime('%Y-%m-%d')
                    birth['lte'] = e_date.strftime('%Y-%m-%d')

                birth['format'] = 'yyyy-MM-dd'

                range['birth'] = birth
                query['bool']['filter'].append({'range': range})

        return query


def get_detail_query(person_id: int) -> dict:
    return {'bool': {'must': {'match': {'tmdb_id': person_id}}}}
