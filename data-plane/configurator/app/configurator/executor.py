import os
import re
import signal
import subprocess

from app.constant import constant
from app.file_utils import *

import shutil

from os import listdir, remove
from os.path import join, isdir
from datetime import datetime


class Executor:
    @staticmethod
    def switch_configs_blue_to_green():
        try:
            for item in listdir(constant.BLUE_CONFIG_PATH):
                item_path = join(constant.BLUE_CONFIG_PATH, item)
                if isdir(item_path):
                    shutil.rmtree(item_path)
                else:
                    remove(item_path)
            logger.info(f'Removed contents on {constant.BLUE_CONFIG_PATH}')
        except Exception as e:
            logger.error(f'Failed to remove blue configs ({e})')

        try:
            for item in listdir(constant.GREEN_CONFIG_PATH):
                source_path = join(constant.GREEN_CONFIG_PATH, item)
                destination_path = join(constant.BLUE_CONFIG_PATH, item)
                if isdir(source_path):
                    shutil.copytree(source_path, destination_path)
                else:
                    shutil.copyfile(source_path, destination_path)
            logger.info(f'Copied {constant.GREEN_CONFIG_PATH} to {constant.BLUE_CONFIG_PATH}')
        except Exception as e:
            logger.error(f'Failed to copy green configs ({e})')

    @staticmethod
    def reload_nginx():
        nginx_pid = 1

        try:
            output = subprocess.check_output(['ps', '-eo', 'pid,cmd']).decode('utf-8')
            match = re.search(r'(\d+)\s+nginx: master process', output)
            if match:
                nginx_pid = int(match.group(1))
        except subprocess.CalledProcessError:
            pass

        try:
            os.kill(nginx_pid, signal.SIGHUP)
            logger.info('Reloaded Nginx')
        except Exception as e:
            logger.error(f'Failed to reload Nginx ({e})')

    @staticmethod
    def rotate_current_log(current: datetime):
        names = ['access_', 'access_200_', 'access_400_', 'access_500_', 'error_']
        time_format = '%Y_%m_%dT%H'

        for name in names:
            src_name = f'{name}{current.strftime(time_format)}.log'
            dest_name = f'{name}current.log'
            copy_file(constant.NGINX_LOG_PATH, constant.NGINX_LOG_PATH, src_name, dest_name)
