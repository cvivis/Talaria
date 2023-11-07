ARG PYTHON_VERSION

FROM python:${PYTHON_VERSION}

WORKDIR /code

COPY ./app /code/app

copy ./commands /code/commands

CMD ["python3", "app/main.py"]
