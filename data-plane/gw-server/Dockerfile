ARG PYTHON_VERSION

FROM python:${PYTHON_VERSION}

WORKDIR /code

COPY ./app /code/app
COPY ./commands /code/commands
COPY ./confs /code/confs

CMD ["python3", "app/main.py"]
