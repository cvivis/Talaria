from app.constant import constant
from app.configurator.conf.api_backends_conf import APIBackendsConf
from app.configurator.conf.api_gateway_conf import APIGatewayConf
from app.configurator.conf.api_keys_conf import APIKeysConf
from app.configurator.conf.api_conf import APIConf
from app.configurator.parser import Parser
from app.file_utils import *

from os.path import join


class Generator:
    def __init__(self):
        self.green_path = constant.GREEN_CONFIG_PATH
        self.api_conf_path = join(self.green_path, 'api_conf.d')
        self.static_config_path = constant.STATIC_CONFIG_PATH
        self.data_plane_domain = constant.DATA_PLANE_DOMAIN

    def setup_green(self):
        clear_directory(self.green_path)
        make_directory(self.green_path)
        make_directory(self.api_conf_path)

    def generate_configs(self, services: dict):
        parser = Parser(services)
        self._copy_constant_configs()

        api_backends_conf = APIBackendsConf('api_backends.conf', parser.servers()).generate()
        write_file(self.green_path, api_backends_conf['name'], api_backends_conf['content'])

        api_gateway_conf = APIGatewayConf('api_gateway.conf', parser.quotas()).generate()
        write_file(self.green_path, api_gateway_conf['name'], api_gateway_conf['content'])

        api_keys_conf = APIKeysConf('api_keys.conf', parser.keys()).generate()
        write_file(self.green_path, api_keys_conf['name'], api_keys_conf['content'])

        for index, service in enumerate(services):
            service_name = parser.service_name(index)
            api_conf = APIConf(f'{service_name}_api.conf', parser.api_information(index)).generate()
            write_file(self.api_conf_path, api_conf['name'], api_conf['content'])

    def _copy_constant_configs(self):
        copy_file(self.static_config_path, self.green_path, 'api_json_errors.conf')
        copy_file(self.static_config_path, self.green_path, 'json_validation.js')
        copy_file(self.static_config_path, self.green_path, 'custom_format.d')
