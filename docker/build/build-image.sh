#!/bin/bash
rm -Rf build/
mkdir build
cp -R ../../* build

docker build -t os2services:rolesfromos2rollekatalog .

rm -Rf build/
