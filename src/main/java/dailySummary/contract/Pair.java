package dailySummary.contract;

import dailySummary.model.PairingMatrixData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pair {
    private String pair1;
    private String pair2;
    private Integer days = 0;
    private LocalDateTime date = LocalDateTime.now();

    public PairingMatrixData toDomain(String teamEmail) {
        return new PairingMatrixData(pair1, pair2, days, LocalDateTime.now(), teamEmail);
    }

    @Override
    public boolean equals(Object o) {
        Pair other = (Pair) o;
        if (Objects.equals(pair1, other.pair1) && Objects.equals(pair2, other.pair2)) return true;
        return Objects.equals(pair1, other.pair2) && Objects.equals(pair2, other.pair1);
    }
}
