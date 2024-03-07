package com.example.webprojectgames.services.implementation;

import com.example.webprojectgames.model.entities.Platform;
import com.example.webprojectgames.repositories.PlatformRepository;
import com.example.webprojectgames.services.PlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlatformServiceImpl implements PlatformService {
final
PlatformRepository platformRepository;

    public PlatformServiceImpl(PlatformRepository platformRepository) {
        this.platformRepository = platformRepository;
    }


    @Override
    public List<Platform> getPlatformsByNames(List<String> platformNames) {
        return platformRepository.findAllByNameIn(platformNames);
    }

}
