# Building docker
 mvn compile jib:dockerBuild

# Running docker in local
docker run -p 8080:8080 -v /Users/Raghu/Downloads/:/var/Downloads -e GOOGLE_APPLICATION_CREDENTIALS='/var/Downloads/firestore-svc.json' -e GOOGLE_CLOUD_PROJECT='event-hub-249001' -t gcr.io/event-hub-249001/event-hub-dao:0.0.1-SNAPSHOT

# Pushing to container registry
mvn compile jib:build



kubectl --namespace=dev create secret docker-registry gcr-json-key-1 \
          --docker-server=https://gcr.io \
          --docker-username=_json_key \
          --docker-password="$(cat ~/Downloads/firestore-svc.json)" \
          --docker-email=porumamilla_raghu@yahoo.com
