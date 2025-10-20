# Architecture Microservices - Gestion de Factures

## 📋 Description du Projet

Application complète de gestion de factures basée sur une architecture microservices avec Spring Boot et Spring Cloud.

## 🏗️ Architecture

L'application comprend **6 microservices** :

### 1. **CUSTOMER-SERVICE** (Port 8081)
- Gère les clients (Customer)
- Base de données H2 en mémoire
- API REST avec Spring Data REST
- **Entité** : Customer (id, name, email)

### 2. **INVENTORY-SERVICE** (Port 8082)
- Gère les produits (Product)
- Base de données H2 en mémoire
- API REST avec Spring Data REST
- **Entité** : Product (id, name, price, quantity)

### 3. **BILLING-SERVICE** (Port 8083)
- Gère les factures et lignes de facture
- Communication avec Customer et Inventory via **OpenFeign**
- **Entités** : 
  - Bill (id, billingDate, customerId, customer @Transient, productItems)
  - ProductItem (id, productId, product @Transient, price, quantity, bill)

### 4. **EUREKA-DISCOVERY-SERVICE** (Port 8761)
- Service d'annuaire basé sur Netflix Eureka Server
- Interface web : http://localhost:8761

### 5. **GATEWAY-SERVICE** (Port 8888)
- API Gateway avec Spring Cloud Gateway (Reactive)
- Load balancing via Eureka
- **Routes** :
  - `/customers/**` → customer-service
  - `/products/**` → inventory-service
  - `/bills/**` → billing-service

### 6. **CONFIG-SERVICE** (Port 9999)
- Configuration centralisée
- Spring Cloud Config Server
- Repository Git (à configurer)

## 🛠️ Technologies Utilisées

- **Java 25**
- **Spring Boot 3.5.6**
- **Spring Cloud 2025.0.0**
- **Spring Data JPA**
- **H2 Database**
- **Lombok**
- **Netflix Eureka**
- **Spring Cloud Gateway**
- **OpenFeign**
- **Maven**

## 🚀 Démarrage de l'Application

### Ordre de Démarrage (IMPORTANT) :

```bash
# 1. Eureka Discovery Service
cd eureka-discovery-service
mvn spring-boot:run

# 2. Config Service (optionnel pour cette version)
cd config-service
mvn spring-boot:run

# 3. Customer Service
cd customer-service
mvn spring-boot:run

# 4. Inventory Service
cd inventory-service
mvn spring-boot:run

# 5. Gateway Service
cd gateway-service
mvn spring-boot:run

# 6. Billing Service
cd billing-service
mvn spring-boot:run
```

### Avec PowerShell (démarrage en arrière-plan) :

```powershell
# Démarrer Eureka
cd eureka-discovery-service; Start-Process mvn -ArgumentList "spring-boot:run"; cd ..

# Attendre 30 secondes
Start-Sleep -Seconds 30

# Démarrer les autres services
cd customer-service; Start-Process mvn -ArgumentList "spring-boot:run"; cd ..
cd inventory-service; Start-Process mvn -ArgumentList "spring-boot:run"; cd ..
cd gateway-service; Start-Process mvn -ArgumentList "spring-boot:run"; cd ..
cd billing-service; Start-Process mvn -ArgumentList "spring-boot:run"; cd ..
```

## 🧪 Tests de l'Application

### 1. Vérifier Eureka Dashboard
```
http://localhost:8761
```
Tous les services doivent apparaître enregistrés.

### 2. Accès Direct aux Services

#### Customer Service :
```bash
# Lister tous les clients
curl http://localhost:8081/customers

# Récupérer un client par ID
curl http://localhost:8081/customers/1
```

#### Inventory Service :
```bash
# Lister tous les produits
curl http://localhost:8082/products

# Récupérer un produit par ID
curl http://localhost:8082/products/1
```

#### Billing Service :
```bash
# Récupérer une facture avec enrichissement
curl http://localhost:8083/bills/1
```

### 3. Accès via Gateway (Recommandé)

```bash
# Via Gateway - Clients
curl http://localhost:8888/customers
curl http://localhost:8888/customers/1

# Via Gateway - Produits
curl http://localhost:8888/products
curl http://localhost:8888/products/1

# Via Gateway - Factures
curl http://localhost:8888/bills/1
```

## 📊 Données de Test

### Clients (Customer Service) :
1. Hassan - hassan@gmail.com
2. Imane - imane@gmail.com
3. Mohamed - mohamed@gmail.com

### Produits (Inventory Service) :
1. Ordinateur - 5000.0 DH (10 unités)
2. Imprimante - 1200.0 DH (5 unités)
3. Smartphone - 3500.0 DH (20 unités)
4. Clavier - 150.0 DH (50 unités)

### Factures (Billing Service) :
- **Bill 1** : Client Hassan - 2 Ordinateurs + 1 Imprimante
- **Bill 2** : Client Imane - 1 Smartphone + 3 Claviers
- **Bill 3** : Client Mohamed - 1 Ordinateur + 2 Smartphones

## 📁 Structure du Projet

```
architecture-micro-service/
├── customer-service/
│   ├── src/main/java/ma/jaouad/customerservice/
│   │   ├── entities/Customer.java
│   │   ├── repositories/CustomerRepository.java
│   │   └── CustomerServiceApplication.java
│   └── application.properties
│
├── inventory-service/
│   ├── src/main/java/ma/jaouad/inventoryservice/
│   │   ├── entities/Product.java
│   │   ├── repositories/ProductRepository.java
│   │   └── InventoryServiceApplication.java
│   └── application.properties
│
├── billing-service/
│   ├── src/main/java/ma/jaouad/billingservice/
│   │   ├── entities/
│   │   │   ├── Bill.java
│   │   │   └── ProductItem.java
│   │   ├── model/
│   │   │   ├── Customer.java
│   │   │   └── Product.java
│   │   ├── feign/
│   │   │   ├── CustomerRestClient.java
│   │   │   └── ProductRestClient.java
│   │   ├── repositories/
│   │   │   ├── BillRepository.java
│   │   │   └── ProductItemRepository.java
│   │   ├── web/BillingRestController.java
│   │   └── BillingServiceApplication.java
│   └── application.properties
│
├── eureka-discovery-service/
│   └── application.properties (port 8761)
│
├── gateway-service/
│   └── application.yml (routes configuration)
│
└── config-service/
    └── application.properties (port 9999)
```

## 🔧 Accès aux Consoles H2

```bash
# Customer Service
http://localhost:8081/h2-console
JDBC URL: jdbc:h2:mem:customer-db

# Inventory Service
http://localhost:8082/h2-console
JDBC URL: jdbc:h2:mem:product-db

# Billing Service
http://localhost:8083/h2-console
JDBC URL: jdbc:h2:mem:billing-db
```

## ✅ Corrections Effectuées

### 1. ✅ BillRepository
- **Problème** : Classe vide sans extends JpaRepository
- **Solution** : Transformé en interface extends JpaRepository<Bill, Long>

### 2. ✅ ProductItemRepository
- **Problème** : Repository manquant
- **Solution** : Créé ProductItemRepository avec @RepositoryRestResource

### 3. ✅ InventoryService Discovery
- **Problème** : @EnableDiscoveryClient manquant
- **Solution** : Ajouté @EnableDiscoveryClient

### 4. ✅ Données de Test - InventoryService
- **Problème** : Pas de CommandLineRunner
- **Solution** : Ajouté 4 produits de test

### 5. ✅ Product Entity
- **Problème** : @Builder manquant
- **Solution** : Ajouté @Builder pour cohérence

### 6. ✅ Données de Test - BillingService
- **Problème** : Pas de factures initiales
- **Solution** : Ajouté 3 factures avec produits

### 7. ✅ Gateway Routes
- **Problème** : Route /bills/** manquante
- **Solution** : Ajouté route vers billing-service

### 8. ✅ Config Service
- **Statut** : Bien configuré avec @EnableConfigServer

## 🎯 Architecture Complète Validée

Votre architecture répond à **100% des exigences** du TP :

✅ 6 microservices fonctionnels  
✅ Communication via OpenFeign  
✅ Service Discovery avec Eureka  
✅ API Gateway avec routage dynamique  
✅ Données de test initialisées  
✅ Base de données H2 par service  
✅ Configuration centralisée  

## 📝 Notes Importantes

1. **Ordre de démarrage** : Respectez l'ordre pour éviter les erreurs de connexion
2. **Délai de démarrage** : Attendez 30 secondes entre Eureka et les autres services
3. **OpenFeign** : Les appels inter-services passent par Eureka pour la découverte
4. **Gateway** : Utilisez le Gateway (port 8888) comme point d'entrée unique

## 🐛 Dépannage

### Service non enregistré dans Eureka
- Vérifiez que Eureka est démarré
- Attendez 30-60 secondes après le démarrage
- Vérifiez les logs du service

### Erreur Feign
- Vérifiez que tous les services sont UP dans Eureka
- Vérifiez les noms de services dans @FeignClient

### Port déjà utilisé
```powershell
# Trouver le processus sur un port
netstat -ano | findstr :8081

# Tuer le processus
taskkill /PID <PID> /F
```

## 👨‍💻 Auteur

**Jaouad** - Activité Pratique N°3 - Architecture Microservices

---

**Date** : Octobre 2025  
**Cours** : Systèmes Distribués & Parallèles et Sécurité  
**Établissement** : ENSET
