package ru.iworking.personnel.reserve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.iworking.personnel.reserve.repository.LearningHistoryRepository;

@Service
@RequiredArgsConstructor
public class LearningHistoryServiceImpl implements LearningHistoryService {

    public final LearningHistoryRepository learningHistoryRepository;

}
