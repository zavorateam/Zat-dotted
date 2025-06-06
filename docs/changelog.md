# Changelog

## [2025-05-28] - Инициализация документации
### Добавлено
- Создан файл tasktracker.md для отслеживания задач.
- Создан файл changelog.md для ведения журнала изменений.

## [2025-05-28] - Актуализация документации
### Добавлено
- Актуализирован tasktracker.md.
- Обновлена структура tasktracker.md для новых задач.

## [2025-05-28] - Обновление документации и задач
### Добавлено
- Добавлены задачи по реализации шифрования и поддержке Tor через Orbot в tasktracker.md.
- Создан файл functions.md с описанием основных функций мессенджера.

### Изменено
- Обновлен раздел "Сеть и трафик" в project.md для использования Orbot.

## [2025-05-28] - Поддержка Tor через Orbot
### Добавлено
- Метод `checkOrbotInstallation` для проверки наличия Orbot.
- Метод `enableOrbotProxy` для настройки прокси через Orbot.

## [2025-05-28] - Поддержка Tor через Orbot
### Добавлено
- Добавлен пункт в меню настроек для выбора типа подключения (свой прокси, Tor через Orbot, без шифрования).
- Создан макет `activity_proxy_settings.xml` для управления настройками подключения.

### Изменено
- Обновлен `ProxySettingsActivity` для обработки выбора типа подключения.