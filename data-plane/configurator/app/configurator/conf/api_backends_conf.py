from app.configurator.conf.base import BaseConf


class APIBackendsConf(BaseConf):
    def __init__(self, name: str, servers: dict):
        super().__init__(name)
        self.servers = servers

    def generate(self) -> dict:
        blocks = ''

        for name, addresses in self.servers.items():
            block = f'upstream {name} {{\n'
            zone, server_str = '', ''

            if len(addresses) >= 2:
                zone = f'\tzone {name}_service 64k;\n'

            for address in addresses:
                server_str += f'\tserver {address};\n'

            block += f'{zone}{server_str}}}\n\n'
            blocks += block

        return {'name': self.name, 'content': blocks}
