
FROM node as frontend
WORKDIR /frontend
COPY . /frontend/
EXPOSE 3000
RUN npm ci
RUN npm run-script build

# start app
CMD ["npm", "start"]
