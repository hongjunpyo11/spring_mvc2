package hello.itemservice.domain.item;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.ScriptAssert;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Item {

    @NotNull // 수정 요구사항 추가
    private Long id;

    @NotBlank(message = "공백X") // 빈값 + 공백만 있는 경우를 허용하지 않는다.
    private String itemName;

    @NotNull
    @Range(min = 1000, max = 1000000)
    private Integer price;

    @NotNull
//    @Max(9999) // 수정 요구사항 추가
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

/**
 * 수정은 잘 동작하지만 등록에서 문제가 발생한다.
 * 등록시에는 id 에 값도 없고, quantity 수량 제한 최대 값인 9999도 적용되지 않는 문제가 발생한다.
 *
 * 결과적으로 item 은 등록과 수정에서 검증 조건의 충돌이 발생하고, 등록과 수정은 같은 BeanValidation 을 적용할 수 없다.
 * 이 문제를 어떻게 해결할 수 있을까?
 */
