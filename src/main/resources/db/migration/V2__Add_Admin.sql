insert into user (id, username, password, email, first_name, last_name, phone, active)
values (1, 'admin', '$2a$08$mTApv7MZDEZrmXT/I/dtNuxgTapu6Ck7sOL0xC.0cO9cluwzsEeK.', 'kaluginnikita1998@mail.ru',
        'Nikita', 'Kalugin', '+7-951-607-9856', true);

insert into user_role (user_id, roles)
values (1, 'USER'),
       (1, 'ADMIN');