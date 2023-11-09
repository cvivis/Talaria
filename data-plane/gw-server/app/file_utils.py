from app.logging import logger

import shutil

from os.path import exists, join
from pathlib import Path


def clear_directory(directory_path: str) -> None:
    if not exists(directory_path):
        return

    try:
        shutil.rmtree(directory_path)
        logger.info(f'Removed old green config folder on {directory_path}')
    except OSError as e:
        logger.error(f'{e.filename} - {e.strerror}')


def make_directory(directory_path: str) -> None:
    try:
        Path(directory_path).mkdir(parents=True, exist_ok=True)
        logger.info(f'Made new directory on {directory_path}')
    except OSError as e:
        logger.error(f'{e.filename} - {e.strerror}')


def copy_directory(from_path: str, to_path: str, directory_name: str) -> None:
    new_from = join(from_path, directory_name)
    new_to = join(to_path, directory_name)

    try:
        shutil.copytree(new_from, new_to)
    except OSError as e:
        logger.error(f'')


def copy_file(from_path: str, to_path: str, file_name: str, file_name2: str = None) -> None:
    new_from = join(from_path, file_name)
    destination = file_name if file_name2 is None else file_name2
    new_to = join(to_path, destination)

    try:
        shutil.copyfile(new_from, new_to)
        logger.info(f'Copied {from_path} to {to_path}')
    except OSError as e:
        logger.error(f'{e.filename} - {e.strerror}')


def write_file(file_path: str, name: str, content: str) -> None:
    with open(join(file_path, name), 'w') as writing:
        writing.write(content)
    logger.info(f'Generated {name} on {file_path}')


def read_file(file_path: str) -> str:
    with open(file_path, 'r') as reading:
        content = reading.read()

    logger.info(f'Read {file_path}')
    return content
