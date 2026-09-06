-- INITIAL
INSERT INTO application_stages (id, code, name, sequence, is_active)
SELECT 1, 'INITIAL', 'Initial', 1, true
WHERE NOT EXISTS (
    SELECT 1 FROM application_stages WHERE code = 'INITIAL'
);

-- SCHEDULING
INSERT INTO application_stages (id, code, name, sequence, is_active)
SELECT 2, 'SCHEDULING', 'Scheduling', 2, true
WHERE NOT EXISTS (
    SELECT 1 FROM application_stages WHERE code = 'SCHEDULING'
);

-- INTERVIEW
INSERT INTO application_stages (id, code, name, sequence, is_active)
SELECT 3, 'INTERVIEW', 'Interview', 3, true
WHERE NOT EXISTS (
    SELECT 1 FROM application_stages WHERE code = 'INTERVIEW'
);

-- OFFER
INSERT INTO application_stages (id, code, name, sequence, is_active)
SELECT 4, 'OFFER', 'Offer', 4, true
WHERE NOT EXISTS (
    SELECT 1 FROM application_stages WHERE code = 'OFFER'
);

-- CLOSURE
INSERT INTO application_stages (id, code, name, sequence, is_active)
SELECT 5, 'CLOSURE', 'Closure', 5, true
WHERE NOT EXISTS (
    SELECT 1 FROM application_stages WHERE code = 'CLOSURE'
);

INSERT IGNORE INTO application_statuses
(id, stage_id, code, name, sequence, is_terminal, is_followup_required, is_active)
VALUES
(1, 1, 'APPLIED', 'Applied', 1, false, false, true),
(2, 1, 'NO_RESPONSE', 'No Response', 2, false, true, true),
(3, 1, 'CALL_BACK', 'Call Back', 3, false, true, true),
(4, 1, 'NOT_INTERESTED', 'Not Interested', 4, true, false, true),
(5, 2, 'TO_BE_SCHEDULED', 'To Be Scheduled', 1, false, true, true),
(6, 2, 'SCHEDULED', 'Scheduled', 2, false, false, true),
(7, 3, 'FEEDBACK_PENDING', 'Feedback Pending', 1, false, true, true),
(8, 3, 'SELECTED', 'Selected', 2, false, false, true),
(9, 3, 'REJECTED', 'Rejected', 3, true, false, true),
(10, 4, 'OFFERED', 'Offered', 1, false, true, true),
(11, 4, 'OFFER_ACCEPTED', 'Offer Accepted', 2, false, false, true),
(12, 5, 'JOINED', 'Joined', 1, true, false, true),
(13, 5, 'DROPPED', 'Dropped', 2, true, false, true),
(14, 5, 'CLOSED', 'Closed', 3, true, false, true);

INSERT IGNORE INTO application_status_transitions (from_status_id, to_status_id)
VALUES
((SELECT id FROM application_statuses WHERE code='APPLIED'), (SELECT id FROM application_statuses WHERE code='NO_RESPONSE')),
((SELECT id FROM application_statuses WHERE code='APPLIED'), (SELECT id FROM application_statuses WHERE code='CALL_BACK')),
((SELECT id FROM application_statuses WHERE code='APPLIED'), (SELECT id FROM application_statuses WHERE code='TO_BE_SCHEDULED')),
((SELECT id FROM application_statuses WHERE code='APPLIED'), (SELECT id FROM application_statuses WHERE code='NOT_INTERESTED'));

INSERT IGNORE INTO application_status_transitions (from_status_id, to_status_id)
VALUES
((SELECT id FROM application_statuses WHERE code='NO_RESPONSE'), (SELECT id FROM application_statuses WHERE code='CALL_BACK')),
((SELECT id FROM application_statuses WHERE code='NO_RESPONSE'), (SELECT id FROM application_statuses WHERE code='TO_BE_SCHEDULED')),
((SELECT id FROM application_statuses WHERE code='NO_RESPONSE'), (SELECT id FROM application_statuses WHERE code='NOT_INTERESTED')),
((SELECT id FROM application_statuses WHERE code='NO_RESPONSE'), (SELECT id FROM application_statuses WHERE code='CLOSED'));

INSERT IGNORE INTO application_status_transitions (from_status_id, to_status_id)
VALUES
((SELECT id FROM application_statuses WHERE code='CALL_BACK'), (SELECT id FROM application_statuses WHERE code='TO_BE_SCHEDULED')),
((SELECT id FROM application_statuses WHERE code='CALL_BACK'), (SELECT id FROM application_statuses WHERE code='NOT_INTERESTED')),
((SELECT id FROM application_statuses WHERE code='CALL_BACK'), (SELECT id FROM application_statuses WHERE code='CLOSED'));

INSERT IGNORE INTO application_status_transitions (from_status_id, to_status_id)
VALUES
((SELECT id FROM application_statuses WHERE code='TO_BE_SCHEDULED'), (SELECT id FROM application_statuses WHERE code='SCHEDULED')),
((SELECT id FROM application_statuses WHERE code='TO_BE_SCHEDULED'), (SELECT id FROM application_statuses WHERE code='DROPPED'));

INSERT IGNORE INTO application_status_transitions (from_status_id, to_status_id)
VALUES
((SELECT id FROM application_statuses WHERE code='SCHEDULED'), (SELECT id FROM application_statuses WHERE code='FEEDBACK_PENDING')),
((SELECT id FROM application_statuses WHERE code='SCHEDULED'), (SELECT id FROM application_statuses WHERE code='DROPPED'));

INSERT IGNORE INTO application_status_transitions (from_status_id, to_status_id)
VALUES
((SELECT id FROM application_statuses WHERE code='FEEDBACK_PENDING'), (SELECT id FROM application_statuses WHERE code='SELECTED')),
((SELECT id FROM application_statuses WHERE code='FEEDBACK_PENDING'), (SELECT id FROM application_statuses WHERE code='REJECTED'));

INSERT IGNORE INTO application_status_transitions (from_status_id, to_status_id)
VALUES
((SELECT id FROM application_statuses WHERE code='SELECTED'), (SELECT id FROM application_statuses WHERE code='OFFERED'));

INSERT IGNORE INTO application_status_transitions (from_status_id, to_status_id)
VALUES
((SELECT id FROM application_statuses WHERE code='OFFERED'), (SELECT id FROM application_statuses WHERE code='OFFER_ACCEPTED')),
((SELECT id FROM application_statuses WHERE code='OFFERED'), (SELECT id FROM application_statuses WHERE code='DROPPED'));

INSERT IGNORE INTO application_status_transitions (from_status_id, to_status_id)
VALUES
((SELECT id FROM application_statuses WHERE code='OFFER_ACCEPTED'), (SELECT id FROM application_statuses WHERE code='JOINED')),
((SELECT id FROM application_statuses WHERE code='OFFER_ACCEPTED'), (SELECT id FROM application_statuses WHERE code='DROPPED'));