package lost.canvas.micronaut_test.repository;


import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.PageableRepository;
import lost.canvas.micronaut_test.entity.User;

@Repository
public interface UserRepository extends PageableRepository<User, Long> {
}
