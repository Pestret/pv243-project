--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
-- insert into Member (id, name, email, phone_number) values (0, 'John Smith', 'john.smith@mailinator.com', '2125551212') 
insert into User (id, name, email, address, passwordHash, role) values (99, 'Pepa z depa', 'nebuduToDelat@milujipraci.cz', 'doma', 'totalniH4sH', 'customer')
insert into User (id, name, email, address, passwordHash, role) values (98, 'Pepa z depa', 'nebuduToDela5t@milujipraci.cz', 'doma', 'totalniH4sH', 'customer')
insert into User (id, name, email, address, passwordHash, role) values (97, 'Pepa z depa2', 'nebuduToDelat2@milujipraci.cz', 'doma2', 'totalniH4sH2', 'customer')
insert into User (id, name, email, address, passwordHash, role) values (96, 'Pepa z depa3', 'nekdo@nekdo.cz', 'doma3', 'nekdo', 'customer')
insert into User (id, name, email, address, passwordHash, role) values (95, 'admin', 'admin@admin.cz', 'admin', 'admin', 'admin')
insert into Product (id, name, description, price, available) values (100, 'Vybusna vrtacka', 'Bum bam ratata...', 250, 4)
insert into Product (id, name, description, price, available) values (111, 'Praskaci kulicky', 'Vyborne na straseni duchodcu', 2, 150)
insert into Product (id, name, description, price, available) values (112, 'Papinak s dusenou mrkvi', 'Boston IMPROVED', 5, 50)
insert into Product (id, name, description, price, available) values (113, 'Tlacenka se strelnym prachem', 'Nom nom nom', 25, 33)

