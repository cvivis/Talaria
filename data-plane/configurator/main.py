from app.utils.requests import Requests
from app.configurator.generator import Generator
from app.configurator.executor import Executor

import time


if __name__ == '__main__':
    generator = Generator()
    requests = Requests()

    while True:
        generator.setup_green()
        generator.generate_configs(requests.services())
        Executor.switch_configs_blue_to_green()
        Executor.reload_nginx()

        time.sleep(3)
