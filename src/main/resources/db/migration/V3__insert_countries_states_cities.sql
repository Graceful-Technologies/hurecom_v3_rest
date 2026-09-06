INSERT INTO countries (id, name, code, is_active)
VALUES (1, 'India', 'IN', true)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO states (id, country_id, name, code, is_active) VALUES
(1, 1, 'Tamil Nadu', 'TN', true),
(2, 1, 'Karnataka', 'KA', true),
(3, 1, 'Telangana', 'TS', true),
(4, 1, 'Maharashtra', 'MH', true),
(5, 1, 'Delhi', 'DL', true),
(6, 1, 'Uttar Pradesh', 'UP', true),
(7, 1, 'Gujarat', 'GJ', true),
(8, 1, 'West Bengal', 'WB', true),
(9, 1, 'Rajasthan', 'RJ', true),
(10, 1, 'Madhya Pradesh', 'MP', true),
(11, 1, 'Kerala', 'KL', true),
(12, 1, 'Andhra Pradesh', 'AP', true),
(13, 1, 'Haryana', 'HR', true)
ON DUPLICATE KEY UPDATE name = VALUES(name);

INSERT INTO cities (id, state_id, name, is_active) VALUES

-- Tamil Nadu
(1, 1, 'Chennai', true),
(2, 1, 'Coimbatore', true),

-- Karnataka
(3, 2, 'Bangalore', true),

-- Telangana
(4, 3, 'Hyderabad', true),

-- Maharashtra
(5, 4, 'Mumbai', true),
(6, 4, 'Pune', true),

-- Delhi
(7, 5, 'New Delhi', true),

-- Uttar Pradesh
(8, 6, 'Noida', true),

-- Gujarat
(9, 7, 'Ahmedabad', true),

-- West Bengal
(10, 8, 'Kolkata', true),

-- Rajasthan
(11, 9, 'Jaipur', true),

-- Madhya Pradesh
(12, 10, 'Indore', true),

-- Kerala
(13, 11, 'Kochi', true),
(14, 11, 'Trivandrum', true),

-- Andhra Pradesh
(15, 12, 'Visakhapatnam', true),

-- Haryana (major corporate hub)
(16, 13, 'Gurgaon', true)

ON DUPLICATE KEY UPDATE name = VALUES(name);