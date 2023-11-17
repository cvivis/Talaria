FROM python:3.11

WORKDIR /test1

COPY ./requirements.txt /test1/requirements.txt

RUN pip install --no-cache-dir --upgrade -r /test1/requirements.txt

COPY ./app /test1/app

EXPOSE 8000

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8000"]
