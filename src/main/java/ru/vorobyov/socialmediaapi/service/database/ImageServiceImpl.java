package ru.vorobyov.socialmediaapi.service.database;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobyov.socialmediaapi.entity.Image;
import ru.vorobyov.socialmediaapi.entity.Post;
import ru.vorobyov.socialmediaapi.repository.ImageRepository;

import java.util.List;
import java.util.Optional;

/**
 * The implementation of  Image service.
 */
@Service("imageService")
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    /**
     * Instantiates a new Image service.
     *
     * @param imageRepository the image repository
     */
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Optional<Image> add(Image image) {
        return Optional.of(imageRepository.save(image));
    }

    @Override
    public List<Image> addAll(List<Image> images) {
        return imageRepository.saveAll(images);
    }

    @Override
    @Transactional
    public List<Image> updateAllByPost(Post post, List<Image> images) {
        imageRepository.deleteByPost(post);
        return addAll(images);
    }

    @Override
    @Transactional
    public void deleteByPost(Post post) {
        imageRepository.deleteByPost(post);
    }
}
