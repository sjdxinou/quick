DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `login_name` varchar(30) NOT NULL COMMENT '用户名',
  `login_pwd` varchar(30) NOT NULL COMMENT '用户密码',
  `deal_pwd` varchar(30) DEFAULT NULL COMMENT '交易密码',
  `real_name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `id_card` varchar(30) DEFAULT NULL COMMENT '身份证',
  `gender` enum('M','F') DEFAULT 'M' COMMENT '性别(M:男,F:女)',
  `mobile` varchar(20) DEFAULT NULL COMMENT '手机号',
  `email` varchar(60) DEFAULT NULL COMMENT '邮箱',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `login_time` datetime DEFAULT NULL COMMENT '登录时间',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_count` bigint(20) DEFAULT NULL COMMENT '登录次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;