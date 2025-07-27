CREATE TABLE IF NOT EXISTS ecommerce.users (
    id               UUID PRIMARY KEY,
    email            TEXT NOT NULL UNIQUE,
    password_hash    TEXT NOT NULL,
    full_name        TEXT,
    is_active        BOOLEAN NOT NULL DEFAULT TRUE,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at       TIMESTAMPTZ
);

CREATE TABLE IF NOT EXISTS ecommerce.roles (
    id               SERIAL PRIMARY KEY,
    name             TEXT NOT NULL UNIQUE,
    description      TEXT
);

CREATE TABLE IF NOT EXISTS ecommerce.user_roles (
    user_id          UUID NOT NULL REFERENCES ecommerce.users(id) ON DELETE CASCADE,
    role_id          INT  NOT NULL REFERENCES ecommerce.roles(id) ON DELETE CASCADE,
    assigned_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
    PRIMARY KEY (user_id, role_id)
);

CREATE INDEX IF NOT EXISTS idx_audit_log_user_id ON ecommerce.audit_log(user_id);
CREATE INDEX IF NOT EXISTS idx_users_email ON ecommerce.users(email);