from datetime import datetime
from typing import List, Optional, Any


class SearchCondition:
    page: int
    size: int

    name: str
    plot: str

    people: List[str]

    genres: List[str]
    keywords: List[str]
    companies: List[str]
    ratings: List[str]

    s_date: Optional[datetime]
    e_date: Optional[datetime]

    from_: int = 0

    def __init__(self, page: int | None = 0, size: int = 10,
                 name: str | None = None, plot: str | None = None, people: str | None = None,
                 genres: str | None = None, keywords: str | None = None,
                 companies: str | None = None, ratings: str | None = None,
                 s_date: str | None = None, e_date: str | None = None):

        self.page = page
        self.size = size
        self.name = name
        self.plot = plot
        self.people = None if not people else people.split(',')
        self.genres = None if not genres else genres.split(',')
        self.keywords = None if not keywords else keywords.split(',')
        self.companies = None if not companies else companies.split(',')
        self.ratings = None if not ratings else ratings.split(',')
        self.s_date = s_date
        self.e_date = e_date
        self._initialize()

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

        if self.plot:
            wildcard = {'wildcard': {'plot': f'*{self.plot}*'}}
            query['bool']['should'].append(wildcard)

        if self.people:
            for person in self.people:
                actor = {'match': {'actor.name': person}}
                crew = {'match': {'crew.name': person}}
                query['bool']['should'].append(actor)
                query['bool']['should'].append(crew)

        if self.genres:
            for genre in self.genres:
                match = {'match': {'genre.name': genre}}
                query['bool']['should'].append(match)

        if self.keywords:
            for keyword in self.keywords:
                match = {'match': {'keyword.name': keyword}}
                query['bool']['should'].append(match)

        if self.companies:
            for company in self.companies:
                match = {'match': {'company.name': company}}
                query['bool']['should'].append(match)

        if self.ratings:
            for rating in self.ratings:
                match = {'match': {'rating': rating}}
                query['bool']['should'].append(match)

        if self.s_date or self.e_date:
            range = dict()
            released_date = dict()

            if self.s_date:
                released_date['gte'] = self.s_date
            if self.e_date:
                released_date['lte'] = self.e_date
            released_date['format'] = 'yyyy-MM-dd'

            range['released_date'] = released_date
            query['bool']['filter'].append({'range': range})

        return query


def get_detail_query(movie_id: int) -> dict:
    return {'bool': {'must': {'match': {'tmdb_id': movie_id}}}}
