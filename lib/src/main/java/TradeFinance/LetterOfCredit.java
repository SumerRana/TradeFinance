package TradeFinance;

import com.owlike.genson.annotation.JsonProperty;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;
import java.util.Objects;

@DataType()
public final class LetterOfCredit {

	public enum Status {
        NOT_ISSUED,
        REQUESTED,
		ISSUED,
        ACCEPTED,
        EXPIRED
    }

	@Property()
	private final String id;

	@Property()
	private final String expiryDate;

	@Property()
	private final String buyerName;

	@Property()
	private final String bank;

	@Property()
	private final String seller;

	@Property()
	private final int amount;

	@Property()
	private final Status status;

	public String getId() {
		return id;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public String getBank() {
		return bank;
	}

	public String getSeller() {
		return seller;
	}

	public int getAmount() {
		return amount;
	}

	public Status getStatus() {
		return status;
	}

	public LetterOfCredit(@JsonProperty("id") final String id,
			@JsonProperty("expiryDate") final String expiryDate,
			@JsonProperty("buyerName") final String buyerName,
			@JsonProperty("bank") final String bank,
			@JsonProperty("seller") final String seller,
			@JsonProperty("amount") final int amount,
			@JsonProperty("status") final Status status) {

		this.id = id;
		this.expiryDate = expiryDate;
		this.buyerName = buyerName;
		this.bank = bank;
		this.seller = seller;
		this.amount = amount;
		this.status = status;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if ((obj == null) || (getClass() != obj.getClass())) {
			return false;
		}
		LetterOfCredit other = (LetterOfCredit) obj;
		return Objects.equals(id, other.id) &&
				Objects.equals(expiryDate, other.expiryDate) &&
				Objects.equals(buyerName, other.buyerName) &&
				Objects.equals(bank, other.bank) &&
				Objects.equals(seller, other.seller) &&
				Objects.equals(amount, other.amount) &&
				Objects.equals(status, other.status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, expiryDate, buyerName, bank, seller, amount, status);
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "@" + Integer.toHexString(hashCode()) + " [id=" + id
				+ ", expiryDate=" + expiryDate + ", buyerName=" + buyerName + ", bank=" + bank
				+ ", seller=" + seller + ", amount=" + amount + ", status=" + status + "]";
	}

}