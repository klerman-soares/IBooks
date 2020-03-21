# Books Online

Hello everybody!

Changes in release 2

This is a bookstore web sample application using Spring Boot, Spring Secure, Spring Data, Thymeleaf, H2 Database 
and Bootstrap.

ref: 3

It's excellent for learning purposes, showing how these technologies fit together.

### Functionalities:

1. **Domain objets**

Books, Category and Author. All with the normal CRUD operations.

Books can belong to many categories and be written by many authors.

2. **Spring Security**

It uses forms-based authentication with custom login and logout.

There are three roles defined: user, admin, admin, book:
* Admin can manage categories, books, and authors.
* Admin_book can manage only books. 
* User can access features outside the administration area (not implemented yet).
Some text
