insert into user (id, username, password, active)
values (1, 'admin', '$2a$08$mTApv7MZDEZrmXT/I/dtNuxgTapu6Ck7sOL0xC.0cO9cluwzsEeK.', true);

insert into user_role (user_id, roles)
values (1, 'USER'),
       (1, 'ADMIN');