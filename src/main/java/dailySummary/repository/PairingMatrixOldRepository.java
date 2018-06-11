package dailySummary.repository;


import dailySummary.contract.Pair;
import dailySummary.model.PairingMatrixData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PairingMatrixOldRepository extends MongoRepository<Pair, String> {
    List<PairingMatrixData> getByPair1AndPair2(String pair1, String pair2);
}
