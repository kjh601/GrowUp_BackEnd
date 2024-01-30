package Growup.spring.growRoom.repository;

import Growup.spring.growRoom.model.Post;
import Growup.spring.growRoom.model.component.Period;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    @Override
    Optional<Post> findById(Long id);
}
