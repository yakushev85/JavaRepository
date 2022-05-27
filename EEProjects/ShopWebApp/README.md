# WebShopApp

This project was created using Angular, Spring Boot, MySQL and Docker.

## Step 1

Compile backend app. In subfolder ShopWebApp/ run `mvn install`.

## Step 2

Compile frontend app. In subfolder ui-shop-app/ run `ng build ui-shop-app --configuration production`.

## Step 3

Run docker using `docker compose up` in root folder.

## Test

Open in browser http://0.0.0.0:4200/ or http://localhost:4200/.

Or copy content from ui-shop-app/dist/ui-shop-app/ to ShopWebApp/src/main/resources/static/ and can be opened in browser via http://0.0.0.0:8080/ or http://localhost:8080/.
