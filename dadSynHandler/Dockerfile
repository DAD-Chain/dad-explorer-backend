################################
### Builder image
################################
FROM maven:3.6.1-slim as builder

ENV VERSION=version2.0 \
    MYSQL_TEST_PASSWORD=qwerty

RUN apt update && \
    apt install -y \
    git

RUN git clone https://github.com/DAD-Chain/dad-explorer.git && \
    cd dad-explorer && \
    git checkout $VERSION

WORKDIR /dad-explorer

RUN cd dadSynHandler && \
    mvn clean package

#################################
#### Runner image
#################################
FROM openjdk:8-jdk-alpine

ENV MYSQL_TEST_PASSWORD=qwerty

RUN apk add --no-cache ca-certificates bash

RUN addgroup DAD && \
    adduser -G DAD -s /bin/sh -D DAD -h /home/DAD

COPY --from=builder /dad-explorer/dadsynHandler/target/dadsynhandler-1.0.0-SNAPSHOT.jar /home/DAD/app.jar
COPY --from=builder /dad-explorer/dadSynHandler/config /home/DAD/config

# # Run as non root
USER DAD
WORKDIR /home/DAD

EXPOSE 8090

CMD ["java", "-jar", "app.jar"]