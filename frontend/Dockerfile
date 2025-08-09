# Build stage
FROM node:20-alpine AS build
WORKDIR /app
COPY package.json package-lock.json* pnpm-lock.yaml* yarn.lock* ./
RUN if [ -f package-lock.json ]; then npm ci; elif [ -f yarn.lock ]; then yarn install --frozen-lockfile; elif [ -f pnpm-lock.yaml ]; then npm i -g pnpm && pnpm i --frozen-lockfile; else npm i; fi
COPY . .
ARG VITE_API_URL
ENV VITE_API_URL=${VITE_API_URL}
RUN npm run build

# Run stage
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]


