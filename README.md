# docker build
```sh
docker build -t ecard-app .
```

# check the docker settings for Environment Variables
```sh
docker inspect ecard-app
```

# docker run
```sh
docker run --env-file .env -p 8080:8080 ecard-app
```
