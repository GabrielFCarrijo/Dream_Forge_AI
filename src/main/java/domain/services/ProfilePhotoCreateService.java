package domain.services;

import domain.models.ProfilePhoto;
import domain.repositories.ProfilePhotoRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Map;

@ApplicationScoped
public class ProfilePhotoCreateService {
    private final ProfilePhotoRepository repository;

    public ProfilePhotoCreateService(ProfilePhotoRepository repository) {
        this.repository = repository;
    }

    public void save(String customerId, ProfilePhoto profilePhoto, String positivePrompt, String negativePrompt) {
        repository.registerEntities(Map.of(customerId, profilePhoto));
        repository.commit(positivePrompt, negativePrompt);
    }
}
