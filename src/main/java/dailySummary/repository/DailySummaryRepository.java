package dailySummary.repository;

import dailySummary.model.DailySummary;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface DailySummaryRepository extends MongoRepository<dailySummary.model.DailySummary, String> {
    List<DailySummary> findByDate(Date date);
}
