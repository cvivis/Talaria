ARG PYTHON_VERSION

FROM python:${PYTHON_VERSION}

WORKDIR /code

COPY ./requirements.txt /code/requirements.txt

RUN pip install --no-cache-dir --upgrade -r /code/requirements.txt

COPY ./main.py /code/main.py
COPY ./app /code/app
COPY ./commands /code/commands
COPY ./confs /code/confs
COPY ./data /code/data

CMD ["python3", "main.py"]
