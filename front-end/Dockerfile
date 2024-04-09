FROM node:20.11.1-alpine3.19

COPY package.json package-lock.json ./

RUN npm install

COPY . .

ARG DISABLE_TESTS
RUN if [ "$DISABLE_TESTS" != "true" ]; then npm run build; fi

CMD ["npm", "start"]
