INSERT INTO app_user(id, email, first_name, last_name, password, username, enabled, locked, email_confirm_uuid)
VALUES (1, 'admin@admin.com', 'admin', 'admin', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'admin',
        true, false, 'admin_email_confirm_token');

INSERT INTO app_user_app_user_roles(app_user_id, app_user_roles)
VALUES (1, 0),
       (1, 1);