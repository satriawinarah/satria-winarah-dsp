# Database
Prepare the database with name `dsp` and create the table below:
```
CREATE TABLE public.users (
	id uuid NOT NULL,
	"name" varchar(60) NOT NULL,
	"password" varchar(60) NOT NULL,
	phone_number varchar(20) NOT NULL,
	CONSTRAINT users_pkey PRIMARY KEY (id)
);
```
***
# How to Run
- Ensure you are using Java 17 for the project and gradle
- Create Database with name `dsp` in your PostgreSQL
- Run the service: `./gradlew clean build bootRun`
- Open Swagger: http://localhost:8080/swagger-ui/index.html
- or use CURLs below
***
# How to Run as Docker Container
Run commands below in project directory where Dockerfile located.
####Build
```
docker build -t satria-winarah-dsp .
```
####Run
```
docker run -p 8080:8080 satria-winarah-dsp
```
<br/>
<i>*note if you are running PostgreSQL inside docker</i>

you need to create network like this:
create the network
```
docker network create dsp-postgresql-network
```

run the postgresql container
```
docker run --name dsp-postgresql --network dsp-postgresql-network -p 5432:5432 -e POSTGRES_PASSWORD=postgres -d postgres
```

run satria-winarah-dsp service
```
docker run --name dsp-app --network dsp-postgresql-network -p 8080:8080 -d satria-winarah-dsp
```

change your database hostname in `application.yml` to `dsp-postgresql`
***
# CURLs Sample
## Registration
```
curl -X POST "http://localhost:8080/api/v1/auth/registration" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"name\": \"Satria\", \"password\": \"Satria123!\", \"phoneNumber\": \"082367216721\"}"
```
## Login
```
curl -X POST "http://localhost:8080/api/v1/auth/login" -H "accept: */*" -H "Content-Type: application/json" -d "{ \"password\": \"Satria123!\", \"phoneNumber\": \"081574905117\"}"
```
## Get Name
```
curl -X GET "http://localhost:8080/api/v1/user" -H "accept: */*" -H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIwODE1NzQ5MDUxMTciLCJleHAiOjE2ODM1MzAzNzUsImlhdCI6MTY4MzUzMDMxNX0.WSMltswLfa5sO5OFleUqDaiZ4oiDiVM-IPU0syugO2wT0_4wjAcs3dY9xW4oi6meWAMtHMhk016jTHSqLIWs4Sgg2L5o5idoC24NlwvfJ8nAloJErfFblRWCxEpt55FNv9nbmr8y30PnT07-NdVpvBG7bUNiXB1K957_LCndAO83wjQwv4ATE9ddjZNqg9b-ZxnfmSomEsH2BTnnNG2rj_LfDBfOxXNGcge2fJxoFqzDD0DUijoN8c2MdNhRaqCOjo-eXPvfuaQlAU4IkLpaxm4EIjXEzAe14_Xrr1D14H9Tt06RZ2RREmnJgfklox-H-0Wv9Ez-xh5rBwnycGSO3g"
```
## Update Name
```
curl -X PUT "http://localhost:8080/api/v1/user?name=Satria2" -H "accept: */*" -H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9.eyJzdWIiOiIwODE1NzQ5MDUxMTciLCJleHAiOjE2ODM1MzA3MzYsImlhdCI6MTY4MzUzMDY3Nn0.yTy-U5erjZZOb0zLzb6suT29bt4vppjhgi50WhsuhQZkiD07T2-ZAM6pqPtvibkpygnAXVx_-vjEaciKz6y4g9MbDSoutw7_axfiOPGJZZfWvMPqnU1u8nsgGbJpbApUUX7IRT1zJUzh15LgTKHxP3kaqZDA61y7i77hhLzM-ua9HstpBjfNypglxQi-x33_IF0pK8Szujs7vbl1_2JIOTt143RjCVK9-s81E7WgJhkU6V0IRedHNhmgTFfwolHlVTLQ-alm2jYF0fNoeNIopmR-JgWlegTrtbGI8xpntLeBc09pTSZui_h2Za6Ors7w6e7rFvvfW5UjqGFk_1PgHQ"
```