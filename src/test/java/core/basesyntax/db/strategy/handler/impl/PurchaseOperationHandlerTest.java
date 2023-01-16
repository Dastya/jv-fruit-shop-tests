package core.basesyntax.db.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static PurchaseOperationHandler purchaseOperationHandler;
    private static final Integer DEFAULT_QUANTITY = 50;
    private static final String DEFAULT_FRUIT = "banana";
    private static final FruitTransaction.Operation PURCHASE = FruitTransaction.Operation.PURCHASE;

    @BeforeClass
    public static void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void init() {
        Storage.getAll().put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
    }

    @Test
    public void apply_correctValue_ok() {
        Integer expected = 30;
        purchaseOperationHandler.apply(
                new FruitTransaction(PURCHASE, DEFAULT_FRUIT, 20));
        Integer actual = Storage.getAll().get(DEFAULT_FRUIT);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void apply_productDoesntExistsKey_notOk() {
        purchaseOperationHandler.apply(
                new FruitTransaction(PURCHASE, "pineapple", DEFAULT_QUANTITY));
    }

    @Test (expected = RuntimeException.class)
    public void apply_NotEnoughProduct_notOk() {
        purchaseOperationHandler.apply(
                new FruitTransaction(PURCHASE, DEFAULT_FRUIT, 2 * DEFAULT_QUANTITY));
    }

    @AfterClass
    public static void tearDown() {
        Storage.getAll().clear();
    }
}