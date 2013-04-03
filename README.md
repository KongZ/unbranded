# CMS (Simple)

This is a CMS Simple REST service that provides a single RESTful endpoint protected by OAuth 2.

## Build
```
mvn package
``` 

## Usage

Test the `content` endpoint:

```sh
curl http://54.169.217.191:8080/content
```

You will receive the following JSON response, which contains the entire Content of the system

```json
[
  {
    "id": 1,
    "title": "Love Story",
    "genres": "Drama,Romance"
  },
  {
    "id": 2,
    "title": "Tae Guk Gi: The Brotherhood of War",
    "genres": "Drama,Action,War"
  }
]
```

Some RESTs are resources protected. If OAuth 2 access token is not defined in the header, you will receive the following JSON response, which indicates you are not authorized to access the resource:

Try
```sh
curl -X PUT -H "Content-Type: application/json" "http://54.169.217.191:8080/user/1" -d '{ "first_name" : "New Name"}'
```

Output
```json
{
  "error": "unauthorized",
  "error_description": "Full authentication is required to access this resource"
}
```

In order to access the protected resource, you must first request an access token via the OAuth 2 handshake. Request OAuth 2authorization:

```sh
curl -X POST -vu clientapp:clientsecret http://54.169.217.191:8080/oauth/token -H "Accept: application/json" -d "password=simple&username=simple&grant_type=password&scope=read%20write&client_secret=clientsecret&client_id=clientapp"
```

A successful authorization results in the following JSON response:

```json
{
  "access_token": "f567f6a7-2a23-4964-8a87-c63d14d33eca",
  "token_type": "bearer",
  "refresh_token": "bfc8d7c8-9ecd-41b3-b08f-e4aa0b8b6f1a",
  "expires_in": 600847,
  "scope": "read write"
}
```

Use the `access_token` returned in the previous request to make the authorized request to the protected endpoint:

```sh
curl -H "Authorization: Bearer f567f6a7-2a23-4964-8a87-c63d14d33eca" "http://54.169.217.191:8080/user/1"
```

If the request is successful, you will see the following JSON response:

```json
{
  "id": 1,
  "first_name": "BFPip"
}
```

After the specified time period, the `access_token` will expire. Use the `refresh_token` that was returned in the original OAuth authorization to retrieve a new `access_token`:

```sh
curl -X POST -vu clientapp:clientsecret http://54.169.217.191:8080/oauth/token -H "Accept: application/json" -d "grant_type=refresh_token&refresh_token=bfc8d7c8-9ecd-41b3-b08f-e4aa0b8b6f1a&client_secret=clientsecret&client_id=clientapp"
```

# API list
### Find a list of user associated with this username
```
Authorization: Bearer {access_token}

GET /access/{username}
```

### Create a new username
```
Authorization: Basic base64({client_id}:{client_secret})

POST /access
{ 
  "first_name" : "First Name",
  "last_name" : "Last Name",
  "username" : "email@gmail.com",
  "password" : "secret"
}
```

### Change password
```
Authorization: Bearer {access_token}

PUT /access/{username}
{ 
  "password" : "new secret"
}
```

### Delete username
```
Authorization: Bearer {access_token}

DELETE /access/{username}
```

### Get user information
``` 
Authorization: Bearer {access_token}

GET /user/{user_id}
```

### Update user information
``` 
Authorization: Bearer {access_token}

PUT /user/{user_id}
{
  "first_name" : "First Name",
  "last_name" : "Last Name"
}
```

### Find all catalogs
```
GET /catalog?page={pageNo}&size={pageSize}
```

### Find catalog by ID
If 'subCatalog' is 'true', return a sub catalog of this catalog too
If 'content' is 'true', return a content of this catalog too
```
GET /catalog/{catalog_id}?[subCatalog=true]&[content=true]
```

### Find root catalog
```
GET /catalog/root
```

### Find all contents
```
GET /content?page={pageNo}&size={pageSize}
```

### Find content by ID
```
GET /content/{content_id}
```

### Search content by Title
```
GET /content/search?q={searchTerm}&page={pageNo}&size={pageSize}
```

### Request a video URL
```
Authorization: Bearer {access_token}

POST /play/{content_id}
```
