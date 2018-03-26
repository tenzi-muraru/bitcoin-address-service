# BitcoinAddressService

How to start the BitcoinAddressService application
---

1. Run `mvn clean install` to build your application
2. Start application with `java -jar target/bitcoin-address-service-1.0-SNAPSHOT.jar server config.yml`
3. To check that your application is running enter url `http://localhost:8080`

Bitcoin address
---
1. Retrieve all unspent output transactions for a Bitcoin address

GET     /address/{bitcoinAddress}

eg.
curl -i "localhost:8080/address/1A8JiWcwvpY7tAopUkSnGuEYHmzGYfZPiq"