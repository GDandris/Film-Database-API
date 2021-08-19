# Film-Database-API

This project is a REST API for a film database where we can save 2 entities, Films and Directors.
There is a relation between them as every movie has a director(ManyToOne).
The project uses JPARepositories to persist these java objects into relational databases.
There is a controller for both entities:

"/movies" 					    GET: List all movies
"/movies"			 		    POST: add new movies
"/movies/year/{year}" 			GET: List all movies from given year
"/movies/{id}"       		    GET: Return movie with given id
"/movies/{id}"       		    PUT: Edit movie with given id
"/movies/{id}"      		    DELETE: Delete movie with given id
"/movies/director/{directorId}" GET: List all movies from director with given id
"/movies/director/{directorId}" DELETE: Delete all movies from director with given id


"/director"       GET: List all directors
"/director"       POST: add new director
"/dirtector/{id}" PUT: Edit director with given id
"/dirtector/{id}" DELETE: delete director by id (deletes every movie by director first) 
