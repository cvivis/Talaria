from app.constant import constant
from app.file_utils import *

import subprocess
import shutil

from os.path import join
from datetime import datetime


class Executor:
    @staticmethod
    def switch_configs_blue_to_green():
        try:
            shutil.rmtree(constant.BLUE_CONFIG_PATH)
            logger.info(f'Removed {constant.BLUE_CONFIG_PATH}')
        except FileNotFoundError as e:
            logger.error(f'FileNotFoundError ({e})')
        except PermissionError as e:
            logger.error(f'PermissionError ({e})')
        except Exception as e:
            logger.error(f'Exception ({e})')

        try:
            shutil.copytree(constant.GREEN_CONFIG_PATH, constant.BLUE_CONFIG_PATH)
            logger.info(f'Copied {constant.GREEN_CONFIG_PATH} to {constant.BLUE_CONFIG_PATH}')
        except Exception as e:
            logger.error(f'Exception ({e})')

    @staticmethod
    def reload_nginx():
        reload_path = join(constant.COMMAND_PATH, 'reload_nginx.sh')
        process = subprocess.Popen(['bash', reload_path], stdout=subprocess.PIPE, stderr=subprocess.PIPE)
        stdout, stderr = process.communicate()

        if len(stdout) > 0:
            logger.info({stdout})

        if len(stderr) > 0:
            logger.error(f'Error on Nginx reloading: {stderr}')

    @staticmethod
    def rotate_current_log(current: datetime):
        names = ['access_', 'access_200_', 'access_400_', 'access_500_', 'error_']
        time_format = '%Y_%m_%dT%H'

        for name in names:
            src_name = f'{name}{current.strftime(time_format)}.log'
            dest_name = f'{name}current.log'
            copy_file(constant.NGINX_LOG_PATH, constant.NGINX_LOG_PATH, src_name, dest_name)
