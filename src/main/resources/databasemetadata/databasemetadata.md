# How To Play Databasemetadata  

## 目录  

***

> 什么是databasemetadata
>
> > 基本概念  
> >
> > 常用的方法
>
>  databasemetadata的应用场景 
>
> > 获取数据库的基本信息
> >
> > 采集数据库元数据信息
> >
> > 反向设计表 
>
> 代码示例





---

## 一. 什么是databasemetadata
### 基本概念
​	DatabaseMetaData类是java.sql包中的类，利用它可以获取我们连接到的数据库的结构、存储等很多信息。如：  
1、数据库与用户，数据库标识符以及函数与存储过程。  
2、数据库限制。  
3、数据库支持不支持的功能。  
4、架构、编目、表、列和视图等。

###  常用的方法
 	通过调用DatabaseMetaData的各种方法，程序可以动态的了解一个数据库。由于这个类中的方法非常的多那么就介绍几个常用的方法来给大家参考。  

* 获取databasemetadata的方式  

  ```java
  // 获取jdbc连接
  Connection connection = DriverManager.getConnection(url, username, password);
  // 从连接中创建databasemetadata实例
  DatabaseMetaData metaData = connection.getMetaData();
  ```



* 获得当前数据库以及驱动的信息  

  ```java
  // 用以获得当前数据库是什么数据库。比如oracle，access等。返回的是字符串。
  metaData.getDatabaseProductName()：
  // 获得数据库的版本。返回的字符串。
  metaData.getDatabaseProductVersion()：
  // 获得驱动程序的版本。返回字符串。
  metaData.getDriverVersion()：
  // 获得当前数据库的类型信息
  metaData.getTypeInfo()：
  ```

  

* 获得所有的数据库信息 

  ​	一般的数据库是通过`metaData.getSchemas()`方法来获取数据库的`ResultSet`;但是实际测试，mysql数据库比较特殊，mysql将自己的数据库归类为catalog， 所以要通过databasemetadata来获取mysql的数据库信息，需要通过`metaData.getCatalogs()`来获取；

  

* 获得当前数据库中表/视图的信息 

  ```java
  metaData.getTables(String catalog,String schema,String tableName,String[] types)
  这个方法带有四个参数，它们表示的含义如下：
  
  String catalog：要获得表所在的编目。`" '' "`意味着没有任何编目，Null表示所有编目。
  String schema：要获得表所在的模式。`" '' "`意味着没有任何模式，Null表示所有模式。
  String tableName：指出要返回表名与该参数匹配的那些表，注意这里支持sql的模糊匹配，如`%info`就是查询后缀为info的表
  String types：一个指出返回何种表的数组。可能的数组项是："TABLE"、"VIEW"、"SYSTEM TABLE"， "GLOBAL TEMPORARY"，"LOCAL  TEMPORARY"，"ALIAS"，"SYSNONYM"  
  
  通过getTables())方法返回的结果集中的每个表都有下面是10字段的描述信息，而且只有10个。通常我们用到的也就是标红的几个字段。而且在结果集中直接使用下面字段前面的序号即可获取字段值。
  
  1.TABLE_CAT(String)      => 表所在的编目(可能为空)  
  2.TABLE_SCHEM (String)   => 表所在的模式(可能为空) 
  3.TABLE_NAME(String)     => 表的名称
  4.TABLE_TYPE(String)     => 表的类型，典型的有 "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL  TEMPORARY", "ALIAS", "SYNONYM". 
  5.REMARKS(String)        => 解释性的备注（可能为空，mysql为空）
  6.TYPE_CAT(String)       =>编目类型(may be null) 
  7.TYPE_SCHEM(String)     => 模式类型(may be null) 
  8.TYPE_NAME(String)      => 类型名称(may be null) 
  9.SELF_REFERENCING_COL_NAME(String) => name of the designated "identifier" column of a typed table (may be null) 
  10.REF_GENERATION   (String)    => specifies how values in SELF_REFERENCING_COL_NAME are created.
  它的值有："SYSTEM"、"USER"、"DERIVED"，也可能为空。
  ```

  
+ 获得当前数据库中函数/存储过程的信息

  ```java
  // 只能获取函数的基本信息
  metaData.getFunctions(String catalog, String schemaPattern, String functionNamePattern);
  // 既能获取函数也可获取存储过程的基本信息（一般采用这个方法）
  metaData.getProcedures(String catalog, String schemaPattern, String procedureNamePattern);
  
  入参语法与metaData.getTables(String catalog,String schema,String tableName,String[] types)类似;
  通过getProcedures可以获得9个字段，但是我们实际使用到的分别是：
  1.PROCEDURE_CAT(String)    => 表所在的编目(可能为空)  
  2.PROCEDURE_SCHEM (String) => 表所在的模式(可能为空) 
  3.PROCEDURE_NAME(String)   => 存储过程/函数名称
  7.REMARKS(String)          => 解释性的备注
  8.PROCEDURE_TYPE(String)   => 对象类型：1：函数，2：存储过程
  ```

  

+ 获得某个表的列信息 

  ```java
  metaData.getColumns(String catalog, String schemaPattern,String tableNamePattern, String columnNamePattern);
  入参语法与metaData.getTables(String catalog,String schema,String tableName,String[] types)类似;
  值得注意的是：
      若tableNamePattern不填写，则查询对应整个数据库的字段；
  	若columnNamePattern不填写，则查询的是这个表的字段；
      
  通过getColumns（）方法返回的结果集中的每一列都有下面是23个字段段的描述信息，而且只有23个。通常我们用到的也就是标红的字段。而且在结果集中直接使用下面字段前面的序号即可获取字段值：
  1.TABLE_CAT String => table catalog (may be null)
  2.TABLE_SCHEM String => table schema (may be null)
  3.TABLE_NAME String => table name (表名称)
  4.COLUMN_NAME String => column name(列名)
  5.DATA_TYPE int => SQL type from java.sql.Types(列的数据类型)
  6.TYPE_NAME String => 列类型名称
  7.COLUMN_SIZE int => 列类型的长度
  8.BUFFER_LENGTH int => 列最大缓存容量
  9.DECIMAL_DIGITS int => the number of fractional digits. Null is returned for data types where DECIMAL_DIGITS is not applicable.
  10.NUM_PREC_RADIX int => Radix (typically either 10 or 2)
  11.NULLABLE int => 是否可为空NULL.
  12.REMARKS String => 字段注释
  13.COLUMN_DEF String => default value for the column, (may be null)
  14.SQL_DATA_TYPE int => unused
  15.SQL_DATETIME_SUB int => unused
  16.CHAR_OCTET_LENGTH int => for char types the maximum number of bytes in the column
  17.ORDINAL_POSITION int => index of column in table (starting at 1)
  18.IS_NULLABLE String => 是否可为空NULL 英文
  19.SCOPE_CATLOG String => catalog of table that is the scope of a reference attribute (null if DATA_TYPE isn't REF)
  20.SCOPE_SCHEMA String => schema of table that is the scope of a reference attribute (null if the DATA_TYPE isn't REF)
  21.SCOPE_TABLE String => table name that this the scope of a reference attribure (null if the DATA_TYPE isn't REF)
  22.SOURCE_DATA_TYPE short => source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types
  23.IS_AUTOINCREMENT String => Indicates whether this column is auto incremented
  ```

  
- 获得表的主键信息

  ```java
  metaData.getPrimaryKeys(String catalog, String schema,String table);
  入参语法与metaData.getTables(String catalog,String schema,String tableName,String[] types)类似;
  但是值得注意的是有些数据库不需要填写table，可直接查处整个数据库的主键；有些数据库（如mysql）,则必须填写table，只能一个表一个表的查询主键；
  通过getPrimaryKeys（）方法返回的结果集有6个字段：
  1.TABLE_CAT => 目录名
  2.TABLE_SCHEM => 模式名
  3.TABLE_NAME => 表名
  4.COLUMN_NAME => 列名
  5.KEY_SEQ => 键所在表中的序号
  6.PK_NAME => 键名（PRIMARY）
  ```

  

- 获得表的索引信息

  ```java
  metaData.getIndexInfo(String catalog, String schema, String table,boolean unique, boolean approximate)
  入参语法与metaData.getTables(String catalog,String schema,String tableName,String[] types)类似, 
  unique: 如果为true，则只返回唯一值的索引，如果为false，则返回索引，无论是否唯一;
  approximate: 如果为真，则允许结果反映近似值或数据值外的值；如果为假，则要求结果准确;
  主键其实本质也是一种索引，所以getIndexInfo里头返回的结果集中也适合包含主键信息的；
  通过getPrimaryKeys（）方法返回的结果集有13个字段，罗列我们常用的字段：
  1.TABLE_CAT => 目录名
  2.TABLE_SCHEM => 模式名
  3.TABLE_NAME => 表名
  4.NON_UNIQUE => 唯一值的索引(部分数据库不处理这个，为永远false)
  6.INDEX_NAME => 索引名
  7.TYPE => 索引类型
  8.ORDINAL_POSITION => 索引字段位置
  9.COLUMN_NAME => 索引的字段名字
  10.ASC_OR_DESC => 索引的排序方式
  ```



## 二.databasemetadata的应用场景

