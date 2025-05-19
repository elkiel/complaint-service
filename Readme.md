# 📦 Complaint Service – REST API

Aplikacja umożliwia zgłaszanie i zarządzanie reklamacjami produktów. REST API zostało opisane zgodnie ze specyfikacją **OpenAPI 3.0**, a interfejs dokumentacji dostępny jest przez **Swagger UI**.

---

## 🚀 Uruchomienie bazy danych PostgreSQL

Do uruchomienia bazy danych lokalnie użyj pliku `docker-compose.yml`:

```bash
docker-compose up -d
```

Parametry połączenia z bazą:

| Parametr     | Wartość            |
|--------------|---------------------|
| Host         | `localhost`         |
| Port         | `5432`              |
| Nazwa bazy   | `complaintdb`       |
| Użytkownik   | `complaint_user`    |
| Hasło        | `complaint_pass`    |

---

## ▶️ Uruchomienie aplikacji

Aby uruchomić aplikację lokalnie, użyj:

```bash
./gradlew bootRun
```

---

## 📄 Dokumentacja API – Swagger UI

Po uruchomieniu aplikacji dokumentacja Swagger UI będzie dostępna pod adresem:

👉 [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

Swagger umożliwia interaktywne testowanie oraz przeglądanie struktury endpointów API.

---

## ✅ Główne funkcjonalności

- ➕ Dodawanie reklamacji
- 🔍 Wyszukiwanie reklamacji po kryteriach (produkt, reporter, kraj, data)
- ✏️ Aktualizacja treści reklamacji
- 🌍 Automatyczne ustalanie kraju klienta na podstawie adresu IP

---

## 🧪 Przykładowe żądanie `POST /complaints`

```http
POST /complaints
Content-Type: application/json

{
  "productId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
  "content": "The product stopped working after one week",
  "reporter": "john.doe@example.com"
}
```

---

## 🛠 Technologie

- **Spring Boot 3**
- **PostgreSQL** (Docker)
- **Spring Data JPA**
- **Flyway** – automatyczne migracje schematu bazy danych
- **WebClient** – pobieranie danych o kraju klienta (z API ipwho.is)
- **OpenAPI 3.0** – specyfikacja REST API
- **Swagger UI** – graficzna dokumentacja API
- **Lombok** – generowanie konstruktorów, getterów/setterów
- **Gradle** – budowanie projektu

---

## 🗂 Struktura projektu

```
.
├── docker-compose.yml                # Konfiguracja PostgreSQL w Dockerze
├── open-api/                         # Specyfikacja OpenAPI (YAML)
├── src/
│   ├── main/java/                    # Logika aplikacji
│   ├── main/resources/db/migration/  # Migracje Flyway
│   └── test/                         # Testy jednostkowe i integracyjne
└── README.md                         # Ten plik
```
