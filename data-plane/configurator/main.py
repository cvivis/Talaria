from app.requests import Requests
from app.constant import constant
from app.file_utils import *
from app.configurator.generator import Generator
from app.configurator.executor import Executor

import time

from datetime import datetime


if __name__ == '__main__':
    write_file(constant.BLUE_CONFIG_PATH, 'api_gateway.conf', '')

    generator = Generator()
    requests = Requests()

    prev = datetime.now()
    while True:
        current = datetime.now()
        if current.hour != prev.hour:
            Executor.rotate_current_log(current)

        generator.setup_green()
        generator.generate_configs(requests.services())
        Executor.switch_configs_blue_to_green()
        # Executor.reload_nginx()

        prev = current
        time.sleep(3)
