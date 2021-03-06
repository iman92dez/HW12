package services;

import domains.Account;
import domains.CreditCard;
import domains.Customer;
import domains.Transaction;
import repositories.CreditCardRepositoryDAO;
import repositories.CustomerRepositoryDAO;
import repositories.TransactionRepositoryDAO;

import java.util.Date;
import java.util.List;

public class CreditCardService {
    CustomerRepositoryDAO customerRepositoryDAO = CustomerRepositoryDAO.getInstance();
    CreditCardRepositoryDAO creditCardRepositoryDAO = CreditCardRepositoryDAO.getInstance();
    TransactionRepositoryDAO transactionRepositoryDAO = TransactionRepositoryDAO.getInstance();

    public void editSecondPasswordByCustomerId(long customerId, long creditCardId, String newPassword) {
        CreditCard creditCard = creditCardRepositoryDAO.selectById(creditCardId);
        if (creditCard != null && creditCard.getAccount().getCustomer().getId() == customerId) {
            creditCard.setSecondPassword(newPassword);
            creditCardRepositoryDAO.update(creditCard);
            System.out.println("Done, your new password is : " + newPassword);
        } else System.out.println("this creditCard not exist");
    }


    public long checkingInformation(long customerId, String originCard, String destinationCard, long chargeTransfer) {
        int check3 = 0;
        long originCardId = 0;
        List<CreditCard> creditCards = creditCardRepositoryDAO.selectAll();
        if (creditCards.size() > 0) {
            for (CreditCard item : creditCards) {
                if (item.getCardNumber().equals(originCard) && item.getCharge() >= (chargeTransfer + 500) && item.getAccount().getCustomer().getId() == customerId) {
                    System.out.println("information of origin card is ok");
                    check3++;
                    originCardId = item.getId();
                }
                if (item.getCardNumber().equals(destinationCard)) {
                    System.out.println("information of destination card is ok");
                    check3++;
                }
            }
        }
        if (check3 == 2) {
            return originCardId;
        } else return 0;
    }


    public String checkingInformation2(long originCardId, String secondPassword, String cvv2, String expirationDate) {
        CreditCard creditCard = creditCardRepositoryDAO.selectById(originCardId);

        if (creditCard.getSecondPassword().equals(secondPassword) &&
                creditCard.getCvv2().equals(cvv2) &&
                creditCard.getExpirationDate().equals(expirationDate))
            return "ok";
        else return null;
    }

    public void editChangeCharge(String originCard, String destinationCard, long chargeTransfer) {
        List<CreditCard> creditCardList = creditCardRepositoryDAO.selectAll();
        if (creditCardList.size() > 0)
        {
            for (CreditCard item : creditCardList)
            {
                if (item.getCardNumber().equals(originCard))
                {
                    item.setCharge(item.getCharge() - (chargeTransfer + 500L));
                    Account account = item.getAccount();
                    Transaction transaction = new Transaction();
                    transaction.setTransactionDate(new Date());
                    transaction.setCost(chargeTransfer);
                    transaction.setCreditCardDestination(destinationCard);
                    transaction.setAccount(account);
                    transactionRepositoryDAO.save(transaction);
                }
                if (item.getCardNumber().equals(destinationCard)) {
                    item.setCharge(item.getCharge() + chargeTransfer);
                }
            }
        }
    }


}
