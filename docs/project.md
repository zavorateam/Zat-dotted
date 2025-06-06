## 📘 **Техническое задание: Zat-dotted **

> Инструкция и проектная документация для команды разработки / нейросети-разработчика

---

### 📱 1. Платформа

* **Тип:** Android-приложение
* **Базовая платформа:** Fork Telegram X / Exteragram (корневая папка) (TDLib- или MTProto-клиент)
* **Язык разработки:** Kotlin + Java
* **Минимальный SDK:** Android 8.0 (API 26)
* **Хранилище:** EncryptedSharedPreferences, SQLCipher или Android Keystore
* QR-код обмена ключами между клиентами

---

### 🔐 2. Регистрация и вход

**Требования:**

* Отказ от логина через номер телефона
* Регистрация по трём параметрам:

  * `username` — публичный псевдоним
  * `password` — используется для входа и генерации локального ключа
  * `cloud_password` — используется для шифрования приватного ключа

**Процедура регистрации:**

1. Генерация пары ключей RSA (2048+ бит)
2. Приватный ключ шифруется с использованием cloud\_password (через AES-256)
3. Хранение зашифрованного приватного ключа и открытого ключа локально
4. Хеш пароля хранится с солью

**Процедура входа:**

* Проверка локального хеша пароля
* Дешифровка приватного ключа через cloud\_password
* Подготовка к обмену сообщениями


---

### 📬 3. Обмен сообщениями

#### 3.1 Структура чатов

* Один-на-один только зашифрованные чаты
* Маскировка трафика под http/s запросы
* Возможность отправить самоуничтожающееся сообщение (после удаления не оставит следов на клиентах и сервере)
* Группы (аналог Telegram group chats), где:

  * Все сообщения шифруются перед отправкой
  * Ключ доступа (session key) рассылается каждому участнику в личку (зашифрованный под его публичный ключ)
  * Пользователи могут расшифровать сообщения, если имеют нужный ключ

#### 3.2 Шифрование

* Custom End-to-End шифрование (все на клиенте, до передачи в Telegram):

  * AES-256 + RSA (или Curve25519 + ChaCha20)
  * Ежедневная генерация ключа (`daily key`) по алгоритму:

    ```kotlin
    val seed = LocalDate.now().toString() + SECRET_PEPPER
    val key = SHA256(seed.toByteArray())
    ```

#### 3.3 Поток отправки

1. Сообщение шифруется с использованием session key / daily key
2. Зашифрованный текст отправляется через Telegram API как обычное сообщение
3. Получатель расшифровывает при получении

---

### 🌐 4. Сеть и трафик

**Требования:**

* Весь трафик должен быть направлен через сеть Tor или указанный прокси.

* Возможные реализации:

  * **Через Orbot**: клиент должен поддерживать SOCKS5-прокси Orbot (`127.0.0.1:9050`).

**Telegram API через Tor:**

* TDLib или MTProto должен быть перенастроен использовать SOCKS5.
* Проверка, что все соединения идут через Tor.
* Если не так - просим пользователя указать иной прокси-сервер.
* При невозможности — уведомляем пользователя, подключаемся без прокси.

---

### 🧰 5. Хранилище ключей и безопасности

* Ключи хранятся локально, приватный ключ всегда зашифрован
* Используются безопасные контейнеры хранения:

  * Android Keystore (если совместим)
  * EncryptedSharedPreferences
  * SQLCipher DB (для хранения чатов, публичных ключей)

---

### ⚠️ 6. Урезание функционала

Удалить или отключить:

* Каналы
* Стикеры
* Облачное хранилище (всё хранится локально, в зашифрованном виде)
* Медиа-автозагрузка

---

### 🧪 7. Возможности расширения (на будущее)

* Self-destruct messages (время жизни сообщений)
* Оффлайн-режим с буферизацией
* Верификация личности по публичному ключу (web-of-trust)

---

---