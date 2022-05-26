INSERT INTO public.app_user(
    id,  email, first_name, last_name, password, username, enabled, locked)
VALUES (1, 'admin@admin.com', 'admin', 'admin', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'admin', true, false),
       (2, 'client@client.com', 'client', 'client', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'client', true, false),
       (3, 'disabled@disabled.com', 'disabled', 'disabled', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'disabled', false , false),
       (4, 'locked@locked.com', 'locked', 'locked', '$2a$12$gcMNSztc70C51IaVZgtFT.rzrATBAzOdrRNpe0r7vHBSJmC2XShbC', 'locked', true, true );