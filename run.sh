#!/bin/sh
./gradlew assemble
echo ------------------------ Test Default ---------------------------
java -jar build/libs/Rebalancer-1.0.jar src/test/resources/default_test.csv
echo ------------------------ Test Default END -----------------------
echo
echo ------------------------ Test 1 ---------------------------
java -jar build/libs/Rebalancer-1.0.jar src/test/resources/test1.csv
echo ------------------------ Test 1 END -----------------------
echo
echo ------------------------ Test 2 ---------------------------
java -jar build/libs/Rebalancer-1.0.jar src/test/resources/test2.csv
echo ------------------------ Test 2 END -----------------------
echo
echo ------------------------ Test 3 ---------------------------
java -jar build/libs/Rebalancer-1.0.jar src/test/resources/test3.csv
echo ------------------------ Test 3 END -----------------------