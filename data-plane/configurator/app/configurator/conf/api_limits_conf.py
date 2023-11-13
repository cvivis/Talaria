from app.configurator.conf.base import BaseConf


class APILimitsConf(BaseConf):
    def __init__(self, name: str, quotas: list):
        super().__init__(name)
        self.quotas = quotas

    def generate(self) -> dict:
        block = self._get_limit_req_zones()
        block += '# vim: syntax=nginx\n'

        return {'name': self.name, 'content': block}

    def _get_limit_req_zones(self):
        req_zones = ''

        for quota in self.quotas:
            req_zones = f'limit_req_zone $http_apikey zone=apikey_{quota}rs:10m rate={quota}r/s;\n'

        return f'{req_zones}\n'
