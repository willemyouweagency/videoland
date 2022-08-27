Routes are the following:

POST
http://localhost:8080/customer_login

{
"email": "willem@gmail.com",
"password": "password"
}


----------------------------------

GET
http://localhost:8080/categories/{email}

--------------------------------------

POST
http://localhost:8080/subscribe_category


{
"email": "willem@gmail.com",
"availableCategory":"DUTCH_FILMS"
}

----------------------------------------

POST
http://localhost:8080/share_category

{
"email": "willem@gmail.com",
"customer": "henk@gmail.com",
"availableCategory":"INTERNATIONAL_FILMS"
}




--------------------------------------

TD:

- Add integration test: @WebMvcTest
- Add JSON web token after authentication and authorize every request using JWT
