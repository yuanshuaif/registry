-- TCC 动作注册表：参与方启动时自注册，长期存在
-- 记录"谁在哪儿、用哪些方法"实现了某个 TCC 动作；发起方按此表通用调用
CREATE TABLE IF NOT EXISTS t_branch_registry (
    action_name     VARCHAR(64) PRIMARY KEY,   -- 业务动作句柄，如 deductStorage
    service_name    VARCHAR(64)  NOT NULL,     -- 参与方服务名
    service_url     VARCHAR(128) NOT NULL,     -- 参与方基地址（调用 {service_url}/tcc/invoke）
    try_method      VARCHAR(128) NOT NULL,     -- 参与方自己声明的三阶段方法名（审计用）
    confirm_method  VARCHAR(128) NOT NULL,
    cancel_method   VARCHAR(128) NOT NULL,
    state           VARCHAR(16)  NOT NULL DEFAULT 'ACTIVE',
    registered_at   TIMESTAMP    DEFAULT CURRENT_TIMESTAMP
);
