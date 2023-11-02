class ElasticsearchNotConnectedException(Exception):
    def __str__(self):
        return 'Could not connect to Elasticsearch.'
