# Event Hub DAO Micro service
This micro service is responsible in performing CRUD operations needed for Event Hub services.

## Building docker
 mvn compile jib:dockerBuild

## Running docker in local
docker run -p 8080:8080 -v /Users/Raghu/Downloads/:/var/Downloads -e GOOGLE_APPLICATION_CREDENTIALS='/var/Downloads/firestore-svc.json' -e GOOGLE_CLOUD_PROJECT='event-hub-249001' -t gcr.io/event-hub-249001/event-hub-dao:0.0.1-SNAPSHOT

## Pushing to container registry
mvn compile jib:build
