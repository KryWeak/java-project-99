FROM gradle:8.7.0-jdk21

WORKDIR /app

COPY . .

RUN ./gradlew bootJar

CMD ./build/install/app/bin/app