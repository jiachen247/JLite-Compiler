#!/bin/sh

make
java -cp ./bin/java-cup-11b.jar:out Main  $1 > output.s
arm-linux-gnueabi-gcc -c output.s -o output.o
arm-linux-gnueabi-gcc -o output.bin output.o --static
./gem5/build/ARM/gem5.opt gem5/configs/example/se.py -c ./output.bin
