-- Create required databases for microservices
-- These run only on first container init (empty PGDATA)
CREATE DATABASE vno_user;
CREATE DATABASE vno_note;
CREATE DATABASE vno_organization;
-- auth_service is created by POSTGRES_DB env
