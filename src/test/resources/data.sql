INSERT INTO public.app_user(
    id,  email, first_name, last_name, password, username, enabled, locked, email_confirm_uuid)
VALUES (1, 'admin@admin.com', 'admin', 'admin', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'admin', true, false, 'admin_email_confirm_token'),
       (2, 'client@client.com', 'client', 'client', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'client', true, false, ''),
       (3, 'disabled@disabled.com', 'disabled', 'disabled', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'disabled', false , false, '1d67bbd9-d119-4ae5-8b3e-2006b386arrr'),
       (4, 'confirmemail@confirmemail.com', 'confirmemail', 'confirmemail', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'confirmemail', false , false, 'confirmemail_token'),
       (5, 'locked@locked.com', 'locked', 'locked', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'locked', true, true, '' ),
       (7, 'changepassword@changepassword.com', 'changepassword', 'changepassword', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'changepassword', true, false, '');

INSERT INTO public.app_user_app_user_roles(app_user_id, app_user_roles)
VALUES (1, 0),
       (2, 1),
       (7, 1);