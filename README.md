# Person Micro Service

## About
This micro service provides REST APIs for person management. The REST API response format is always JSON. 
The data is stored in a portable database like H2 to keep it simple in this example. There is one existing REST API `GET /api/persons` which returns all existing persons.

## Notice
You can change everything in the code. Important is only that the micro service is working as described. 

## Requirements
- Spring Boot 3+
- Java 17+
- Maven 3+

## Tasks
0. __PERSISTENCE.__ Implement a persistence layer with Spring Boot by using a in-memory database like H2 (recommended, but you can choose other portable database if you want). Persist the example data listed in the class `InMemoryDataSource` to the database. Implement persistence / database access classes to create, read, update and delete those data entries. 

1. __BACKEND.__ Implement a new REST API endpoint `GET /api/persons/summary` that counts and sorts (ascending by name) the existing names and returns a response e. g.
```json
{
  "Thomas": 2,
  "Evelin": 1, 
  "Oliver": 4,
  //etc.
}
```

Using __Java streams__ is the highly preferred solution. If the solution with Java streams is not possible other ways are allowed.
Validate your solution by using unit tests. 

2. __BACKEND.__ Add a new field to the class `Person` called `birthday` (e. g. 25-12-1985) and use a suitable data type. Add different birthdays to all the persons in the in-memory data.
Change the existing REST API `GET /api/persons` and show only the birth year , e. g.
```json
[
  {
    "id": "h2314",
    "name": "Thomas",
    "year": 1982
  },
  {
    "id": "f5962",
    "name": "Thomas",
    "year": 1974
  },
  {
    "id": "e5891",
    "name": "Evelin",
    "year": 1996
  },
  //etc.
]
```

Using __Java streams__ is the highly preferred solution. If the solution with Java streams is not possible other ways are allowed.

3. __BACKEND.__ Implement a new REST API `POST /api/person` that adds a new person in the in-memory data.
Consider that the data is in-memory and not exclusively locked if there are simultaneous calls. The person is defined in the REST API / HTTP body as:

```json
{
  "id": "t9620",
  "name": "Tim",
  "birthday": "20-11-1999" // or your defined format before
}
```

Validate if the fields are correct (name not empty, valid birthday format, id length is starting with a character followed by four digits) and that the id is unique in the in-memory data.

The REST API returns `HTTP OK 200` on success, `HTTP BAD REQUEST 400` on format validation or unique errors 
and `HTTP INTERNAL ERROR 500` on all other internal server errots.

4. __FRONTEND.__ Create a new Angular app from scratch with following features:
- Add a new page with a form for creating new persons with three inputs: `id`, `name` and `birthday`.
  - Add a client side validation for these fields as described in the task before. 
  - Implement a REST API call to create this new person in the backend service. 
  - Handle the backend status codes properly (HTTP 200, 400 and 500) and show related messages in the UI.
  - Show a message if the person is created successfully or there was an error. 
- Add a new page with a list to show all persons in a table.

Using a CSS framework like Bootstrap, TailwindCSS, etc. is not required but you can use one if you want to.

5. Questions (do not implement, write your solution only as text)
- How do you validate that your code is working properly and why?
- How would you store multiple documents (> 5 MB) for persons and why?
- How would you integrate a authentication for the REST APIs? Which authentication would you use and why?
