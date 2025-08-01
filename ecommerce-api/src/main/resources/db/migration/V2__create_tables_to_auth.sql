CREATE TABLE IF NOT EXISTS ecommerce.users (
    id                         BIGSERIAL PRIMARY KEY,
    user_uuid                  UUID NOT NULL UNIQUE DEFAULT gen_random_uuid(),
    email                      TEXT NOT NULL UNIQUE,
    password_hash              TEXT NOT NULL,
    full_name                  TEXT,
    is_active                  BOOLEAN NOT NULL DEFAULT TRUE,
    created_at                 TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at                 TIMESTAMPTZ,
    failed_login_attempts      INT NOT NULL DEFAULT 0,
    is_locked                  BOOLEAN NOT NULL DEFAULT FALSE,
    lock_time                  TIMESTAMPTZ,
    last_password_change_date  DATE,
    last_login_at              TIMESTAMPTZ,
    last_login_attempt_at      TIMESTAMPTZ,
    account_expiration_date    DATE
);

CREATE INDEX IF NOT EXISTS idx_users_user_uuidrole_uuid ON ecommerce.users(user_uuid);
CREATE INDEX IF NOT EXISTS idx_users_email ON ecommerce.users(email);

CREATE TABLE IF NOT EXISTS ecommerce.roles (
    id               SERIAL PRIMARY KEY,
    name             TEXT NOT NULL UNIQUE,
    description      TEXT
);

CREATE TABLE IF NOT EXISTS ecommerce.user_roles (
    user_id          BIGINT NOT NULL REFERENCES ecommerce.users(id) ON DELETE CASCADE,
    role_id          INT NOT NULL REFERENCES ecommerce.roles(id) ON DELETE CASCADE,
    assigned_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, role_id)
);
