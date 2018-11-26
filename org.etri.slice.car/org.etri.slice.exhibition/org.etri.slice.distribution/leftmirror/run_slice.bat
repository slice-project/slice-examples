@echo off
java -jar -Dcom.sun.management.jmxremote.port=3404 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false ./bin/felix.jar
