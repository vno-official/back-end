-- Phase 1: Auth + Multi-tenant Schema
-- Tables: organizations, users, memberships, invitations, workspaces, pages

-- 1. Organizations (Core tenant table)
CREATE TABLE organizations (
    id                  BIGSERIAL PRIMARY KEY,
    name                TEXT NOT NULL,
    slug                TEXT UNIQUE NOT NULL,
    plan                TEXT DEFAULT 'free',
    stripe_customer_id  TEXT,
    trial_ends_at       TIMESTAMPTZ,
    created_at          TIMESTAMPTZ DEFAULT now(),
    deleted_at          TIMESTAMPTZ
);

CREATE INDEX idx_organizations_slug ON organizations(slug) WHERE deleted_at IS NULL;

-- 2. Users
CREATE TABLE users (
    id                  BIGSERIAL PRIMARY KEY,
    email               TEXT UNIQUE NOT NULL,
    password_hash       TEXT,
    name                TEXT,
    avatar_url          TEXT,
    created_at          TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_users_email ON users(email);

-- 3. Memberships (User-Organization junction with roles)
CREATE TABLE memberships (
    id                  BIGSERIAL PRIMARY KEY,
    user_id             BIGINT NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    organization_id     BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    role                TEXT NOT NULL CHECK (role IN ('OWNER','ADMIN','MEMBER')),
    joined_at           TIMESTAMPTZ DEFAULT now(),
    UNIQUE(user_id, organization_id)
);

CREATE INDEX idx_memberships_user ON memberships(user_id);
CREATE INDEX idx_memberships_org ON memberships(organization_id);

-- 4. Invitations
CREATE TABLE invitations (
    id                  BIGSERIAL PRIMARY KEY,
    organization_id     BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    email               TEXT NOT NULL,
    role                TEXT NOT NULL DEFAULT 'MEMBER',
    token               TEXT UNIQUE NOT NULL,
    invited_by          BIGINT REFERENCES users(id),
    expires_at          TIMESTAMPTZ NOT NULL,
    accepted_at         TIMESTAMPTZ,
    created_at          TIMESTAMPTZ DEFAULT now()
);

CREATE INDEX idx_invitations_token ON invitations(token) WHERE accepted_at IS NULL;
CREATE INDEX idx_invitations_org ON invitations(organization_id);

-- 5. Workspaces
CREATE TABLE workspaces (
    id                  BIGSERIAL PRIMARY KEY,
    organization_id     BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    name                TEXT NOT NULL,
    icon_emoji          TEXT,
    cover_url           TEXT,
    default_permission  TEXT DEFAULT 'organization',
    created_by          BIGINT REFERENCES users(id),
    created_at          TIMESTAMPTZ DEFAULT now(),
    deleted_at          TIMESTAMPTZ
);

CREATE INDEX idx_workspaces_org ON workspaces(organization_id) WHERE deleted_at IS NULL;

-- 6. Pages (with hierarchical structure and soft delete)
CREATE TABLE pages (
    id                  BIGSERIAL PRIMARY KEY,
    organization_id     BIGINT NOT NULL REFERENCES organizations(id) ON DELETE CASCADE,
    workspace_id        BIGINT REFERENCES workspaces(id) ON DELETE CASCADE,
    parent_page_id      BIGINT REFERENCES pages(id) ON DELETE SET NULL,
    title               TEXT NOT NULL DEFAULT 'Untitled',
    icon_emoji          TEXT,
    cover_url           TEXT,
    path                TEXT,
    visibility          TEXT DEFAULT 'inherit',
    is_locked           BOOLEAN DEFAULT false,
    created_by          BIGINT REFERENCES users(id),
    created_at          TIMESTAMPTZ DEFAULT now(),
    updated_at          TIMESTAMPTZ DEFAULT now(),
    deleted_at          TIMESTAMPTZ
);

CREATE INDEX idx_pages_org ON pages(organization_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_pages_workspace ON pages(workspace_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_pages_parent ON pages(parent_page_id) WHERE deleted_at IS NULL;
CREATE INDEX idx_pages_path ON pages(path) WHERE deleted_at IS NULL;
