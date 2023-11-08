package lk.ijse.Dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

public class itemDto {
    String code;
    String description;
    int qty;
    String unitPrice;

}
