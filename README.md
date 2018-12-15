# demo

To run app:
1. create database with name "test";
2. run the app 
3.  test api's go to : http://localhost:8080/swagger-ui.html#/

#### BASE_URL : http://localhost:8080

## Items:

GET /item?item_id=? : get single item

GET /item/all : get all items

POST /item : create Item

Request:
{
  "currentStock": 0,
  "id": 0,
  "name": "string",
  "price": 0
}

PUT /item/{itemId} : update Item

Request:
{
  "currentStock": 0,
  "id": 0,
  "name": "string",
  "price": 0
}


DELETE /item?item_id=? : Delete item


## Order:

POST /order : create order

Request

{
  "email": "string",
  "items": [
    {
      "itemId": 0,
      "quantity": 0
    }
  ],
  "mobile": "string"
}


GET /order/all : get all orders








