# Person Micro Service with UI

## About
This micro service provides REST APIs and Angular App for person management. The REST API response format is always JSON. 
The data is stored in a portable file-based database like H2 to keep it simple in this example. There is one existing REST API `GET /api/persons` which returns all existing persons.

## Notice
You can change everything in the code. Important is only that the micro service is working as described. 

## Requirements
- Spring Boot 3+
- Tailwind 3+
- Java 17+
- Maven 3+

## Tasks
0. __PERSISTENCE.__ Implement a persistence layer with Spring Boot by using an file-based database like H2 (recommended, but you can choose other portable file-based database if you want). Persist the example data listed in the class `InMemoryDataSource` to the database. Implement persistence / database access classes to create, read, update and delete those data entries (basic CRUD operations). 

1. __BACKEND.__ Implement a new REST API endpoint `GET /api/persons/summary` that counts and sorts (ascending by name) the existing names and returns a response e. g.
```json
{
  "Thomas": 2,
  "Evelin": 1, 
  "Oliver": 4,
  //etc.
}
```

Validate your solution by using unit tests. 

2. __BACKEND.__ Add a new field to the entity `Person` called `birthday` (e. g. 25-12-1985) and use a suitable data type. Add different birthdays to all the persons in the database.
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

3. __BACKEND.__ Implement a new REST API `POST /api/person` that adds a new person to the database. The person is defined in the REST API / HTTP body as:

```json
{
  "id": "t9620",
  "name": "Tim",
  "birthday": "20-11-1999" // or your defined format before
}
```

Validate if the fields are correct (name not empty, valid birthday format, id length is starting with a character followed by four digits) and that the id is unique in the database.

The REST API returns `HTTP OK 200` on success, `HTTP BAD REQUEST 400` on format validation or unique errors 
and `HTTP INTERNAL ERROR 500` on all other internal server errots.

4. __FRONTEND.__ Create a new Angular app with TailwindCSS from scratch with following features:
- A representive Tailwind CSS UI is implemented 
- Add a new page with a form for creating new persons with three inputs: `id`, `name` and `birthday`.
  - Add a client side validation UI for these fields as described in the task before. 
  - Implement a REST API call to create this new person in the backend service. 
  - Handle the backend status codes properly (HTTP 200, 400 and 500) and show related messages in the UI.
  - Show a message if the person is created successfully or there was an error. 
- Add a new page with a list to show all persons in a table.
  - Add a additional filter input field (text input) to enable a textual filter for person names
  - The table should be filtered on the fly in the UI

5. __Questions__ (do not implement, write your solution only as text)
- How do you validate that your code is working properly and why?
- How would you store multiple documents (> 5 MB) for persons and why?
- How would you integrate a authentication for the REST APIs? Which authentication would you use and why?
