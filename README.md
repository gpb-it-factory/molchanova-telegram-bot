<div style="text-align: center;">
    <img src="images/banner.svg" alt="Шапка">
</div>
<h1><a name="up">Telegram-bot // Frontend</a></h1>

Часть приложения `Мини-банк` − telegram-бот, с которым будет взаимодействовать пользователь.

---

## Общая архитектура
### Описание

#### 1. Frontend
Telegram-бот. Клиентское приложение, инициирует запросы пользователей.

#### 2. Middle
Java-сервис. Принимает запросы от пользователя, выполняет валидацию и бизнес-логику, маршрутизирует их в `Backend` и отправляет ответ.

#### 3. Backend
Автоматизированная банковская система. Обрабатывает транзакции, хранит клиентские данные.

### Схема
Описание архитектуры можно представить в виде краткой схемы ниже:\
\
![Схемочка](images/arhitecture_diagram.svg "Архитектура")

<details>
<summary>Код PlantUML схемы</summary>

```plantuml
@startuml
scale 1

!define Background #0d1117
!define Arrow #bcbec4
!define ColorY #bfa96d
!define ColorP #947abb
!define ColorG #72b76b

skinparam backgroundColor Background
skinparam sequence {
    ActorBorderColor ColorG
    ActorFontColor Arrow
    ActorFontStyle bold

    ParticipantPadding 30
    ParticipantFontStyle bold
    ParticipantFontColor Background
    ParticipantBackgroundColor Arrow
    LifeLineBorderColor Arrow

    MessageAlign center
    ArrowFontColor Arrow
    ArrowFontStyle bold
    ArrowColor Arrow
}

actor "Awesome\nYou" as User Arrow

participant "Frontend" as F ColorG
participant "Middle" as M ColorY
participant "Backend" as B ColorP

User -> F: Command
activate F ColorG

F -> M: Request
activate M ColorY

M -> B: Request
activate B ColorP
B --> M: Response
deactivate B

M --> F: Response
deactivate M

F --> User: Answer
deactivate F

@enduml
```

</details>

## Планы на будущее
- **Создать «скелет» бота** \
  Разработать расширяемый механизм добавления новых команд.
- **Добавить первую тестовую команду** \
  Добавить `/ping`, в ответе возвращающая `pong`.

## Контакты
Связаться со мной можно через:
- [GitHub](https://github.com/molchmd): molchmd
- [Telegram](https://t.me/molchmd): @molchmd

---

[<img src="images/up.svg" alt="up" width="70">](#up)