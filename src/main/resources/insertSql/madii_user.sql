/*
-- Query: select *
from t_user
where 1=1
LIMIT 0, 300

-- Date: 2024-03-16 21:43
*/

INSERT INTO t_user (`user_id`,`created_at`,`modified_at`,`login_id`,`password`,`image`,`nickname`,`role`,`provider`,`social_id`,`agrees_marketing`) 
VALUES (1,now(),now(),'9uttery','4fb62257511eae4c15940fb30c0fcb13b1859c8145ad5ad656612439fbb1a642','https://cdn.madii.kr/956438 bytes.png_1709128295913','구떠리','ROLE_USER',NULL,NULL,'1');