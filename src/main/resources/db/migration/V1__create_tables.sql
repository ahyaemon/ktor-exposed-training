create table companies (
    id text primary key,
    corporate_name text not null,
    representative_name text not null,
    phone_number text not null,
    post_code text not null,
    address text not null
);

create table users (
    id text primary key,
    company_id text references companies(id) not null,
    name text not null,
    mail_address text unique not null,
    password text not null
);

create table suppliers (
    id text primary key,
    company_id text references companies(id) not null,
    corporate_name text not null,
    representative_name text not null,
    phone_number text not null,
    post_code text not null,
    address text not null
);

create table supplier_bank_accounts (
    id text primary key,
    supplier_id text references suppliers(id) not null,
    corporate_name text not null,
    representative_name text not null,
    phone_number text not null,
    post_code text not null,
    address text not null
);

create table invoices (
    id text primary key,
    company_id text references companies(id) not null,
    supplier_id text references suppliers(id) not null,
    created_user_id text references users(id) not null,
    issued_date date not null,
    payment_amount integer not null,
    fee integer not null,
    fee_rate decimal(3, 2) not null,
    consumption_tax integer not null,
    consumption_tax_rate decimal(3, 2) not null,
    billing_amount integer not null,
    payment_due_date date not null,
    status text not null
);
