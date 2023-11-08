class Parser:
    def __init__(self, services: dict):
        self.services = services

    def servers(self) -> dict:
        server_dict = dict()

        for service in self.services:
            dept_name = service['info']['dept_name']
            group_name = service['info']['group_name']
            name = f'{dept_name}_{group_name}'

            oas = service['oas']
            servers = []
            for server in oas['servers']:
                servers.append(server['url'])
            server_dict[name] = servers

        return server_dict

    def quotas(self) -> list:
        quota_set = set()

        for service in self.services:
            quota = service['policies']['quota']
            quota_set.add(quota)

        return list(quota_set)

    def quota(self, index: int) -> int:
        service = self.services[index]
        return service['policies']['quota']

    def keys(self) -> dict:
        general_keys, service_keys = dict(), dict()

        for service in self.services:
            service_users = []
            for key_items in service['keys']:
                key, user_id = key_items['key'], key_items['id']
                general_keys[user_id] = key
                service_users.append(user_id)

            service_name = self.service_name(service)
            service_keys[service_name] = service_users

        return {'general': general_keys, 'service': service_keys}

    def service_name(self, index: int) -> str:
        service = self.services[index]
        dept_name = service['info']['dept_name']
        group_name = service['info']['group_name']
        return f'{dept_name}_{group_name}'

    def location(self, index: int) -> str:
        service = self.services[index]
        dept_name = service['info']['dept_name']
        group_name = service['info']['group_name']
        return f'/{dept_name}/{group_name}/'

    def whitelist(self, index: int) -> list:
        service = self.services[index]
        return [ip['ip'] for ip in service['policies']['whitelist']]

    def apis(self, index: int) -> dict:
        service = self.services[index]
        return service['oas']['paths']

    def api_information(self, index: int) -> dict:
        location = self.location(index)
        service_name = self.service_name(index)
        whitelist = self.whitelist(index)
        quota = self.quota(index)
        apis = self.apis(index)

        return {'location': location,
                'service_name': service_name,
                'whitelist': whitelist,
                'quota': quota,
                'apis': apis}
