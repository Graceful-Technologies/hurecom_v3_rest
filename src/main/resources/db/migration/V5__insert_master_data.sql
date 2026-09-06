INSERT INTO master_data (
    type,
    code,
    name,
    display_order,
    is_active
) VALUES
      ('JOB_STATUS', 'DRAFT', 'Draft', 1, true),
      ('JOB_STATUS', 'OPEN', 'Open', 2, true),
      ('JOB_STATUS', 'HOLD', 'Hold', 3, true),
      ('JOB_STATUS', 'CLOSED', 'Closed', 4, true);

INSERT INTO master_data (
    type,
    code,
    name,
    display_order,
    is_active
) VALUES
      ('WORK_MODE', 'ONSITE', 'Onsite', 1, true),
      ('WORK_MODE', 'HYBRID', 'Hybrid', 2, true),
      ('WORK_MODE', 'REMOTE', 'Remote', 3, true);

INSERT INTO master_data (
    type,
    code,
    name,
    display_order,
    is_active
) VALUES
      ('EMPLOYMENT_TYPE', 'FULL_TIME', 'Full Time', 1, true),
      ('EMPLOYMENT_TYPE', 'PART_TIME', 'Part Time', 2, true),
      ('EMPLOYMENT_TYPE', 'CONTRACT', 'Contract', 3, true),
      ('EMPLOYMENT_TYPE', 'FREELANCE', 'Freelance', 4, true),
      ('EMPLOYMENT_TYPE', 'INTERNSHIP', 'Internship', 5, true);

INSERT INTO master_data (
    type,
    code,
    name,
    display_order,
    is_active
) VALUES
      ('COMMISSION_TYPE', 'FIXED', 'Fixed', 1, true),
      ('COMMISSION_TYPE', 'PERCENTAGE', 'Percentage', 2, true);

INSERT INTO master_data (
    type,
    code,
    name,
    display_order,
    is_active
) VALUES
      ('GENDER', 'MALE', 'Male', 1, true),
      ('GENDER', 'FEMALE', 'Female', 2, true),
      ('GENDER', 'OTHERS', 'Others', 3, true);