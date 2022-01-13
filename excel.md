### 日期相关
#### 获取每个月最后一天
`EOMONTH(A5,0)`

#### 获取两个日期之前天数
`DAYS(EOMONTH(A8,0),A8)`

### 获取日期年、月
`YEAR(A7)  MONTH(A7)`

### 增加一个月
`DATE(YEAR(C2),MONTH(C2)+1,DAY(C2))`
***
### 字符串处理
#### 多行拼接至一个单元格中，逗号分隔
`TEXTJOIN(",",TRUE,OFFSET(A:A,0,,20))`
***
### 单元格相关
#### 动态获取某个单元格值
`INDIRECT(CHAR(65+N1)&"7")`
