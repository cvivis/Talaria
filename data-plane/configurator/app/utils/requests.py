from app.utils.constant import constant

import requests
import json

from os.path import join


class Requests:
    def __init__(self):
        self.mode = constant.MODE

    def services(self):
        if self.mode == 'test':
            return self.get_sample_data()
        else:
            return self.request_to_control_plane()

    @staticmethod
    def request_to_control_plane():
        url = f'https://{constant.CONTROL_PLANE_DOMAIN}/{constant.DESCRIBING_SERVICE_URI}'
        return requests.get(url).text

    @staticmethod
    def get_sample_data():
        sample_path = join(constant.DATA_PATH, 'test.json')

        with open(sample_path, 'r') as json_file:
            data = json.load(json_file)

        return data['services']
