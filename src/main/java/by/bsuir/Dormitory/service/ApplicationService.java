package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.ApplicationRequest;
import by.bsuir.Dormitory.dto.request.ApplicationStatusRequest;
import by.bsuir.Dormitory.dto.response.ApplicationResponse;
import by.bsuir.Dormitory.exception.ApplicationNotFoundException;
import by.bsuir.Dormitory.mapper.ApplicationMapper;
import by.bsuir.Dormitory.model.Application;
import by.bsuir.Dormitory.model.Document;
import by.bsuir.Dormitory.model.Right;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.ApplicationRepository;
import by.bsuir.Dormitory.repository.DocumentRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final DocumentRepository documentRepository;

    private final ApplicationMapper applicationMapper;

    private final AuthService authService;

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getAll() {
        return applicationRepository.findAll()
                .stream()
                .map(applicationMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getById(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
        return applicationMapper.mapToDto(application);
    }

    public void deleteById(Long id) {
        applicationRepository.deleteById(id);
    }

    public ApplicationResponse save(ApplicationRequest applicationRequest) {
        Application application = applicationMapper.map(applicationRequest);
        Long size = applicationRepository.countByStatus(Application.Status.WAITING);
        application.setNumber(size + 1);
        application = applicationRepository.save(application);
        return applicationMapper.mapToDto(application);
    }

    public void changeStatus(ApplicationStatusRequest request) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ApplicationNotFoundException(request.getApplicationId()));
        application.setStatus(Application.Status.getStatusByType(request.getStatus()));
        applicationRepository.save(application);
        recalculateQueue();
    }

    public void cancelCurrent() {
        User user = authService.getCurrentUser();
        Application application = applicationRepository.findByUser(user)
                .orElseThrow(() -> new ApplicationNotFoundException(user));
        cancel(application);
    }

    public void cancel(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
        cancel(application);
    }

    private void cancel(Application application) {
        application.setStatus(Application.Status.WAITING_CANCELED);
        applicationRepository.save(application);
    }

    public void confirmCancel(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ApplicationNotFoundException(id));
        application.setStatus(Application.Status.CANCELED);
        applicationRepository.save(application);
        recalculateQueue();
    }

    public List<Application> recalculateQueue() {
        List<Application> applications = applicationRepository
                .findAllByStatusIn(Arrays.asList(Application.Status.WAITING, Application.Status.WAITING_CANCELED))
                .stream()
                .sorted((a1, a2) -> {
                    Iterator<Right> it1 = documentRepository.findAllByUser(a1.getUser())
                            .stream()
                            .map(Document::getRight)
                            .sorted(Right::compareTo)
                            .iterator();
                    Iterator<Right> it2 = documentRepository.findAllByUser(a2.getUser())
                            .stream()
                            .map(Document::getRight)
                            .sorted(Right::compareTo)
                            .iterator();
                    while (it1.hasNext() && it2.hasNext()) {
                        int c = it1.next().compareTo(it2.next());
                        if (c != 0)
                            return c;
                    }

                    if (!it1.hasNext() && !it2.hasNext())
                        return a1.getDate().compareTo(a2.getDate());
                    return it1.hasNext() ? 1 : -1;
                })
                .collect(toList());
        for (int i = 0; i < applications.size(); i++) {
            applications.get(i).setNumber((long) (i + 1));
            applicationRepository.save(applications.get(i));
        }
        return applications;
    }
}
