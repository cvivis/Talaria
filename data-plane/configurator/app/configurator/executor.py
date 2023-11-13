import os
import re
import signal
import subprocess

from app.utils.constant import constant
from app.utils.file_utils import *

import shutil

from os import listdir, remove
from os.path import join, isdir


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
            logger.info(f'Reloaded Nginx, pid: {nginx_pid}')
        except Exception as e:
            logger.error(f'Failed to reload Nginx ({e})')
