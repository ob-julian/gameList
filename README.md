# Game Listing

Game Listing is a web application based on Spring Boot and Thymeleaf. For the moment Thyemleaf is used for the frontend but a Angular frontend is in development.

## Features

- CRUD operations for games
    - R(ead) operations are available for all users
    - CUD operations are only available for authenticated users
- User authentication via Spring Security
- User roles 
- H2 database
- Docker support

## How use

### Initial setup
On fist run the application will create a default user (admin) with the following credentials:
- username: admin
- password: admin

Login is available via the /login site, e.g. `http://localhost:8080/COTEXT_PATH/login`
> For more info on `CONTEXT_PATH` see the [Production](#production) section

After first Login the System will force you to change the password. I didint put any minimum requirements for the password, exept that it must not be empty.

### User Creation
Only persons with the role `ADMIN` can create new users. The default admin user has this role.

To create a new user, go to the `/addUser` site, e.g. `http://localhost:8080/COTEXT_PATH/addUser`

### Game Creation
Only users with the role `ADMIN` can create new games.
If you are authenticated the main page will have a form for creating new games.

### Editing and Deleting Games
Same as for creating games, the Buttons for editing and deleting are automatically added to every game entry if you are authenticated.

### User Roles
IF you want to create a new Role, you need to do it via the H2 Console, which is available on `http://localhost:8080/COTEXT_PATH/h2-console` but only if you are authenticated.

New Roles will be automatically added to the User Creation Form.

But they will do nothing, because the application at the moment only looks for the `ADMIN` role or authenticated in general.

### Changing Password
If you want to change your password, you can do it via the `/changePassword` site, e.g. `http://localhost:8080/COTEXT_PATH/changePassword`

If you are not authenticated, you need to enter your username, if you are authenticated, the username will be prefilled and hidden.

Further more a /.well-known/ redirect is implemented, so passwordm Managers can automatically find this site/endpoint.

## How to run


### Development

#### Requirements

- java \
  Download and install from [Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- maven \
  Comes with the Project

#### Steps

1. Open a terminal
2. Run the application
    ```bash
    ./mvnw spring-boot:run
    ```
3. The application will be running on `http://localhost:8080`

### Production

#### Requirements

- Docker \
  Download and install from [Docker](https://www.docker.com/products/docker-desktop)
- java \
  Download and install from [Java](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- maven \
    Comes with the Project
#### Steps

1. Open a terminal
    ```
2. Build the application
    ```bash
    ./mvnw clean package
    ```
3. Build the docker image
    ```bash
    docker build -t game-listing .
    ```
4. Run the docker container
    All positional arguments:
    - CONTEXT_PATH: \
    The context path of the application, application will be running on `http://localhost:8080/CONTEXT_PATH`
    - DB_USER: \
    Name of the database user
    - DB_PASSWORD:\
    Password of the database user

    > Please note that the database is accessible on `http://localhost:8080/COTEXT_PATH/h2-console` but only if you are authenticated.

    So thecommand for running the container can be: \
    ```bash
    docker run -p 8080:8080 -e CONTEXT_PATH=/game-listing -e DB_USER=sa -e DB_PASSWORD=password game-listing
    ```

5. The application will be running on `http://localhost:8080/CONTEXT_PATH` dont forget to configure the firewall to allow the port 8080 or put it behind a reverse proxy.

#### Reverse Proxy
the application can be put behind a reverse proxy like Nginx or Apache, here is an example of a Apache configuration:

```apache
<VirtualHost *:80>
    ServerName localhost

    ProxyPreserveHost On
    ProxyPass /gameList http://localhost:8080/gameList 
    ProxyPassReverse /gameList http://localhost:8080/gameList 

    <Location /gameList>
        Require all granted
    </Location>
</VirtualHost>
```