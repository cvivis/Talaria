import os

from os.path import join
from pathlib import Path


class Constant:
    @property
    def MODE(self):
        return os.environ.get('MODE')

    @property
    def CONFIG_PATH(self):
        return '/nginx'

    @property
    def GREEN_CONFIG_PATH(self):
        return join(self.CONFIG_PATH, 'green_talaria.d')

    @property
    def BLUE_CONFIG_PATH(self):
        return join(self.CONFIG_PATH, 'talaria.d')

    @property
    def CONTROL_PLANE_DOMAIN(self):
        return os.environ.get('CONTROL_PLANE_DOMAIN')

    @property
    def DESCRIBING_SERVICE_URI(self):
        return os.environ.get('DESCRIBING_SERVICE_URI')

    @property
    def DATA_PLANE_DOMAIN(self):
        return os.environ.get('DATA_PLANE_DOMAIN')

    @property
    def SERVER_PATH(self):
        return Path(os.getcwd())

    @property
    def DATA_PATH(self):
        return join(self.SERVER_PATH, 'data')

    @property
    def COMMAND_PATH(self):
        return join(self.SERVER_PATH, 'commands')

    @property
    def STATIC_CONFIG_PATH(self):
        return join(self.SERVER_PATH, 'confs')

    @property
    def LOGGING_PATH(self):
        return '/var/log'


constant = Constant()
