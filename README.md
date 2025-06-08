# 🌅 Healthy Mornings API

Healthy Mornings to aplikacja wspierająca organizację porannej rutyny, poprawę samopoczucia i zwiększenie produktywności. To RESTful API jest serwerową częścią systemu, napisaną w **Spring Boot**, obsługującą użytkowników, zadania, poziomy, nagrody oraz statystyki.

## ✨ Kluczowe funkcjonalności

- ✅ Rejestracja i logowanie z JWT
- 📝 Tworzenie i realizacja zadań
- 📈 Statystyki ukończonych i aktywnych zadań
- 🧠 System poziomów oparty na punktach
- 🎁 Odblokowywanie i odbieranie nagród
- 👤 Zarządzanie kontem użytkownika

---

## 🛠️ Technologie

- Java 17
- Spring Boot 3
- Spring Security (JWT)
- Hibernate & JPA
- PostgreSQL (lub inna baza wspierająca JPA)
- Maven

---

## 📦 Struktura projektu
```
src
├── configuration // Konfiguracja projektu, JWT, Spring Security
├── controller // Endpointy REST
├── entity // Encje JPA
├── exception // Niestandardowa obsługa wyjątków
├── payload // Obiekty DTO do komunikacji
├── repository // Repozytoria JPA
└── service // Logika biznesowa
```

---

## 🚀 Uruchomienie lokalne

1. Sklonuj repozytorium:
   ```bash
   git clone https://github.com/twoj-login/healthy-mornings-api.git
   cd healthy-mornings-api
   ```
2. Skonfiguruj połączenie z bazą danych (np. w application.yml lub application.properties):
   ```
   spring.datasource.url=jdbc:postgresql://localhost:5432/healthy_mornings
   spring.datasource.username=postgres
   spring.datasource.password=haslo
   ```
3. Uruchom projekt:
   ```
   ./mvnw spring-boot:run
   ```

---

## 🔐 Autoryzacja

- JWT Token jest wymagany do większości operacji (Bearer <token>)
- Rejestracja i logowanie dostępne są publicznie:
  - POST /auth/register
  - POST /auth/login

---

## 👥 Autorzy
| Imię i nazwisko:  | Indeks: | Rola:                  |
|-------------------|---------|------------------------|
| Miłosz Fedorczak  | 38039   | Backend & Architektura |
| Szymon Pietruszka | 37719   | Współtwórca koncepcji  |
