<a name="readme-top"></a>

<h3 align="center">Aufgabenverwaltung</h3>
    <br />
    <br />

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#beschreibung-des-projekts">Beschreibung des Projekts</a>
      <ul>
        <li><a href="#genutzte-technologien">Genutzte Technologien</a></li>
      </ul>
    </li>
    <li>
      <a href="#erste-schritte">Erste Schritte</a>
      <ul>
        <li><a href="#voraussetzungen">Voraussetzungen</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li>
	    <a href="#anwendung">Anwendung</a>
		<ul>
	        <li><a href="#endpoints">Endpunkte</a></li>
	        <li><a href="#profiles">Profile</a></li>
      </ul>
</li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## Beschreibung des Projekts

Dies ist eine Lösung für eine von Agido gestellte Bewerbungsaufgabe. Ziel der Aufgabe war es, eine Aufgabenverwaltung in Java Spring zu implementieren. Tasks und User können über eine API erstellt, gepflegt, gelöscht und abgerufen werden. Dabei kann zwischen InMemory- und Datenbankbetrieb umgeschaltet werden. Eine grafische Oberfläche gibt es nicht.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Genutzte Technologien

- [![Spring Boot][Spring Boot]][Spring Boot Url]
- [![PostgreSql][Postgres]][Postgres Url]
- [![Swagger][Swagger]][Swagger Url]

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Erste Schritte

### Voraussetzungen

- Java 24
- Docker Compose
- Maven

### Installation

Die Postgres Instanz muss während des Ausführens von `mvn install` online sein. Während des Build Prozesses werden alle Tests ausgeführt, da kein eigener Testcontainer eingebunden wurde ist dafür eine Verbindung zu der Postgres Instanz notwendig.

1. Das Repository klonen
   ```sh
   git clone https://github.com/JulianNiedbal/Aufgabenverwaltung.git
   ```
2. Den Postgres Docker Container starten, damit die Datenbank Testcases funktionieren
   ```sh
   docker compose -f compose.yaml up -d
   ```
3. Alle Dependencies installieren
   ```sh
   mvn install
   ```
4. Projekt bauen und ausführen
   ```sh
   mvn spring-boot:run
   ```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->

## Anwendung

Tasks können nur bereits bestehenden Usern zugeordnet werden. Es muss also erst ein User erstellt werden, bevor ein Task angelegt werden kann. Dazu kann die Route `http://localhost:8088/user` verwendet werden. Wird der Username geändert oder der User gelöscht, werden die zugehörigen Tasks entsprechend angepasst. </br></br>
Eine komplette Dokumentation der Api kann unter `http://localhost:8088/swagger-ui/index.html` aufgerufen werden. \
Die folgenden Beispiele sind vorgefertigte Curl Befehle um einen ersten Eindruck der Applikation gewinnen zu können.

### User

#### Post

```sh
curl -X POST http://localhost:8088/user \
  -H "Content-Type: application/json" \
  -d '{"username": "UserName"}'
```

#### Put

```sh
curl -X PUT http://localhost:8088/user \
  -H "Content-Type: application/json" \
  -H "X-User: UserName" \
  -d '{"username": "NewName"}'
```

#### Delete

```sh
curl -X DELETE http://localhost:8088/user \
  -H "X-User: UserName"
```

### Tasks

#### Post

```sh
curl -X POST http://localhost:8088/tasks \
 -H "Content-Type: application/json" \
 -H "X-User: UserName" \
 -d '{"title": "Titel", "description": "Beschreibung", "completed": false}'
```

#### Get

```sh
curl -X GET http://localhost:8088/tasks \
-H "X-User: UserName"
```

#### Get

```sh
curl -X GET http://localhost:8088/tasks/1 \
-H "X-User: UserName"
```

#### Put

```sh
curl -X PUT http://localhost:8088/tasks/1 \
 -H "Content-Type: application/json" \
 -H "X-User: UserName" \
 -d '{"title": "Neuer Titel", "description": "Neue Beschreibung", "completed": false}'
```

#### Delete

```sh
curl -X DELETE http://localhost:8088/tasks/1 \
-H "X-User: UserName"
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Profile

Die verschiedenen Profile können über Commandline Arguments beim Starten der Applikation ausgewählt werden. Das "inMemory"-Profil lädt dabei jedoch standardmäßig, falls kein anderes Profil spezifiziert wird und muss daher nicht extra ausgewählt werden. \
Das "inMemory"-Profil speichert die User in einer `ArrayList<User>` und Tasks in einer `Map<String, List<Task>>`. Es speichert die Daten nicht persistent. \
Das "postgres"-Profil speichert User und Tasks in einer Postgres Instanz die mittels Docker Compose automatisch beim Ausführen der Applikation gestartet wird.

#### inMemory

```sh
mvn spring-boot:run -Dspring-boot.run.profiles=inMemory
```

#### postgres

```sh
mvn spring-boot:run -Dspring-boot.run.profiles=postgres
```

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->

[Spring Boot]: https://img.shields.io/badge/Spring_Boot-000000?style=for-the-badge&logo=springboot&logoColor=white
[Spring Boot Url]: https://spring.io/projects/spring-boot
[Postgres]: https://img.shields.io/badge/postgresql-000000?style=for-the-badge&logo=postgresql&logoColor=white
[Postgres Url]: https://www.postgresql.org/
[Swagger]: https://img.shields.io/badge/swagger-000000?style=for-the-badge&logo=swagger&logoColor=white
[Swagger Url]: https://swagger.io/
