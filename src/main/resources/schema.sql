-- can log in as different users
-- users can have different accounts, like for a shared account
-- multiple users can have access to the same account
-- each account has their own transactions

CREATE TABLE IF NOT EXISTS users (
    id              SERIAL PRIMARY KEY,
    username        VARCHAR(32) UNIQUE NOT NULL,
    hashed_password VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS accounts (
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(32) NOT NULL,
    description     TEXT,
    color           INTEGER, --RBG int, randomly generated when creating a new account
    owner_id        INTEGER NOT NULL,

    UNIQUE (name, owner_id),
    FOREIGN KEY(owner_id) REFERENCES users(id) ON DELETE CASCADE -- delete the account if the owner user is deleted
);

CREATE TABLE IF NOT EXISTS account_users (
    user_id         INTEGER NOT NULL,
    account_id      INTEGER NOT NULL,

    UNIQUE (user_id, account_id),

    FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE, -- remove the user from the table if the referred user is deleted
    FOREIGN KEY(account_id) REFERENCES accounts(id) ON DELETE CASCADE -- remove the user from the table if the referred account is deleted
);

CREATE TABLE IF NOT EXISTS transactions (
    id              SERIAL PRIMARY KEY,
    description     TEXT,
    amount          DECIMAL NOT NULL,
    timestamp_ms    BIGINT NOT NULL DEFAULT (EXTRACT(EPOCH FROM NOW()) * 1000), -- milliseconds since 1 Jan 1970

    account_id      INTEGER NOT NULL,

    FOREIGN KEY(account_id) REFERENCES accounts(id) ON DELETE CASCADE -- delete the transaction if the referred account is deleted
);