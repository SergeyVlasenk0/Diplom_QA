## Запуск автотестов проекта Diplom_QA.
1. Запустить IDE с проектом Diplom_QA.
2. Запустить Docker Desktop. В нем находятся контейнеры "mySQL", "PostgreSQL", "node".
3. В терминале Local IDE выполнить команду "docker-compose up". Дождаться загрузки контейнеров.
4. В новом окне терминала Local(2) выполнить команду "java -jar artifacts/aqa-shop.jar ".
   Дождаться загрузки SUT.
5. В новом окне терминала Local(3) выполнить команду "./gradlew clean test -info".