package by.bsuir.Dormitory.service;

import by.bsuir.Dormitory.dto.request.ApplicationRequest;
import by.bsuir.Dormitory.dto.request.ApplicationStatusRequest;
import by.bsuir.Dormitory.dto.response.ApplicationResponse;
import by.bsuir.Dormitory.exception.ApplicationNotFoundException;
import by.bsuir.Dormitory.exception.UserNotFoundException;
import by.bsuir.Dormitory.mapper.ApplicationMapper;
import by.bsuir.Dormitory.model.Application;
import by.bsuir.Dormitory.model.Document;
import by.bsuir.Dormitory.model.Right;
import by.bsuir.Dormitory.model.User;
import by.bsuir.Dormitory.repository.ApplicationRepository;
import by.bsuir.Dormitory.repository.DocumentRepository;
import by.bsuir.Dormitory.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
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
    private final UserRepository userRepository;

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
        Long size2 = applicationRepository.countByStatus(Application.Status.WAITING_CHECKIN);
        application.setNumber(size + 1 + size2);
        application = applicationRepository.save(application);
        return applicationMapper.mapToDto(application);
    }

    public void changeStatus(ApplicationStatusRequest request) {
        Application application = applicationRepository.findById(request.getApplicationId())
                .orElseThrow(() -> new ApplicationNotFoundException(request.getApplicationId()));

        Application.Status status = Application.Status.getStatusByType(request.getStatus());
        application.setStatus(status);
        if (status == Application.Status.CONFIRMED || status == Application.Status.CANCELED || status == Application.Status.REJECTED)
            application.setFinishDate(new Date());
        if (!request.getMessage().isEmpty())
            application.setMessage(request.getMessage());

        applicationRepository.save(application);
        recalculateQueue();
    }


    public List<Application> recalculateQueue() {
        List<Application> applications = applicationRepository
                .findAllByStatusIn(Arrays.asList(Application.Status.WAITING, Application.Status.WAITING_CHECKIN))
                .stream()
                .sorted((a1, a2) -> {
                    if (a1.getStatus() != a2.getStatus())
                        return a1.getStatus() == Application.Status.WAITING_CHECKIN ? -1 : 1;

                    Iterator<Right> it1 = documentRepository.findAllByApplication(a1)
                            .stream()
                            .map(Document::getRight)
                            .sorted(Right::compareTo)
                            .iterator();
                    Iterator<Right> it2 = documentRepository.findAllByApplication(a2)
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
                    return it1.hasNext() ? -1 : 1;
                })
                .collect(toList());
        for (int i = 0; i < applications.size(); i++) {
            applications.get(i).setNumber((long) (i + 1));
            applicationRepository.save(applications.get(i));
        }

        applicationRepository
                .findAllByStatusNotIn(Arrays.asList(Application.Status.WAITING, Application.Status.WAITING_CHECKIN))
                .forEach((application -> {
                    application.setNumber(0L);
                    applicationRepository.save(application);
                }));

        return applications;
    }

    @Transactional(readOnly = true)
    public ApplicationResponse getByCurrentUser() {
        User user = authService.getCurrentUser();
        Application application = applicationRepository.findFirstByUserOrderByDateDesc(user)
                .orElseThrow(() -> new ApplicationNotFoundException(user));
        return applicationMapper.mapToDto(application);
    }

    public List<ApplicationResponse> getAllByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        return getAllByUser(user);
    }

    public List<ApplicationResponse> getAllByCurrentUser() {
        User user = authService.getCurrentUser();
        return getAllByUser(user);
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getAllByUser(User user) {
        return applicationRepository.findAllByUserOrderByDateDesc(user)
                .stream()
                .map(applicationMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public List<ApplicationResponse> getAllByStatus(String type) {
        Application.Status status = Application.Status.getStatusByType(type);
        if (status == Application.Status.WAITING_CHECKIN)
            return applicationRepository.findAllByStatusOrderByNumber(status)
                    .stream()
                    .map(applicationMapper::mapToDto)
                    .collect(toList());
        return applicationRepository.findAllByStatusOrderByDate(status)
                .stream()
                .map(applicationMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public Long countByStatus(String type) {
        Application.Status status = Application.Status.getStatusByType(type);
        return applicationRepository.countByStatus(status);
    }

}
