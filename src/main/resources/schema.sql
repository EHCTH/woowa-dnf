DROP TABLE IF EXISTS character_snapshot;

CREATE TABLE character_snapshot (
    server_id           VARCHAR(20)  NOT NULL,
    character_id        VARCHAR(50)  NOT NULL,
    level               INT          NOT NULL,
    name                VARCHAR(50)  NOT NULL,
    adventure_name      VARCHAR(50)  NOT NULL,
    fame                INT          NOT NULL,
    updated_at          DATETIME(6)  NOT NULL,
    job_grow_name       VARCHAR(20),
    guild_name          VARCHAR(50),

    base_status_json    JSON,
    equipment_json      JSON,
    avatars_json        JSON,
    buff_avatars_json   JSON,
    buff_equipment_json JSON,
    creature_json       JSON,
    buff_creature_json  JSON,
    flag_json           JSON,

    PRIMARY KEY (server_id, character_id)
) ENGINE=InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;
