# JDBC demo

## Intro

These examples are using an in-memory H2 database. It can be accessed via its Web Console at http://localhost:8082.

The PersonDAO interface is implemented using several DB access techniques, so that we can compare the amount of code needed with each one.

## Plain JDBC

It is implemented in the fr.sewatech.demo.jdbc.plain package, and uses nothing but the JDBC API.
