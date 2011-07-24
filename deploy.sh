#!/bin/sh

clear;clear

cd war-bootstrap-archetype
mvn clean deploy -U -DperformRelease=true -Dgpg.keyname=EE82F9AB
