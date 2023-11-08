from app.configurator.conf.base import BaseConf
from app.file_utils import *
from app.constant import constant

from os.path import join


class APIGatewayConf(BaseConf):
    def __init__(self, name: str, quotas: list):
        super().__init__(name)
        self.quotas = quotas

    def generate(self) -> dict:
        part1_path = join(constant.STATIC_CONFIG_PATH, 'api_gateway_part1.txt')
        part2_path = join(constant.STATIC_CONFIG_PATH, 'api_gateway_part2.txt')

        block = 'include api_backends.conf;\n'
        block += 'include api_keys.conf;\n\n'
        block += self._get_limit_req_zones()
        block += read_file(part1_path)
        block += f'\tserver_name {constant.DATA_PLANE_DOMAIN};\n\n'
        block += read_file(part2_path)

        return {'name': self.name, 'content': block}

    def _get_limit_req_zones(self):
        req_zones = ''

        for quota in self.quotas:
            req_zones = f'limit_req_zone $http_apikey zone=apikey_{quota}rs:1m rate={quota}r/s;\n'

        return f'{req_zones}\n'
