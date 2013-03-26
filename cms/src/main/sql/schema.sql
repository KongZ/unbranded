----------------------------------------
--    authenticate & authorization    --
----------------------------------------
-- client
create table oauth_client_details(
    client_id varchar(255) not null,
    resource_ids varchar(255),
    client_secret varchar(255),
    scope varchar(255),
    authorized_grant_types varchar(255),
    web_server_redirect_uri varchar(255),
    authorities varchar(255),
    access_token_validity integer,
    refresh_token_validity integer,
    additional_information text,
    autoapprove varchar(255),
    constraint pk_oauth_client_details primary key(client_id) 
);
insert into oauth_client_details values (
    'clientapp', 
    'simple', 
    'clientsecret', 
    'read,write', 
    'password,authorization_code,refresh_token',
    '',
    '',
    604800,
    2592000,
    '',
    true
);
-- oauth_client_token
create table oauth_client_token(
    token_id varchar(255),
    token blob,
    authentication_id varchar(255) not null,
    user_name varchar(255),
    client_id varchar(255),
    constraint pk_client_token primary key(authentication_id) 
);
-- oauth_access_token
create table oauth_access_token(
    token_id varchar(255),
    token blob,
    authentication_id varchar(255) not null,
    user_name varchar(255),
    client_id varchar(255),
    authentication blob,
    refresh_token varchar(255),
    constraint pk_oauth_access_token primary key(authentication_id) 
);
-- oauth_refresh_token
create table oauth_refresh_token(
    token_id varchar(255),
    token blob,
    authentication blob
);
-- oauth_code
create table oauth_code(
    code varchar(255),
    authentication blob
);
-- oauth_approvals
create table oauth_approvals(
    userid varchar(255),
    clientid varchar(255),
    scope varchar(255),
    status varchar(10),
    expiresat timestamp,
    lastmodifiedat timestamp
);
-- groups
create table groups(
    id varchar(255) not null,
    name varchar(255),
    constraint pk_groups primary key(id) 
);
-- users
create table users(
    id integer auto_increment,
    first_name varchar(255),
    last_name varchar(255),
    status integer,
    constraint pk_users primary key(id) 
);
-- users_group
create table users_groups(
    user_id integer not null,
    group_id varchar(255) not null,
    expiry_date timestamp,
    constraint pk_users_groups primary key(user_id, group_id),
    constraint fk_users_groups_user_id foreign key(user_id) references users(id) on delete cascade,
    constraint fk_users_groups_group_id foreign key(group_id) references groups(id) on delete cascade
);
-- access
create table access(
    username varchar(255) not null,
    password varchar(255),
    enabled boolean,
    user_id integer,
    constraint pk_access primary key(username),
    constraint fk_access_user_id foreign key(user_id) references users(id) on delete cascade
);
----------------------------------------
--          catalog & content         --
----------------------------------------
-- catalog
create table catalog(
    id integer auto_increment,
    title varchar(255),
    status integer,
    banner varchar(255),
    parent_id integer,
    is_root boolean,
    constraint pk_catalog primary key(id),
    constraint fk_catalog_parent_id foreign key(parent_id) references catalog(id)
);
-- content
create table content(
    id integer auto_increment,
    title varchar(255),
    synopsis text,
    status integer,
    genres varchar(255),
    poster varchar(255),
    url varchar(255),
    imdb_id varchar(100),
    comments varchar(255),
    constraint pk_content primary key(id)
);
-- catalogs_contents
create table catalogs_contents(
    catalog_id integer not null,
    content_id integer not null,
    constraint pk_catalogs_contents primary key(catalog_id, content_id),
    constraint fk_catalogs_contents_catalog_id foreign key(catalog_id) references catalog(id) on delete cascade,
    constraint fk_catalogs_contents_content_id foreign key(content_id) references content(id) on delete cascade
);
-- contents_groups
create table contents_groups(
    content_id integer not null,
    group_id varchar(255) not null,
    constraint pk_contents_groups primary key(content_id, group_id),
    constraint fk_contents_groups_content_id foreign key(content_id) references content(id) on delete cascade,
    constraint fk_contents_groups_group_id foreign key(group_id) references groups(id) on delete cascade
);
