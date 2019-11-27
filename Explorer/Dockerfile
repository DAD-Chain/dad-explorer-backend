################################
### Builder image
################################
FROM maven:3.6.1-slim as builder

ENV VERSION=version2.0

RUN apt update && \
    apt install -y \
    git

RUN git clone https://github.com/DAD-Chain/dad-explorer.git && \
    cd dad-explorer && \
    git checkout $VERSION

WORKDIR /dad-explorer

RUN cd Explorer && \
    mvn clean package

#################################
#### Runner image
#################################
FROM openjdk:8-jdk-alpine

RUN apk add --no-cache ca-certificates bash

RUN addgroup DAD && \
    adduser -G DAD -s /bin/sh -D DAD -h /home/DAD

COPY --from=builder /dad-explorer/Explorer/target/explorer-1.3.0.RELEASE.jar /home/DAD/app.jar
COPY --from=builder /dad-explorer/Explorer/config /home/DAD/config

# # Run as non root
USER DAD
WORKDIR /home/DAD

EXPOSE 8090

CMD ["java", "-jar", "app.jar"]