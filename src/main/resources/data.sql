INSERT INTO client (
    client_name,
    uri,
    phone_number,
    street_address,
    city,
    state,
    zip_code
) VALUES (
    'Aquent',
    'aquent.com',
    '4567778912',
    '123 Any St.',
    'Hudson',
    'VA',
    '28801'
), (
    'JohDeere',
    'jd.com',
    '1234567892',
    '123 Any St.',
    'Asheville',
    'NC',
    '28801'
);


INSERT INTO person (
    first_name,
    last_name,
    email_address,
    street_address,
    city,
    state,
    zip_code,
    client_id
) VALUES (
    'John',
    'Smith',
    'fake1@aquent.com',
    '123 Any St.',
    'Asheville',
    'NC',
    '28801',
    1
), (
    'Jane',
    'Smith',
    'fake2@aquent.com',
    '123 Any St.',
    'Asheville',
    'NC',
    '28801',
    1
), (
    'Pam',
    'Smith',
    'fake2@aquent.com',
    '123 Any St.',
    'Asheville',
    'NC',
    '28801',
    2
), (
    'Jim',
    'Smith',
    'fake2@aquent.com',
    '123 Any St.',
    'Asheville',
    'NC',
    '28801',
    2
);


