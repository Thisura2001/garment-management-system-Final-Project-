package lk.ijse.Dto;

import lk.ijse.Tm.CartTm;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString

public class PlaceOrderDto {
    private String orderId;
    private LocalDate date;
    private String customerId;
    private List<CartTm> cartTmList = new ArrayList<>();

    public List<CartTm> getCartTmList() {
        return cartTmList;
    }

    public void setCartTmList(List<CartTm> cartTmList) {
        this.cartTmList = cartTmList;
    }
}
