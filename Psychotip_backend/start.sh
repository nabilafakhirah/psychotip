#!/bin/bash
python manage.py makemigrations
python manage.py migrate
python manage.py collectstatic

echo Starting Gunicorn.
exec gunicorn Psychotip_backend.wsgi:application \
    --bind 0.0.0.0:22505 \
    --workers=3
