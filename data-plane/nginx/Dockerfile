ARG NGINX_VERSION

FROM nginx:${NGINX_VERSION}

COPY nginx.conf ./etc/nginx/nginx.conf
COPY ./talaria_conf.d/api_gateway.conf ./etc/nginx/talaria_conf.d/api_gateway.conf
