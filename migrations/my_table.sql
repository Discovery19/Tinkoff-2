--liquibase formatted sql

--changeset nevinture:1
create table links (
    id bigserial primary key,
    url text NOT NULL,
    description text,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    last_checked TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
create table chats (
    id bigint primary key
);
create table link_chat (
    link_id bigint references links(id) on delete cascade,
    chat_id bigint references chats(id) on delete cascade,
    primary key (link_id, chat_id)
);
