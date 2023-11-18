from app.configurator.conf.base import BaseConf


class APIBackendsConf(BaseConf):
    def __init__(self, name: str, servers: dict, monitoring_port: int):
        super().__init__(name)
        self.servers = servers
        self.monitoring_port = monitoring_port

    def generate(self) -> dict:
        blocks = ''

        # for monitoring in ['monitoring', 'socket']:
        #     blocks += f'upstream {monitoring} {{\n'
        #     blocks += f'\tserver 127.0.0.1:{self.monitoring_port};\n'
        #     blocks += '}\n\n'

        for name, server_dict in self.servers.items():
            block = f'upstream {name} {{\n'
            zone, server_str = '', ''

            if len(server_dict) >= 2:
                zone = f'\tzone {name}_service 64k;\n'

            for address in server_dict:
                server_str += f'\tserver {address}:443;\n'

            block += f'{zone}{server_str}}}\n\n'
            blocks += block
            blocks += '#vim: syntax=nginx\n'

        return {'name': self.name, 'content': blocks}
