CREATE SCHEMA IF NOT EXISTS ecommerce;

CREATE TABLE IF NOT EXISTS ecommerce.audit_log (
    id               BIGSERIAL PRIMARY KEY,
    user_id          UUID NOT NULL,
    action           TEXT NOT NULL,           -- Ex: 'LOGIN', 'LOGOUT', 'PASSWORD_CHANGE'
    ip_address       INET,
    user_agent       TEXT,
    created_at       TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE INDEX IF NOT EXISTS idx_audit_log_user_id ON ecommerce.audit_log(user_id);
