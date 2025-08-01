-- Garante extensão UUID e criptografia
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- ========================================================
-- ROLES
-- ========================================================
INSERT INTO ecommerce.roles (name, description) VALUES
  ('ROLE_ADMIN', 'Administrador do sistema'),
  ('ROLE_USER', 'Usuário comum'),
  ('ROLE_MANAGER', 'Gerente do sistema')
ON CONFLICT (name) DO NOTHING;

-- ========================================================
-- USERS
-- ========================================================

-- ADMIN
INSERT INTO ecommerce.users (
    user_uuid, email, password_hash, full_name,
    is_active, created_at, updated_at,
    failed_login_attempts, is_locked, lock_time,
    last_password_change_date, last_login_at, last_login_attempt_at,
    account_expiration_date
) VALUES (
    '8e1f5b2b-12cd-4a37-9c89-4cde2a9a5d67',
    'admin@dominio.com',
    crypt('SenhaForte123', gen_salt('bf')),
    'Admin Master',
    TRUE, now(), now(),
    0, FALSE, NULL,
    current_date, now(), now(), NULL
) ON CONFLICT (email) DO NOTHING;

-- USER ativo
INSERT INTO ecommerce.users (
    user_uuid, email, password_hash, full_name,
    is_active, created_at, updated_at,
    failed_login_attempts, is_locked, lock_time,
    last_password_change_date
) VALUES (
    gen_random_uuid(),
    'user@dominio.com',
    crypt('SenhaUser123', gen_salt('bf')),
    'Usuário Ativo',
    TRUE, now(), now(),
    0, FALSE, NULL,
    current_date
) ON CONFLICT (email) DO NOTHING;

-- BLOQUEADO
INSERT INTO ecommerce.users (
    user_uuid, email, password_hash, full_name,
    is_active, created_at, updated_at,
    failed_login_attempts, is_locked, lock_time,
    last_password_change_date
) VALUES (
    gen_random_uuid(),
    'blocked@dominio.com',
    crypt('SenhaBloqueada123', gen_salt('bf')),
    'Usuário Bloqueado',
    TRUE, now(), now(),
    5, TRUE, now(),
    current_date
) ON CONFLICT (email) DO NOTHING;

-- SENHA EXPIRADA
INSERT INTO ecommerce.users (
    user_uuid, email, password_hash, full_name,
    is_active, created_at, updated_at,
    failed_login_attempts, is_locked, lock_time,
    last_password_change_date
) VALUES (
    gen_random_uuid(),
    'expired@dominio.com',
    crypt('SenhaExpirada123', gen_salt('bf')),
    'Usuário com Senha Expirada',
    TRUE, now(), now(),
    0, FALSE, NULL,
    current_date - interval '7 months'
) ON CONFLICT (email) DO NOTHING;

-- INATIVO
INSERT INTO ecommerce.users (
    user_uuid, email, password_hash, full_name,
    is_active, created_at, updated_at,
    failed_login_attempts, is_locked, lock_time,
    last_password_change_date
) VALUES (
    gen_random_uuid(),
    'inactive@dominio.com',
    crypt('SenhaInativo123', gen_salt('bf')),
    'Usuário Inativo',
    FALSE, now(), now(),
    0, FALSE, NULL,
    current_date
) ON CONFLICT (email) DO NOTHING;

-- ========================================================
-- ASSOCIAÇÃO DE USERS <-> ROLES
-- ========================================================

-- ADMIN → ROLE_ADMIN
INSERT INTO ecommerce.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM ecommerce.users u, ecommerce.roles r
WHERE u.email = 'admin@dominio.com' AND r.name = 'ROLE_ADMIN'
  AND NOT EXISTS (
    SELECT 1 FROM ecommerce.user_roles ur WHERE ur.user_id = u.id AND ur.role_id = r.id
);

-- USER → ROLE_USER
INSERT INTO ecommerce.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM ecommerce.users u, ecommerce.roles r
WHERE u.email = 'user@dominio.com' AND r.name = 'ROLE_USER'
  AND NOT EXISTS (
    SELECT 1 FROM ecommerce.user_roles ur WHERE ur.user_id = u.id AND ur.role_id = r.id
);

-- BLOCKED → ROLE_USER
INSERT INTO ecommerce.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM ecommerce.users u, ecommerce.roles r
WHERE u.email = 'blocked@dominio.com' AND r.name = 'ROLE_USER'
  AND NOT EXISTS (
    SELECT 1 FROM ecommerce.user_roles ur WHERE ur.user_id = u.id AND ur.role_id = r.id
);

-- EXPIRED → ROLE_USER
INSERT INTO ecommerce.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM ecommerce.users u, ecommerce.roles r
WHERE u.email = 'expired@dominio.com' AND r.name = 'ROLE_USER'
  AND NOT EXISTS (
    SELECT 1 FROM ecommerce.user_roles ur WHERE ur.user_id = u.id AND ur.role_id = r.id
);

-- INACTIVE → ROLE_MANAGER
INSERT INTO ecommerce.user_roles (user_id, role_id)
SELECT u.id, r.id
FROM ecommerce.users u, ecommerce.roles r
WHERE u.email = 'inactive@dominio.com' AND r.name = 'ROLE_MANAGER'
  AND NOT EXISTS (
    SELECT 1 FROM ecommerce.user_roles ur WHERE ur.user_id = u.id AND ur.role_id = r.id
);
