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

        block = read_file(part1_path)
        block += f'\tserver_name {constant.DATA_PLANE_DOMAIN};\n\n'
        block += read_file(part2_path)

        return {'name': self.name, 'content': block}
