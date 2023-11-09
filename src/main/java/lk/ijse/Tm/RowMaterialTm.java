package lk.ijse.Tm;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class RowMaterialTm {
    private String row_id;
    private String name;
    private String unit_price;
    private Integer qty_on_hand;
}
