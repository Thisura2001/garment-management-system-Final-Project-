package lk.ijse.Tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@ToString
@Getter

public class ItemTm {
    private String code;
    private String description;
    private Integer qty_on_hand;
    private String unit_price;

}
