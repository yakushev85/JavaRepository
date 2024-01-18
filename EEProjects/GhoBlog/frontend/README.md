# GhoBlog

This project was created using Angular, Spring Boot, PodtgreSQL and Docker.

## Step 1

Compile backend app. In nackend subfolder `mvn install`.

## Step 2

Compile frontend app. In frontend subfolder run `ng build gho-blog --configuration production`.

## Step 3

Run docker using `docker compose up` in root folder.

## Test

Open in browser http://0.0.0.0:4200 or http://localhost:4200.

Or copy content from gho-blog/dist/ui-shop-app/ to .../src/main/resources/static/ and can be opened in browser via http://0.0.0.0/ or http://localhost/.

