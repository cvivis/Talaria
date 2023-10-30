from elasticsearch import AsyncElasticsearch


class ElasticConfig:

    HOST: str
    PORT: int
    USERNAME: str
    PASSWORD: str

    def __init__(self, config: dict):
        self.HOST = config['host']
        self.PORT = config['port']
        self.USERNAME = config['username']
        self.PASSWORD = config['password']
        self._initialize()

    def _initialize(self):
        self.client = AsyncElasticsearch(
            hosts=[self._describe_host()],
            basic_auth=self._describe_auth())

    def _describe_host(self) -> dict:
        setting = dict()
        setting['host'] = self.HOST
        setting['port'] = self.PORT
        setting['scheme'] = 'http'
        return setting

    def _describe_auth(self) -> tuple:
        return self.USERNAME, self.PASSWORD

    def get_client(self) -> AsyncElasticsearch:
        return self.client
