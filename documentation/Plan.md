# План автоматизации по дипломному проекту по профессии «Тестировщик»
Дипломный проект — автоматизация тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.
## 1.	Перечень автоматизируемых сценариев:

•	заполнение и отправка формы покупки тура через сервис платежей с валидными данными (Payment Gate)

•	заполнение и отправка формы покупки тура через сервис платежей с невалидными данными (Payment Gate)

•	заполнение и отправка формы покупки тура через кредитный сервис с валидными данными (Credit Gate)

•	заполнение и отправка формы покупки тура через кредитный сервис с невалидными данными (Credit Gate)

### 1.1.	Примечание к перечню автоматизируемых сценариев.

Валидные значения полей банковской карты:

•	Номер карты - длина 16 цифр

•	Месяц - число от 01 до 12 

•	Год – 23 и последующие за ним числа

•	Владелец карты – допускается двойная фамилия, наличие пробела, ввод на латинице

•	CVC/CVV - длина 3 цифры

## 2.	Перечень используемых инструментов с обоснованием выбора:
•	IntelliJ IDEA - это интегрированная среда разработки (IDE) для разработки программного обеспечения. Выбор очевиден ввиду обучения на 2 модулях Нетологии именно в IntelliJ IDEA

•	Java 11 - стабильная версия языка программирования Java.

•	Gradle - система управления проектами и сборки, которая максимально понятна и легка в использовании и настройке.

•	JUnit - фреймворк для модульного тестирования Java-приложений. 

•	Selenide - удобный фреймворк автоматизации тестирования веб-приложений.

•	Allure - это легкий в обращении фреймворк и неприхотливый в плане ресурсов рабочей станции отчетный инструмент для создания красивых и информативных отчетов о выполнении тестов. 

•	Lombok – библиотека для упрощения разработки на Java путем автоматической генерации кода, значительно сокращает объем написанного кода.

•	Docker Desktop - инструмент для создания и управления контейнерами в операционной системе, обеспечит работу с каталогом gate-simulator

•	DevTools браузера - инструмент для поиска css-селекторов

•	GitHub - это платформа для хостинга и совместной работы над проектами с использованием системы контроля версий Git.

•	Joxi – программа для создания скриншотов

•	DBeaver – удобный доступный программный продукт для работы с БД

•	Google Chrome - браузер для просмотра веб-страниц и прогона тестов

•	Java Faker - простой инструмент для генерации тестовых данных

## 3.	Перечень и описание возможных рисков при автоматизации:

•	Трудности, связанные с настройкой окружения

•	UI-автотесты могут падать при изменении фронтэнда сайта, перемещении локаторов элементов

•	медленная работа компьютера из-за большой нагрузки (Docker)

•	неосведомленность реальными возможными действиями пользователей

•	форс-мажорные обстоятельства

## 4.	Интервальная оценка с учётом рисков в часах:

•	Планирование автоматизации тестирования – 12 часов

•	Написание кода автотестов – 120 часов

•	Подготовка отчетной документации – 24 часа

## 5.	План сдачи работы: 
автотесты и результаты их прогона будут готовы к 02.09.2023г.
