# Proyecto biblioteca de Spring Boot en version 2.7.8

Este proyecto se ha creado para la asignatura [Tecnologías de Internet Orientadas al Navegador del Máster Universitario en Desarrollo de Aplicaciones y Servicios Web de la Universidad de Alicante](https://cvnet.cpd.ua.es/Guia-Docente/?wcodasi=38215&wlengua=es&scaca=2023-24).

## Configuración
En el fichero ```src/main/resources/application.properties``` se puede configurar el acceso a la base de datos mysql para el almacenamiento de los libros del catálogo.

El proyecto incluye la [librería Jena](https://jena.apache.org) como dependencia en el fichero ```pom.xml``` para el uso de SPARQL.

Por defecto, la aplicación solicita el login. El usuario es ```user``` y el password ```password```. Se puede comprobar en el fichero ```src/main/java/es/ua/biblioteca/
SecurityConfig```.

El fichero ```pom.xml``` incluye las versiones de las librerías utilizadas en este proyecto.

## API REST

El proyecto proporciona los siguientes patrones de URL para dar acceso al API REST:

- Crear libro (POST): localhost:8081/api/book
- Modificar libro (UPDATE): localhost:8081/api/book/{ID}
- Eliminar libro (DELETE): localhost:8081/api/book/{ID}
- Recuperar libro (GET): localhost:8081/api/book/{ID}

Para poder realizar pruebas se puede utilizar la herramienta [Postman](https://www.postman.com/)

## URLs de otros servicios
- Generar fichero PDF con el listado de obras: localhost:8081/api/pdfreport
- Consulta autores de la [Biblioteca Virtual Miguel de Cervantes](https://www.cervantesvirtual.com/) al repositorio [Wikidata](https://query.wikidata.org/): localhost:8081/api/authorsbvmc

## Thymeleaf
Las vistas que proporciona la aplicación son las siguientes:

- localhost:8081/login
- localhost:8081/logout
- Listado de libros: localhost:8081/books
- Formulario para crear un libro: localhost:8081/createBook
- Búsqueda: localhost:8081/searchBook

## Acceso a Wikidata
El proyecto proporciona un servicio para recuperar información de Wikidata. Se utiliza la [siguiente sentencia SPARQL](https://w.wiki/9F33):

```
PREFIX wdt: <http://www.wikidata.org/prop/direct/> 
PREFIX wikibase: <http://wikiba.se/ontology#> 
PREFIX bd: <http://www.bigdata.com/rdf#> 
SELECT DISTINCT ?autor ?autorLabel 
WHERE { 
  ?autor wdt:P2799 ?idbvmc. 
  SERVICE wikibase:label { bd:serviceParam wikibase:language "es" } 
} LIMIT 10
```

## Posibles mejoras
- Modificar el aspecto del fichero PDF con el listado de obras: localhost:8081/api/pdfreport
- Parametrizar número de resultados de la sentencia SPARQL
- Modificar la sentencia SPARQL para recuperar otro tipo de contenido a partir del repositorio de Wikidata. Para ello podéis consultar información sobre cómo [crear consultas SPARQL](https://data.cervantesvirtual.com/noticia/tutorial-de-inicio-a-sparql), [ejemplos de Wikidata](https://www.wikidata.org/wiki/Wikidata:SPARQL_query_service/queries/examples/es) y [otras iniciativas](https://github.com/hibernator11/hdh-compartir-pantalla-2023).
- Añadir CSS a las plantillas de Thymeleaf
- Añadir nuevas propiedades a la entidad libro
- Añadir el identificador al listado de libros
- Modificar el formulario de búsqueda para poder buscar por otros atributos

## Seguridad
- https://stackoverflow.com/questions/52363487/what-is-the-reason-to-disable-csrf-in-spring-boot-web-application
- ```Our recommendation is to use CSRF protection for any request that could be processed by a browser by normal users. If you are only creating a service that is used by non-browser clients, you will likely want to disable CSRF protection.```

## Licencia y términos de uso
<a rel="license" href="http://creativecommons.org/licenses/by/4.0/"><img alt="Licencia Creative Commons" style="border-width:0" src="https://i.creativecommons.org/l/by/4.0/80x15.png" /></a><br />Esta obra está bajo una <a rel="license" href="http://creativecommons.org/licenses/by/4.0/">Licencia Creative Commons Atribución 4.0 Internacional</a>.
