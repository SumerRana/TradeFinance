package TradeFinance;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import com.owlike.genson.Genson;

@Contract(name = "TradeFinance", info = @Info(title = "TradeFinance contract", description = "Letter of Credit transfer chaincode", version = "0.0.1-SNAPSHOT"))

@Default
public final class LetterOfCreditStatus implements ContractInterface {

	private final Genson genson = new Genson();

	private enum TradeFinanceErrors {
		LetterOfCredit_NOT_FOUND,
		LetterOfCredit_ALREADY_EXISTS
	}

	/**
	 * Add some initial properties to the ledger
	 *
	 * @param ctx the transaction context
	 */
	@Transaction()
	public void initLedger(final Context ctx) {

	}

	/**
	 * This function is used to request a letter of credit.
	 * Input Parameters:
	 *
	 * @param ctx        the transaction context
	 * @param id         is a Letter of credit ID
	 * @param Expirydate is the expiry date of the letter of credit
	 * @param Buyer      is the buyer of the goods
	 * @param Bank       is the one who does the finance
	 * @param Seller     is the one who who sells the goods
	 * @param Amount     to be paid by the buyer to seller
	 * @return the created L/C
	 *         This function also checks the following:
	 *         The same LC with the same id does not exist already
	 **/

	@Transaction()
	public LetterOfCredit requestNewLOC(final Context ctx,
			final String _id,
			final String _expiryDate,
			final String _buyerName,
			final String _bank,
			final String _seller,
			final int _amount) {

		ChaincodeStub stub = ctx.getStub();

		String LOCState = stub.getStringState(_id);

		if (!LOCState.isEmpty()) {
			String errorMessage = String.format("Letter Of Credit ID %s already exists", _id);
			System.out.println(errorMessage);
			throw new ChaincodeException(errorMessage, TradeFinanceErrors.LetterOfCredit_ALREADY_EXISTS.toString());
		}

		LetterOfCredit LOC = new LetterOfCredit(_id, _expiryDate, _buyerName, _bank, _seller, _amount,
				LetterOfCredit.Status.REQUESTED);

		LOCState = genson.serialize(LOC);

		stub.putStringState(_id, LOCState);

		return LOC;
	}

	/**
	 * This function helps to issue a letter of credit to a seller by the buyer's
	 * bank.
	 * Input Parameters:
	 * 
	 * @param ctx the transaction context
	 * @param id  the key
	 * @return the Letter Of Credit with updated status
	 *         This function also checks the following:
	 *         ● LC should be present in the ledger.
	 */

	@Transaction()
	public LetterOfCredit issueLOC(final Context ctx, final String _id) {
		ChaincodeStub stub = ctx.getStub();

		String LOCState = stub.getStringState(_id);

		if (LOCState.isEmpty()) {
			String errorMessage = String.format("Product %s not Registered", _id);
			System.out.println(errorMessage);
			throw new ChaincodeException(errorMessage, TradeFinanceErrors.LetterOfCredit_NOT_FOUND.toString());
		}

		LetterOfCredit LOC = genson.deserialize(LOCState, LetterOfCredit.class);

		LetterOfCredit newLOC = new LetterOfCredit(LOC.getId(),
				LOC.getExpiryDate(),
				LOC.getBuyerName(),
				LOC.getBank(),
				LOC.getSeller(),
				LOC.getAmount(),
				LetterOfCredit.Status.ISSUED);

		String newProductState = genson.serialize(newLOC);

		stub.putStringState(_id, newProductState);

		return newLOC;
	}

	/**
	 * This function helps to accept the letter of credit.
	 * Input Parameters:
	 * 
	 * @param ctx the transaction context
	 * @param id  the key
	 * @return the Letter Of Credit with updated status
	 *         This function also checks the following:
	 *         ● LC should be present in the ledger.
	 * 
	 */

	@Transaction()
	public LetterOfCredit acceptLOC(final Context ctx, final String _id) {
		ChaincodeStub stub = ctx.getStub();

		String LOCState = stub.getStringState(_id);

		if (LOCState.isEmpty()) {
			String errorMessage = String.format("Product %s not Registered", _id);
			System.out.println(errorMessage);
			throw new ChaincodeException(errorMessage, TradeFinanceErrors.LetterOfCredit_NOT_FOUND.toString());
		}

		LetterOfCredit LOC = genson.deserialize(LOCState, LetterOfCredit.class);

		LetterOfCredit newLOC = new LetterOfCredit(LOC.getId(),
				LOC.getExpiryDate(),
				LOC.getBuyerName(),
				LOC.getBank(),
				LOC.getSeller(),
				LOC.getAmount(),
				LetterOfCredit.Status.ACCEPTED);

		String newProductState = genson.serialize(newLOC);

		stub.putStringState(_id, newProductState);

		return newLOC;
	}

	/**
	 * This function helps to retrieve the Letter of Credit details from the ledger.
	 * Input Parameters
	 * 
	 * @param ctx the transaction context
	 * @param id  the key
	 * @return the Letter Of Credit details
	 */

	@Transaction()
	public LetterOfCredit queryLOCById(final Context ctx, final String _id) {
		ChaincodeStub stub = ctx.getStub();
		String LOCState = stub.getStringState(_id);

		if (LOCState.isEmpty()) {
			String errorMessage = String.format("Product %s not Registered", _id);
			System.out.println(errorMessage);
			throw new ChaincodeException(errorMessage, TradeFinanceErrors.LetterOfCredit_NOT_FOUND.toString());
		}

		LetterOfCredit LOC = genson.deserialize(LOCState, LetterOfCredit.class);
		return LOC;
	}
}
