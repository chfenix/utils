### MySQL日期获取
#### 本月第一天
select date_add(curdate(), interval - day(curdate()) + 1 day);
#### 本月最后一天
select last_day(curdate());
#### 上月第一天
select date_add(curdate()-day(curdate())+1,interval -1 month);
#### 上月最后一天
select last_day(date_sub(now(),interval 1 month));
#### 下月第一天
select date_add(curdate()-day(curdate())+1,interval 1 month);
#### 下月最后一天
select last_day(date_sub(now(),interval -1 month));
#### 本月天数
select day(last_day(curdate()));
#### 上月今天的当前日期
select date_sub(curdate(), interval 1 month);
#### 上月今天的当前时间（时间戳）
select unix_timestamp(date_sub(now(),interval 1 month));
#### 获取当前时间与上个月之间的天数
select datediff(curdate(), date_sub(curdate(), interval 1 month));
