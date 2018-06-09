package dailySummary.repository;


import dailySummary.model.PairingMatrixData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PairingMatrixRepository extends MongoRepository<PairingMatrixData, String> {
    List<PairingMatrixData> getByPair1AndPair2(String pair1, String pair2);
}
