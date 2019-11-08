package com.udacity.course3.reviews.repository;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CommentRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired private CommentRepository commentRepository;

    @Test
    public void injectedComponentsAreNotNull(){
        assertThat(testEntityManager).isNotNull();
        assertThat(commentRepository).isNotNull();
    }

    @Test
    public void testCreateCommentForReview(){
        Product product = new Product("iPhone XS Max",
                "Welcome to the big screens.  Super retina.  Even faster Face ID.");
        Review review = new Review("Awesome Phone",
                "coming from the iPhone X.  The XS Max is much faster!", true);
        review.setProduct(product);

        testEntityManager.persist(product);
        testEntityManager.persist(review);
        testEntityManager.flush();

        Comment comment = new Comment("Awesome Phone",
                "Coming from the iPhone X.  The XS Max is much faster!");
        comment.setReview(review);

        commentRepository.save(comment);

        Comment fromDb = commentRepository.findById(comment.getCommentId()).orElse(null);
        assertThat(fromDb.getCommentId()).isEqualTo(comment.getCommentId());

        commentRepository.delete(comment); // clean up
    }

    @Test
    public void testFindAllByReview(){
        Product product = new Product("iPhone 11",
                "Welcome to the big screens.  Super retina.  Even faster Face ID.");
        Review review = new Review("The best $700 iPhone Apple has ever made",
                "Even faster speed, improved battery life.", true);
        review.setProduct(product);

        Comment comment1 = new Comment("Awesome Phone",
                "Coming from the iPhone X.  The XS Max is much faster!");
        comment1.setReview(review);
        Comment comment2 = new Comment("Amazing Phone",
                "Loving the new iPhone XS Max So far.  The screen is amazing");
        comment2.setReview(review);

        testEntityManager.persist(product);
        testEntityManager.persist(review);
        testEntityManager.persist(comment1);
        testEntityManager.persist(comment2);
        testEntityManager.flush();

        List<Comment> allComments = commentRepository.findAllByReview(review);
        assertThat(allComments).hasSize(2).extracting(Comment::getCommentTitle)
                .containsOnly(comment1.getCommentTitle(), comment2.getCommentTitle());
    }
}
