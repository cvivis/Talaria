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
        block += '\tauth_request /_validate_apikey;\n\n'

        block += self._get_apis_block()
        block += '\treturn 404;\n'
        block += '}\n\n'
        block += '# vim: syntax=nginx\n\n'

        return {'name': self.name, 'content': block}

    def _get_ip_block(self) -> str:
        block = ''
        for ip in self.whitelist:
            block += f'\tallow {ip};\n'
        block += '\tdeny all;\n\n'

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
            block = f'\tlocation = {self.location[:-1]}{api_uri} {{\n'
        else:
            new_uri = re.sub(pattern, r'[^/]+', api_uri)
            block = f'\tlocation ~ ^{self.location[:-1]}{new_uri}$ {{\n'

        for num in [401, 403, 405, 429]:
            block += f'\t\terror_page {num} = @{num};\n'
        block += '\n'

        block += f'\t\tlimit_req zone=apikey_{self.quota}rs burst={self.quota} nodelay;\n'
        block += '\t\tlimit_req_status 429;\n\n'

        methods_str = '|'.join(methods)
        block += f'\t\tif ($request_method !~ ^({methods_str.upper()})$) {{\n'
        block += '\t\t\treturn 405;\n'
        block += '\t\t}\n\n'

        block += f'\t\tproxy_pass https://{self.service_name}{api_uri};\n'
        block += '\t\tproxy_set_header Host $host;\n'
        block += '\t\tproxy_set_header X-Real-IP $remote_addr;\n'
        block += '\t\tproxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;\n'
        block += '\t\tproxy_set_header X-Forwarded-Proto $scheme;\n'
        block += '\t}\n'
        return block
