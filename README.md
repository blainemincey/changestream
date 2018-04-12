# changestream
Example application using Change Streams introduced in MongoDB 3.6

1.  Install Java (at least 1.8)
2.  Install Apache Maven
3.  Install MongoDB 3.6 (must be running in Replica Set mode which can be a single instance)

After cloning repo, cd into changestream directory.

In a terminal window and if Maven and Java are on your classpath:\
`mvn compile`

This will compile the java classes.

Next, in the same terminal:\
`mvn exec:java -Pproduce`

This will begin writing random values for MDB stock prices to a collection at random periods.  Output will be similar to:\
`{ "_id" : { "$oid" : "5acfc989c527044f183a8dce" }, "Symbol" : "MDB", "Price" : 88.49 }`

Then, in a separate terminal:\
`mvn exec:java -Plisten`

When the stock price is greater than 75.00, the change stream listener will output the document similar to:\
`received: Document{{_id=5acfc9f0c527044f35951f8b, Symbol=MDB, Price=83.45}}`

