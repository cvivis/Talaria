from app.utils.constant import constant

import logging

from os.path import join


class Logger:
    def __init__(self):
        self.logger = logging.getLogger()
        self.logger.setLevel(logging.DEBUG)

        self.formatter = logging.Formatter('%(asctime)s %(levelname)s: %(message)s')

        self.stream_handler = logging.StreamHandler()
        self.stream_handler.setFormatter(self.formatter)

        logging_path = join(constant.LOGGING_PATH, 'configurator.log')
        self.file_handler = logging.FileHandler(logging_path)
        self.file_handler.setFormatter(self.formatter)

        self.logger.addHandler(self.stream_handler)
        self.logger.addHandler(self.file_handler)


logger = Logger().logger
