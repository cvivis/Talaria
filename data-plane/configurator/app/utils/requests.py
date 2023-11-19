from app.utils.constant import constant

import requests
import json

from os.path import join


class Requests:
    def __init__(self):
        self.mode = constant.MODE

    def services(self) -> dict:
        if self.mode == 'test':
            return self.get_sample_data()
        elif self.mode == 'prod':
            return self.request_to_control_plane()

    @staticmethod
    def request_to_control_plane() -> dict:
        url = f'https://{constant.CONTROL_PLANE_DOMAIN}/{constant.DESCRIBING_SERVICE_URI}'
        response = requests.get(url)
        print(response.json())
        return response.json()['services']

    @staticmethod
    def get_sample_data() -> dict:
        sample_path = join(constant.DATA_PATH, 'test.json')

        with open(sample_path, 'r') as json_file:
            data = json.load(json_file)

        return data['services']
