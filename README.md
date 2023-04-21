# SondageService

## Description
SondageService est une application qui permet de gérer des sondages. L'application est développée en Java avec le framework Spring Boot.

## Prérequis

Pour exécuter l'application, vous devez avoir Java 11 ou supérieur installé sur votre système.
Vous devez créer une base de données sans créer de table. Vous pouvez mettre le nom de votre BDD dans application.properties à cette ligne : spring.datasource.url=jdbc:mysql://localhost:3306/LeNomDeVotreBDD


## Installation

1. Clonez ce dépôt de code à l'aide de la commande `git clone https://github.com/Andytomsingvien/SondageService'
2. Lancez le sur votre IDE puis lancer la classe SondageServiceApplication

Par défaut, l'application écoutera sur le port 8080. Si vous souhaitez changer le port, vous pouvez modifier la ligne # server.port = le port de votre choix.


## Utilisation

L'application expose des points de terminaison REST pour effectuer les opérations CRUD sur les sondages. Les points de terminaison sont les suivants:

### Récupérer tous les sondages
- GET `/rest/sondages`

### Récupérer un sondage spécifique en fonction de son identifiant
- GET `/rest/sondages/{id}`

### Ajouter un nouveau sondage
- POST `/rest/sondages`

### Mettre à jour un sondage existant en fonction de son identifiant
- PUT `/rest/sondages/{id}`

### Supprimer un sondage existant en fonction de son identifiant
- DELETE `/rest/sondages/{id}`

Pour effectuer des requêtes sur ces points de terminaison, vous pouvez utiliser un outil tel que Postman.

## Contribuer

Les contributions sont les bienvenues! Si vous souhaitez contribuer à ce projet, veuillez forker le projet et créer une nouvelle branche pour votre contribution. Une fois votre contribution terminée, veuillez créer une pull request pour la fusionner avec la branche principale.
