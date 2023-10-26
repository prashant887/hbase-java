HBase is key value strore its far OLTP system efficient CURD
HBase stores all the values in Byte arrays

##Start Server
 hbase-2.5.2/bin/start-hbase.sh

 hbase shell
##Create Table
create <TABLE> <COL_FAMILY_1> <COL_FAMILY_2>
create 'customer' ,'address' ,'order'

##Insert Data
put <TABLE> <ROW_KEY> <COL_FAMILY_1:FIELD_1> <VALUE>
put <TABLE> <ROW_KEY> <COL_FAMILY_1:FIELD_2> <VALUE>

put <TABLE> <ROW_KEY> <COL_FAMILY_2:FIELD_1> <VALUE>
put <TABLE> <ROW_KEY> <COL_FAMILY_2:FIELD_2> <VALUE>

put 'customer' , 'john' , 'address:city' , 'Boston'
put 'customer' , 'john' , 'address:state' , 'MA'
put 'customer' , 'john' , 'address:country' , 'US'
put 'customer' , 'john' , 'address:zip' , '560-702-201'


put 'customer' , 'john' , 'order:id' , 'ORD-15'
put 'customer' , 'john' , 'order:product' , 'TOY'
put 'customer' , 'john' , 'order:total' , '101.20'


put 'customer' , 'paul' , 'order:date' , '2022-10-22'

put 'customer' , 'paul' , 'order:product' , '2022-10-22'


count '<TABLE>'

##Get
get <TABLE> <ROW_KEY>

get 'customer' , 'john'

get <TABLE> ,<ROW_KEY>,<CF>


get 'customer' , 'john','address'

get <TABLE> ,<ROW_KEY>,<CF>:<FIELD>


get 'customer' , 'john','address:country'

get <TABLE> ,<ROW_KEY>,[<CF>,<CF>]


get 'customer' , 'john' , ['address','order']


get 'customer' , 'john' , ['address:city','order:product']

 get 'customer' , 'paul' , ['address:city','order']


 get 'customer' , 'john' , {COLUMN=>'address'}

 ##Delete

 delete <TABLE> ,<ROW_KEY>,<CF>:<FIELD>

 delete 'customer' , 'john','address:zip'

 Delete CF
 alter <TABLE> , 'delete' => '<CF>'

 Create New CF

 alter <TABLE> , {NAME => '<CF>'}


##Version
scan '<TABLE>' , {COLUMN=>'CF:FILED',version=2}

scan 'customer' , {COLUMN=>'address:zip',version=>2}

scan 'customer' , {COLUMN=>'address:zip',VERSIONS=>1}

create 'customer_transaction' , {NAME=>'transaction',VERSIONS=>10}

##Rest API

# Foreground
$ bin/hbase rest start -p 8070

# Background, logging to a file in $HBASE_LOGS_DIR
$ bin/hbase-daemon.sh start rest -p 8070

curl -H "Accept:application/json"  http://localhost:8070/


curl -H "Accept:application/json"  http://localhost:8070/customer/schema


curl -H "Accept:application/json"  http://localhost:8070/customer/regions

https://hbase.apache.org/book.html#_examples  


Structred:Loose Data Structure 
Low Latency: Real time access using row based indeces called row key 
Random Access: Row keys allow access updated to one recored , update across single row is ACID compliant and not across entire collection 

UI : http://localhost:16010/

every row has ROWKEY CF TIMESTAMP 

Version with Lastest TS is retrived 

Row Keys are arraged in asc order 

range of row keys are distributed to region 

<ROWKEY> <CF1> <CF2>
         CF1:C1
         CF1:C2

Every CF is stored in diff files , all cols in family are fetched 

CF are part of Schema , COLS can be dynamic

count <TABLE> 

describe <TABLE>
scan 'table_name', {STARTROW=>"<start_row_key>", ENDROW=>"<end_row_key>"}
