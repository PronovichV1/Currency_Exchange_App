CREATE TABLE IF NOT EXISTS currencies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    full_name VARCHAR UNIQUE NOT NULL,
    code VARCHAR UNIQUE NOT NULL,
    sign VARCHAR UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS exchange_rates (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    base_currency_id INTEGER NOT NULL,
    target_currency_id INTEGER NOT NULL,
    rate DECIMAL NOT NULL,
    FOREIGN KEY (base_currency_id) REFERENCES currencies(id),
    FOREIGN KEY (target_currency_id) REFERENCES currencies(id),
    CONSTRAINT key_name UNIQUE (base_currency_id, target_currency_id)
);

INSERT OR IGNORE INTO currencies (code, full_name, sign) VALUES
('USD', 'US Dollar', '$'),
('EUR', 'Euro', '€'),
('PLN', 'Polish Zloty', 'zł');

INSERT OR IGNORE INTO exchange_rates (base_currency_id, target_currency_id, rate) VALUES
(1, 2, 0.88),
(1, 3, 3.80),
(3,2, 0.23);