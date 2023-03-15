FROM node:18.13.0 as build-stage
WORKDIR /var/jenkins_home/workspace/cosmos_deploy/front
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# 실제 컨테이너로 생성될 이미지를 생성하는 부분
FROM nginx:stable-alpine as production-stage
COPY --from=build-stage /var/jenkins_home/workspace/cosmos_deploy/front/dist /usr/share/nginx/html
COPY --from=build-stage /var/jenkins_home/workspace/cosmos_deploy/front/deploy_conf/nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

# nginx를 백그라운드에서 실행하는 컨테이너를 실행하는 명령어
CMD ["nginx", "-g","daemon off;"]
