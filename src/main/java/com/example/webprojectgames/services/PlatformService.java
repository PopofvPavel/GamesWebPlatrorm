package com.example.webprojectgames.services;

import com.example.webprojectgames.model.entities.Platform;

import java.util.List;

public interface PlatformService {

    List<Platform> getPlatformsByNames(List<String> platform);
}
