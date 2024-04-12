/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  NATCRI
 * Created: 28 nov 2023
 */
CREATE SCHEMA IF NOT EXISTS orchestrator;

CREATE TABLE IF NOT EXISTS orchestrator.transactions
(
    event_uuid character varying(36) NOT NULL,
    operation character varying(50) NOT NULL,
    outcome character varying(20) NOT NULL,
    expire timestamp with time zone NOT NULL,
    timeout bigint NOT NULL,
    CONSTRAINT transactions_event_uuid_ukey UNIQUE (event_uuid)
);

-- Index: transactions_event_uuid_idx
-- DROP INDEX orchestrator.transactions_event_uuid_idx;
CREATE INDEX transactions_event_uuid_idx ON orchestrator.transactions USING btree(event_uuid);
