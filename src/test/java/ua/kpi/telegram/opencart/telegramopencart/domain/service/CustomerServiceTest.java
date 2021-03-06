package ua.kpi.telegram.opencart.telegramopencart.domain.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.kpi.telegram.opencart.telegramopencart.domain.model.Customer;
import ua.kpi.telegram.opencart.telegramopencart.domain.model.taxonomy.Goods;
import ua.kpi.telegram.opencart.telegramopencart.domain.service.impl.CustomerServiceImpl;
import ua.kpi.telegram.opencart.telegramopencart.repository.CustomerRepository;
import ua.kpi.telegram.opencart.telegramopencart.repository.taxonomy.GoodsRepository;
import ua.kpi.telegram.opencart.telegramopencart.web.dto.CustomerDto;

import java.time.Instant;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceTest {
    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private GoodsRepository goodsRepository;

    private static final String TEST_USERNAME = "testLogin";

    private static final long TEST_USER_ID = 123;

    private static final String TEST_GOOD_1 = "testGood1";

    private static final long TEST_GOOD_ID_1 = 1;

    private static final String TEST_GOOD_2 = "testGood2";

    private static final long TEST_GOOD_ID_2 = 2;

    private static final String TEST_GOOD_3 = "testGood3";

    private static final long TEST_GOOD_ID_3 = 3;

    private Customer savedCustomer = new Customer();

    private CustomerDto testCustomerDto;

    private Goods savedGoods1 = new Goods(TEST_GOOD_1, "testDescription1", 123);

    private Goods savedGoods2 = new Goods(TEST_GOOD_2, "testDescription2", 123);

    @Before
    public void setUp() {
        savedCustomer.setRegisterDate(Instant.now());
        savedCustomer.setUsername(TEST_USERNAME);

        testCustomerDto = new CustomerDto();
        testCustomerDto.setUsername(TEST_USERNAME);

        savedCustomer.getCart().addToCart(savedGoods1, 4);
        savedCustomer.getCart().addToCart(savedGoods2, 5);
        savedCustomer.setId(TEST_USER_ID);

        when(customerRepository.findById(anyLong())).thenReturn(savedCustomer);
        //when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);
        when(goodsRepository.findById(anyLong())).thenReturn(savedGoods1);
    }

    @Test
    public void shouldSumAlreadyExisted() {
        customerService.addToCart(TEST_USER_ID, TEST_GOOD_ID_2, 3);

        assertEquals(savedGoods1, savedCustomer.getCart().getBuyItems().get(0).getGoods());
    }

    @Test
    public void shouldAddNewItemToCart() {
        customerService.addToCart(TEST_USER_ID, TEST_GOOD_ID_3, 3);

        assertEquals(savedGoods1, savedCustomer.getCart().getBuyItems().get(0).getGoods());
    }

    @Test
    public void shouldReturnTwoAmount() {
        customerService.removeFromCart(TEST_USER_ID, TEST_GOOD_ID_1, 2);

        assertEquals(2, savedCustomer.getCart().getBuyItems().get(0).getAmount());
    }

    @Test
    public void shouldReturnEmptyCartOnClearingCart() {
        customerService.clearCart(TEST_USER_ID);

        assertTrue(savedCustomer.getCart().isEmpty());
    }

    @Test
    public void shouldReturnEmptyCartOnCheckout() {
        customerService.checkout(TEST_USER_ID);

        assertTrue(savedCustomer.getCart().isEmpty());
    }

    @Test
    public void shouldReturnSavedGoodsTwo() {
        customerService.removeGoodsFromCart(TEST_USER_ID, TEST_GOOD_ID_1);

        assertEquals(savedGoods2, savedCustomer.getCart().getBuyItems().get(0).getGoods());
    }

    @Test
    public void shouldReturnSameListAsRepository() {
        assertEquals(asList(savedGoods1, savedGoods2), customerService.getAllCustomerGoods(testCustomerDto.getId()));
    }
}
