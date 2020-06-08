FROM nginx:alpine

ADD . /application
WORKDIR /application

COPY default.conf /etc/nginx/conf.d/default.conf
COPY app/build/outputs/apk/debug/app-debug.apk /usr/share/nginx/static/

EXPOSE 80
