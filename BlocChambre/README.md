# Gestion des Blocs et Chambres - Application Microservices

Une application basée sur une architecture de microservices pour gérer les blocs et chambres. Elle utilise Spring Cloud avec Eureka pour la découverte de services et un API Gateway comme point d'entrée.

## Architecture
- **blocchambre** : Microservice gérant les données des blocs et des chambres (CRUD).
- **gateway** : API Gateway pour router les requêtes vers les microservices.
- **eureka** : Serveur de découverte de services pour enregistrer et localiser les instances.

## Prérequis
- Java 17
- Maven 3.8+
- Git
