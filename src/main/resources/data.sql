-- SalePointEntity
INSERT INTO sale_point_entity (name) VALUES ('CABA');
INSERT INTO sale_point_entity (name) VALUES ('GBA_1');
INSERT INTO sale_point_entity (name) VALUES ('GBA_2');
INSERT INTO sale_point_entity (name) VALUES ('Santa Fe');
INSERT INTO sale_point_entity (name) VALUES ('Córdoba');
INSERT INTO sale_point_entity (name) VALUES ('Misiones');
INSERT INTO sale_point_entity (name) VALUES ('Salta');
INSERT INTO sale_point_entity (name) VALUES ('Chubut');
INSERT INTO sale_point_entity (name) VALUES ('Santa Cruz');
INSERT INTO sale_point_entity (name) VALUES ('Catamarca');

-- CostEntity (uses composite key: from_id, to_id)
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (1, 2, 2);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (1, 3, 3);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (2, 3, 5);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (2, 4, 10);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (1, 4, 11);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (4, 5, 5);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (2, 5, 14);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (6, 7, 32);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (8, 9, 11);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (10, 7, 5);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (3, 8, 10);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (5, 8, 30);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (10, 5, 5);
INSERT INTO cost_entity (from_id, to_id, cost) VALUES (4, 6, 6);
