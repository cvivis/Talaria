from app.configurator.conf.base import BaseConf


class APIKeysConf(BaseConf):
    def __init__(self, name: str, keys: dict):
        super().__init__(name)
        self.keys = keys

    def generate(self) -> dict:
        block = ''

        block += self._get_key_user_map()
        for service_name, service_users in self.keys['service'].items():
            block += self._get_service_key_map(service_name, service_users)
        block += '# vim: syntax=nginx\n'

        return {'name': self.name, 'content': block}

    def _get_key_user_map(self) -> str:
        key_user_map = 'map $http_apikey $api_client_name {\n'
        key_user_map += '\tdefault "";\n\n'

        for user_name, user_key in self.keys['general'].items():
            key_user_map += f'\t"{user_key}" "{user_name}";\n'

        key_user_map += '}\n\n'
        return key_user_map

    def _get_service_key_map(self, service_name: str, users: list) -> str:
        user_map = f'map $api_client_name $is_{service_name} {{\n'
        user_map += '\tdefault 0;\n\n'

        for user_name in users:
            user_map += f'\t"{user_name}" 1;\n'

        user_map += '}\n\n'
        return user_map
