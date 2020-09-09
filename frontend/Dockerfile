
FROM node as frontend
WORKDIR /frontend
COPY . /frontend/
RUN npm ci
RUN npm run-script build

