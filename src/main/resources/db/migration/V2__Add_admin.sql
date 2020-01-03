insert into usr (id, username, password, email, first_name, last_name, phone, active)
values (0, 'admin', '$2a$08$mTApv7MZDEZrmXT/I/dtNuxgTapu6Ck7sOL0xC.0cO9cluwzsEeK.', 'test@mail.ru',
        'Nikita', 'Kalugin', '8-800-555-3535', true);

insert into user_role (user_id, roles)
values (0, 'USER'),
       (0, 'ADMIN');
