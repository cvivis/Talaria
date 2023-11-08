ARG PYTHON_VERSION

FROM python:${PYTHON_VERSION}

WORKDIR /code

COPY ./requirements.txt /code/requirements.txt
COPY ./app /code/app
COPY ./commands /code/commands
COPY ./confs /code/confs

RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt

CMD ["python3", "app/main.py"]
