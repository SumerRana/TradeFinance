# TradeFinance
Decentralized Trade finance management system to ensure transparency and security in Trade Finance


This is a Java code for a letter of credit (LOC) transfer chaincode implemented using the Hyperledger Fabric platform. The code consists of two classes, 
LetterOfCredit and LetterOfCreditStatus.

The LetterOfCredit class represents the LOC asset. It contains the necessary attributes for an LOC such as id, expiryDate, buyerName, bank, seller, amount,
and status. The class also contains methods to get the values of these attributes.

The LetterOfCreditStatus class is the main contract that implements the business logic for the LOC transfer chaincode. It contains methods to initialize 
the ledger, request a new LOC, and get the status of an existing LOC. The class also defines an enum for the error messages that can be thrown during the 
execution of the chaincode.

The requestNewLOC method in the LetterOfCreditStatus class is used to request a new LOC. It takes in the necessary input parameters and creates a new instance
of the LetterOfCredit class. It then checks if an LOC with the same id already exists in the ledger, and throws an error if it does. Otherwise, it sets the 
status of the new LOC to REQUESTED and returns it.

Overall, this code provides a basic implementation for an LOC transfer chaincode using the Hyperledger Fabric platform. Additional methods can be added to 
the LetterOfCreditStatus class to implement more complex business logic.
