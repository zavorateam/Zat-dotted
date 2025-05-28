# Основные функции мессенджера Zat-dotted

## Файл: TMessagesProj/src/main/java/org/telegram/messenger/MessagesController.java
- **Функция:** Управление сообщениями
  - Описание: Обрабатывает отправку, получение и удаление сообщений.
  - Основные методы:
    - `sendMessage`: Отправка сообщений.
    - `deleteMessage`: Удаление сообщений.
    - `loadMessages`: Загрузка истории сообщений.

## Файл: TMessagesProj/src/main/java/org/telegram/messenger/UserConfig.java
- **Функция:** Управление конфигурацией пользователя
  - Описание: Хранит и обрабатывает данные пользователя, такие как настройки и авторизация.
  - Основные методы:
    - `saveConfig`: Сохранение конфигурации пользователя.
    - `clearConfig`: Очистка данных пользователя.

## Файл: TMessagesProj/src/main/java/org/telegram/ui/LaunchActivity.java
- **Функция:** Главный экран приложения
  - Описание: Отвечает за отображение основного интерфейса и навигацию.
  - Основные методы:
    - `onCreate`: Инициализация интерфейса.
    - `onResume`: Обновление данных при возврате к приложению.

## Файл: TMessagesProj/src/main/java/org/telegram/messenger/ConnectionsManager.java
- **Функция:** Управление сетевыми соединениями
  - Описание: Обеспечивает взаимодействие с сервером Telegram через MTProto.
  - Основные методы:
    - `initConnection`: Инициализация соединения.
    - `sendRequest`: Отправка запросов на сервер.
    - `closeConnection`: Закрытие соединения.

## Файл: TMessagesProj/src/main/java/org/telegram/messenger/SecretChatHelper.java
- **Функция:** Поддержка секретных чатов
  - Описание: Обрабатывает создание и управление зашифрованными чатами.
  - Основные методы:
    - `startSecretChat`: Инициализация секретного чата.
    - `encryptMessage`: Шифрование сообщений.
    - `decryptMessage`: Расшифровка сообщений.

## Файл: TMessagesProj/src/main/java/org/telegram/messenger/NotificationCenter.java
- **Функция:** Управление уведомлениями
  - Описание: Обрабатывает системные и пользовательские уведомления.
  - Основные методы:
    - `postNotification`: Отправка уведомлений.
    - `addObserver`: Добавление наблюдателей для событий.
    - `removeObserver`: Удаление наблюдателей.
