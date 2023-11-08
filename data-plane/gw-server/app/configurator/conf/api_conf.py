from app.configurator.conf.base import BaseConf

import re


class APIConf(BaseConf):
    def __init__(self, name: str, api_information: dict):
        super().__init__(name)
        self.location = api_information['location']
        self.service_name = api_information['service_name']
        self.whitelist = api_information['whitelist']
        self.quota = api_information['quota']
        self.apis = api_information['apis']

    def generate(self):
        block = f'location {self.location} {{\n'
        block += self._get_ip_block()
        block += f'\taccess_log /var/log/nginx/{self.service_name}_api.log talaria_log_format;\n\n'
        block += self._get_limit_block()
        block += self._get_auth_block()
        block += self._get_apis_block()
        block += '\treturn 404;\n'
        block += '}\n\n'
        block += '# vim: syntax=nginx'

        return {'name': self.name, 'content': block}

    def _get_ip_block(self) -> str:
        block, ips = '', [ip['ip'] for ip in self.whitelist]

        for ip in ips:
            block += f'\tallow {ip};\n'
        block += '\tdeny all;\n'
        block += '\terror_page 403 = $403;\n\n'

        return block

    def _get_limit_block(self) -> str:
        block = f'\tlimit_req zone=apikey_{self.quota}rs;\n'
        block += '\tlimit_req_status 429;\n\n'
        return block

    def _get_auth_block(self) -> str:
        block = f'\tauth_request /_vallidate_apikey;\n'
        block += f'\tif ($is_{self.service_name} = 0) {{\n'
        block += '\t\treturn 403;\n'
        block += '\t}\n\n'
        return block

    def _get_apis_block(self) -> str:
        block = ''

        for api_uri in self.apis.keys():
            methods = list(self.apis[api_uri].keys())
            block += self._get_api_block(api_uri, methods)

        return block

    def _get_api_block(self, api_uri: str, methods: list) -> str:
        pattern = r'\{([^/]+)\}'
        matches = re.findall(pattern, api_uri)

        if len(matches) == 0:
            block = f'\tlocation = {api_uri} {{\n'
        else:
            new_uri = re.sub(pattern, r'[^/]+', api_uri)
            block = f'\tlocation ~ ^{new_uri}$ {{\n'

        methods_str = ' '.join(methods)
        block += f'\t\tlimit_except ' + methods_str.upper() + ' {\n'
        block += '\t\t\tdeny all;\n'
        block += '\t\t}\n'
        block += '\t\terror_page 403 = @405;\n'
        block += f'\t\tproxy_path http://{self.service_name};\n'
        block += '\t}\n\n'
        return block
