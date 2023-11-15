from pydantic import BaseSettings


class Settings(BaseSettings):
    HOST: str = '0.0.0.0'
    PORT: int = 8000
    ELASTIC_USERNAME: str
    ELASTIC_PASSWORD: str
    MOVIE_INDEX: str
    DRAMA_INDEX: str
    PEOPLE_INDEX: str
    GENRE_INDEX: str
    KEYWORD_INDEX: str
    COMPANY_INDEX: str
    RATING_INDEX: str
    FIELD_INDEX: str
    CHANNEL_INDEX: str

    class Config:
        env_file = '.env'

    def describe_settings(self):
        return {'host': self.HOST,
                'port': self.PORT,
                'username': self.ELASTIC_USERNAME,
                'password': self.ELASTIC_PASSWORD}


def get_settings() -> Settings:
    return Settings()
