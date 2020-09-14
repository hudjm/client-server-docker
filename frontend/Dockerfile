
FROM node as frontend

RUN mkdir -p /frontend
WORKDIR /frontend
# add app
COPY . ./

# add `/app/node_modules/.bin` to $PATH
ENV PATH /app/node_modules/.bin:$PATH

COPY package.json ./
COPY package-lock.json ./

RUN npm install --silent
RUN npm install react-scripts@3.4.0 --silent

# add app
COPY . ./

EXPOSE 3000

# start app
CMD ["npm", "start"]