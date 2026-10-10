default:
    @just --list

# compile + jar (build/libs/drakonix-sky-adventure-mod.jar)
build:
    ./gradlew build

clean:
    ./gradlew clean

# launch a dev client with the mod loaded (run/)
run-client:
    ./gradlew runClient

# launch a dev server with the mod loaded (run-server/)
run-server:
    ./gradlew runServer
