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
!define ColorY #8c7c50
!define ColorP #675582
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

User -> F: Request
activate F ColorG

F -> M: HTTP
activate M ColorY

M -> B: HTTP
activate B ColorP
B --> M: HTTP
deactivate B

M --> F: HTTP
deactivate M

F --> User: Response
deactivate F

@enduml
```

</details>

---

[<img src="images/up.svg" alt="up" width="70">](#up)