### MySQL日期获取
#### 本月第一天
`select date_add(curdate(), interval - day(curdate()) + 1 day);`
#### 本月最后一天
`select last_day(curdate());`
#### 上月第一天
`select date_add(curdate()-day(curdate())+1,interval -1 month);`
#### 上月最后一天
`select last_day(date_sub(now(),interval 1 month));`
#### 下月第一天
`select date_add(curdate()-day(curdate())+1,interval 1 month);`
#### 下月最后一天
`select last_day(date_sub(now(),interval -1 month));`
#### 本月天数
`select day(last_day(curdate()));`
#### 上月今天的当前日期
`select date_sub(curdate(), interval 1 month);`
#### 上月今天的当前时间（时间戳）
`select unix_timestamp(date_sub(now(),interval 1 month));`
#### 获取当前时间与上个月之间的天数
`select datediff(curdate(), date_sub(curdate(), interval 1 month));`
***
### MySQL GROUP BY 
#### GROUP BY 某些字段获取最后一条
```roomsql
SELECT
Id,
Name,
SUBSTRING_INDEX(
GROUP_CONCAT( Other_Columns ORDER BY Id DESC SEPARATOR '||' ),
    '||', 1 ) Other_Columns
FROM
messages
GROUP BY Name
```
***
### 带条件UPDATE
```roomsql
-- 更新sql --
UPDATE cost_order_total t
JOIN (
	SELECT
		cot.id,
		SUBSTRING_INDEX(GROUP_CONCAT(cir.item_rate ORDER BY cir.validate_start_time DESC SEPARATOR '||' ),
			'||', 1 ) mall_tax,
		SUBSTRING_INDEX(GROUP_CONCAT(c.rate ORDER BY b.validate_start_time DESC SEPARATOR '||' ),
			'||',1 ) normal_tax
	FROM
		cost_order_total cot
	JOIN cost_market_item cci ON cci.delete_flag = 0 AND cci.mall_code = cot.mall_code AND cci.cost_item_code = cot.cost_item_code
	LEFT JOIN cost_market_item_rate_relation cir ON cci.id = cir.cost_market_item_id AND cir.validate_start_time <= cot.begin_time AND cir.company_code = cot.company_code
	LEFT JOIN cost_item a ON a.Item_code = cot.cost_item_code AND a.delete_flag = 0
	LEFT JOIN cost_item_rate_relation b ON a.id = b.cost_item_id
	LEFT JOIN cost_rate_config c ON b.rate_config_id = c.id
	GROUP BY cot.id
) v_t ON t.id = v_t.id
SET t.cost_rate = ifnull( v_t.mall_tax, v_t.normal_tax)
WHERE	t.id = 690746


```

### 字符串处理
#### 314>336>478>484 拆分为为4个字段
```roomsql
select category_directory_id,
SUBSTRING_INDEX(category_directory_id, '>',  1) c1,
SUBSTRING_INDEX(SUBSTRING_INDEX(category_directory_id,'>',2), '>', -1) c2,
SUBSTRING_INDEX(SUBSTRING_INDEX(category_directory_id,'>',3), '>', -1) c3,
SUBSTRING_INDEX(SUBSTRING_INDEX(category_directory_id,'>',4), '>', -1) c4
from invest_brand_category where delete_flag=0
```
### insert into by select
```roomsql
INSERT INTO table2
(column_name(s))
SELECT column_name(s)
FROM table1;
```
