import time
import os


if __name__ == '__main__':
    i = 1

    envs = {
        "EMAIL": os.environ.get("EMAIL")
    }

    with open('../.env', 'w') as env_file:
        for key, value in envs.items():
            env_file.write(f'{key}={value}\n')

    while True:
        print(f'{i}: hello data plane')
        i += 1
        time.sleep(3)
