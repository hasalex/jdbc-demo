# JDBC demo

## Intro

These examples are using an in-memory H2 database. It can be accessed via its Web Console at http://localhost:8082.

The PersonDAO interface is implemented using several DB access techniques, so that we can compare the amount of code needed with each one. 
The first examples are simple FUCR (some people say CRUD, but I prefer FUCR), with generic classes in order to reduce code.  

## Plain JDBC

It is implemented in the fr.sewatech.demo.jdbc.plain package, and uses nothing but the JDBC API.

## My Batis

It is implemented in the fr.sewatech.demo.jdbc.mybatis package, and uses Java annotations instead of XML mapping files.
