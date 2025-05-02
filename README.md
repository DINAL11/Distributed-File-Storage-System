# 📁 Distributed File Storage System

## Overview
This project implements a scalable, fault-tolerant distributed file storage system using consistent hashing, REST APIs, and a Java-based client.

## 🔧 Features
- 🔄 Data Replication & Fault Tolerance
- ⚙️ Consistent Hashing for node mapping
- 🌐 REST APIs for file operations
- 💻 CLI Client App
- 🐳 Dockerized Deployment

## 🛠 Technologies
- Java 17+
- Spring Boot
- Docker

## 📂 Structure
```
distributed-file-storage/
├── coordinator/
│   ├── Dockerfile
│   └── src/main/java/com/distributed/coordinator/Coordinator.java
├── storage-node/
│   ├── Dockerfile
│   └── src/main/java/com/distributed/storage/StorageNode.java
├── client-app/
│   └── ClientApp.java
└── README.md
```

## 🏃 Getting Started
### 1. Build Images
```bash
docker build -t coordinator ./coordinator
docker build -t storage-node ./storage-node
```

### 2. Run Coordinator
```bash
docker run -p 8080:8080 coordinator
```

### 3. Run Storage Nodes
```bash
docker run -e COORDINATOR_URL=http://localhost:8080 -p 8081:8080 storage-node
docker run -e COORDINATOR_URL=http://localhost:8080 -p 8082:8080 storage-node
```

### 4. Run Client
```bash
cd client-app
javac ClientApp.java
java ClientApp
```

## 🔖 Example API Usage
```bash
curl -F "file=@sample.txt" http://localhost:8081/upload
curl http://localhost:8081/download?fileName=sample.txt --output sample.txt
```

## 📆 Future Enhancements
- Dynamic rebalancing
- Enhanced replication
- File metadata and listing support
